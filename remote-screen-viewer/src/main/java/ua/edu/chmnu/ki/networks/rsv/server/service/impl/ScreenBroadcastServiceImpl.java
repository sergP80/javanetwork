package ua.edu.chmnu.ki.networks.rsv.server.service.impl;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.codec.FrameEncoder;
import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunk;
import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunkHeader;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializeFactory;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializer;
import ua.edu.chmnu.ki.networks.rsv.server.service.ClientBroadcastService;
import ua.edu.chmnu.ki.networks.rsv.server.service.ScreenBroadcastService;
import ua.edu.chmnu.ki.networks.rsv.server.service.ScreenService;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class ScreenBroadcastServiceImpl implements ScreenBroadcastService {
    private static final int HEADER_SIZE = 1 + 4 + 4 + 4 + 4 + 4;

    private final ScreenService screenService;

    private final FrameEncoder encoder;

    private final ClientBroadcastService clientBroadcastService;

    private final PacketSerializeFactory packetSerializeFactory;

    private final int packetSize;

    private final AtomicInteger frameCounter = new AtomicInteger();

    @Override
    public void captureAndSend() throws IOException {
        BufferedImage image = screenService.capture();
        byte[] encoded = encoder.encode(image);

        int maxPayload = packetSize - HEADER_SIZE;
        int totalChunks = (int) Math.ceil((double) encoded.length / maxPayload);

        int frameId = frameCounter.incrementAndGet();

        for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
            int start = chunkIndex * maxPayload;
            int len = Math.min(maxPayload, encoded.length - start);

            FrameChunk frameChunk = createChunk(
                    frameId,
                    totalChunks,
                    chunkIndex,
                    encoded.length,
                    encoded,
                    start,
                    len
            );

            PacketSerializer packetSerializer = packetSerializeFactory.fetchBy(frameChunk);

            byte[] serializedPacket = packetSerializer.serialize();

            clientBroadcastService.broadcast(serializedPacket);
        }
    }

    private FrameChunk createChunk(int frameId, int totalChunks, int chunkIndex, int totalBytes,
                                   byte[] source, int offset, int length) {

        FrameChunkHeader header = new FrameChunkHeader(
                frameId,
                totalChunks,
                chunkIndex,
                totalBytes,
                length,
                offset);

        return new FrameChunk(
                PacketType.FRAME_CHUNK,
                header,
                source
        );
    }
}
