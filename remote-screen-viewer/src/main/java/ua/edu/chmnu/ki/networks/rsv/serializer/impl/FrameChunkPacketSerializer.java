package ua.edu.chmnu.ki.networks.rsv.serializer.impl;

import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunk;
import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunkHeader;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializer;

import java.io.DataOutputStream;
import java.io.IOException;

public class FrameChunkPacketSerializer extends AbstractPacketSerializer implements PacketSerializer {
    private static final int HEADER_SIZE = 1 + 4 + 4 + 4 + 4 + 4;

    public FrameChunkPacketSerializer(PacketFrame source) {
        super(source);
    }

    @Override
    protected boolean accept() {
        return source instanceof FrameChunk;
    }

    @Override
    protected void consume(DataOutputStream dos) throws IOException {
        if (source instanceof FrameChunk frameChunk) {
            dos.writeByte(PacketType.FRAME_CHUNK.getType());
            FrameChunkHeader header = frameChunk.header();

            dos.writeInt(header.frameId());
            dos.writeInt(header.totalChunks());
            dos.writeInt(header.chunkIndex());
            dos.writeInt(header.totalBytes());
            dos.writeInt(header.payloadLength());
            dos.write(frameChunk.payload(), header.offset(), header.payloadLength());
        }
    }
}
