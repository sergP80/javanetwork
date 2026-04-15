package ua.edu.chmnu.ki.networks.rsv.deserializer;

import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;

import java.io.IOException;

public interface PacketDeserializer {

    PacketFrame deserialize() throws IOException;
}
