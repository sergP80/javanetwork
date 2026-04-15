package ua.edu.chmnu.ki.networks.rsv.codec;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface FrameEncoder {

    byte[] encode(BufferedImage image, float quality) throws IOException;
}
