package ua.edu.chmnu.ki.networks.rsv.deserializer;

import java.net.DatagramPacket;

public interface PacketDeserializeFactory {

    PacketDeserializer fetchBy(DatagramPacket packet);
}
