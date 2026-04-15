package ua.edu.chmnu.ki.networks.rsv.deserializer.impl;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.protocol.HeartBeat;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.deserializer.PacketDeserializer;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.time.LocalDateTime;

@AllArgsConstructor
public class HeartBeatPacketDeserializer implements PacketDeserializer {

    private final DatagramPacket datagramPacket;

    @Override
    public PacketFrame deserialize() throws IOException {

        byte[] source = datagramPacket.getData();

        int length = datagramPacket.getLength();

        try (ByteArrayInputStream bais = new ByteArrayInputStream(source, 0, length);
             DataInputStream dis = new DataInputStream(bais)) {

            PacketType packetType = PacketType.of(dis.readByte());

            if (packetType != PacketType.HEARTBEAT) {
                throw new IllegalArgumentException("Cannot read heart-beat packet");
            }

            int year = dis.readInt();
            int month = dis.readInt();
            int day = dis.readInt();
            int hour = dis.readInt();
            int minute = dis.readInt();
            int second = dis.readInt();
            int nano = dis.readInt();

            LocalDateTime timeStamp = LocalDateTime.of(year, month, day, hour, minute, second, nano);

            return new HeartBeat(PacketType.HEARTBEAT, timeStamp);

        }
    }
}
