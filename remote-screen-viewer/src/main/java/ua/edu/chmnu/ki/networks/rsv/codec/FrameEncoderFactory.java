package ua.edu.chmnu.ki.networks.rsv.codec;

public interface FrameEncoderFactory {

    FrameEncoder fetchBy(String type, float quality);
}
