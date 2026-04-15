package ua.edu.chmnu.ki.networks.rsv.protocol;

public record FrameChunk(FrameChunkHeader header, byte[] payload) implements PacketFrame {
    @Override
    public PacketType type() {
        return PacketType.FRAME_CHUNK;
    }
}
