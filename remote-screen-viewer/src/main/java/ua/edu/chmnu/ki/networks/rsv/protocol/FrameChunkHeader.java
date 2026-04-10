package ua.edu.chmnu.ki.networks.rsv.protocol;

public record FrameChunkHeader(
        int frameId,
        int totalChunks,
        int chunkIndex,
        int totalBytes,
        int payloadLength
) {
}