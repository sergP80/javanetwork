package ua.edu.chmnu.ki.networks.rsv.serializer;

import java.io.IOException;

public interface PacketSerializer {

    byte[] serialize() throws IOException;
}
