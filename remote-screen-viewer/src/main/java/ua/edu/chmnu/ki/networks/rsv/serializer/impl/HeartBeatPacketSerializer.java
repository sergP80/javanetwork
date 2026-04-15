package ua.edu.chmnu.ki.networks.rsv.serializer.impl;

import ua.edu.chmnu.ki.networks.rsv.protocol.HeartBeat;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class HeartBeatPacketSerializer extends AbstractPacketSerializer implements PacketSerializer {

    public HeartBeatPacketSerializer(PacketFrame source) {
        super(source);
    }

    @Override
    protected boolean accept() {
        return source instanceof HeartBeat;
    }

    @Override
    protected void consume(DataOutputStream dos) throws IOException {
        if (source instanceof HeartBeat heartBeat) {
            dos.writeByte(heartBeat.type().getType());

            LocalDateTime localDateTime = LocalDateTime.now();

            dos.writeInt(localDateTime.getYear());
            dos.writeInt(localDateTime.getMonthValue());
            dos.writeInt(localDateTime.getDayOfMonth());
            dos.writeInt(localDateTime.getHour());
            dos.writeInt(localDateTime.getMinute());
            dos.writeInt(localDateTime.getSecond());
            dos.writeInt(localDateTime.getNano());
        }
    }
}
