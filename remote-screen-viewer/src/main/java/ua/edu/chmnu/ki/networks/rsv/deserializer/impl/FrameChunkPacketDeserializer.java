package ua.edu.chmnu.ki.networks.rsv.deserializer.impl;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunk;
import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunkHeader;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.deserializer.PacketDeserializer;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

@AllArgsConstructor
public class FrameChunkPacketDeserializer implements PacketDeserializer {

    private final DatagramPacket datagramPacket;

    @Override
    public PacketFrame deserialize() throws IOException {

        byte[] source = datagramPacket.getData();

        int length = datagramPacket.getLength();

        try (ByteArrayInputStream bais = new ByteArrayInputStream(source, 0, length);
             DataInputStream dis = new DataInputStream(bais)) {

            PacketType packetType = PacketType.of(dis.readByte());

            if (packetType != PacketType.FRAME_CHUNK) {
                throw new IllegalArgumentException("Cannot read frame-chunk packet");
            }
            int frameId = dis.readInt();
            int totalChunks = dis.readInt();
            int chunkIndex = dis.readInt();
            int totalBytes = dis.readInt();
            int payloadLength = dis.readInt();

            FrameChunkHeader header = new FrameChunkHeader(frameId, totalChunks, chunkIndex, totalBytes, payloadLength, 0);

            byte[] payload = new byte[payloadLength];

            dis.read(payload, 0, payloadLength);

            return new FrameChunk(PacketType.FRAME_CHUNK, header, payload);

        }
    }
}
