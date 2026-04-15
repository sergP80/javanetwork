package ua.edu.chmnu.ki.networks.rsv.serializer;

import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;

public interface PacketSerializeFactory {

    PacketSerializer fetchBy(PacketFrame source);
}
