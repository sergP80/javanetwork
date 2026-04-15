package ua.edu.chmnu.ki.networks.rsv.protocol;

public record FrameChunk(
        PacketType type,
        FrameChunkHeader header,
        byte[] payload
) implements PacketFrame {
}
