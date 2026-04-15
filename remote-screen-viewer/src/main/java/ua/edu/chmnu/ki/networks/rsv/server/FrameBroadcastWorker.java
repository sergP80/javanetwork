package ua.edu.chmnu.ki.networks.rsv.server;


import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.codec.FrameEncoder;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class FrameBroadcastWorker implements Runnable {

    private static final int HEADER_SIZE = 1 + 4 + 4 + 4 + 4 + 4;

    private final UdpTransport transport;
    private final ClientRegistry clientRegistry;
    private final ScreenCaptureService captureService;
    private final FrameEncoder encoder;
    private final int fps;
    private final float jpegQuality;
    private final int packetSize;
    private final AtomicInteger frameCounter = new AtomicInteger();

    @Override
    public void run() {
        long delayMs = Math.max(1, 1000L / Math.max(1, fps));

        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (clientRegistry.isEmpty()) {
                    Thread.sleep(500);
                    continue;
                }

                BufferedImage image = captureService.capture();
                byte[] encoded = encoder.encode(image, jpegQuality);

                int maxPayload = packetSize - HEADER_SIZE;
                int totalChunks = (int) Math.ceil((double) encoded.length / maxPayload);
                int frameId = frameCounter.incrementAndGet();

                Set<InetSocketAddress> clients = clientRegistry.getClients();

                for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
                    int start = chunkIndex * maxPayload;
                    int len = Math.min(maxPayload, encoded.length - start);

                    byte[] packetBytes = createFrameChunkPacket(
                            frameId,
                            totalChunks,
                            chunkIndex,
                            encoded.length,
                            encoded,
                            start,
                            len
                    );

                    for (InetSocketAddress client : clients) {
                        transport.send(packetBytes, client);
                    }
                }

                Thread.sleep(delayMs);
            } catch (Exception e) {
                if (!Thread.currentThread().isInterrupted()) {
                    System.err.println("Broadcast error: " + e.getMessage());
                }
            }
        }
    }

    private byte[] createFrameChunkPacket(int frameId,
                                          int totalChunks,
                                          int chunkIndex,
                                          int totalBytes,
                                          byte[] source,
                                          int offset,
                                          int length) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(HEADER_SIZE + length);
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeByte(PacketType.FRAME_CHUNK);
        dos.writeInt(frameId);
        dos.writeInt(totalChunks);
        dos.writeInt(chunkIndex);
        dos.writeInt(totalBytes);
        dos.writeInt(length);
        dos.write(source, offset, length);
        dos.flush();

        return baos.toByteArray();
    }
}
