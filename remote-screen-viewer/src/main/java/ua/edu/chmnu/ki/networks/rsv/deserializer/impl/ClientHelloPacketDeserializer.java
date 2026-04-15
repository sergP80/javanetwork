package ua.edu.chmnu.ki.networks.rsv.deserializer.impl;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.deserializer.PacketDeserializer;
import ua.edu.chmnu.ki.networks.rsv.protocol.ClientHello;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;

import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class ClientHelloPacketDeserializer implements PacketDeserializer {

    private final DatagramPacket datagramPacket;

    @Override
    public PacketFrame deserialize() {

        byte[] source = datagramPacket.getData();

        int length = datagramPacket.getLength();

        String message = new String(source, 1, length - 1, StandardCharsets.UTF_8);

        return new ClientHello(message);
    }
}
