package ua.edu.chmnu.ki.networks.rsv.client.service;

import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunk;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ClientScreenAssemblyService {

    BufferedImage tryToCompleteWith(FrameChunk chunk) throws IOException;
}
