package ua.edu.chmnu.ki.networks.rsv.protocol;

public record FrameChunk(
        FrameChunkHeader header,
        byte[] payload
) {
}
