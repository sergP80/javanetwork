package ua.edu.chmnu.ki.networks.rsv.client.assembly;

import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunk;
import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunkHeader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FrameAssemblerImpl implements FrameAssembler {

    private final Map<Integer, PartialFrame> frames = new ConcurrentHashMap<>();

    @Override
    public byte[] accept(FrameChunk chunk) {
        FrameChunkHeader header = chunk.header();

        PartialFrame frame = frames.computeIfAbsent(
                header.frameId(),
                id -> new PartialFrame(header.totalChunks(), header.totalBytes())
        );

        frame.put(header.chunkIndex(), chunk.payload());

        if (frame.isComplete()) {
            frames.remove(header.frameId());
            return frame.join();
        }

        cleanupOldFrames(header.frameId());

        return null;
    }

    private void cleanupOldFrames(int latestFrameId) {
        frames.keySet().removeIf(id -> id < latestFrameId - 10);
    }



    private static class PartialFrame {
        private final byte[][] chunks;
        private final int totalBytes;
        private int received;

        PartialFrame(int totalChunks, int totalBytes) {
            this.chunks = new byte[totalChunks][];
            this.totalBytes = totalBytes;
        }

        synchronized void put(int index, byte[] payload) {
            if (chunks[index] == null) {
                chunks[index] = payload;
                received++;
            }
        }

        synchronized boolean isComplete() {
            return received == chunks.length;
        }

        synchronized byte[] join() {
            byte[] result = new byte[totalBytes];
            int offset = 0;

            for (byte[] chunk : chunks) {
                System.arraycopy(chunk, 0, result, offset, chunk.length);
                offset += chunk.length;
            }

            return result;
        }
    }
}
