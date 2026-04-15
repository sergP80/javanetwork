package ua.edu.chmnu.ki.networks.rsv.client.assembly;

import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunk;

public interface FrameAssembler {

    byte[] accept(FrameChunk chunk);
}
