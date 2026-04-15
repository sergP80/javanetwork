package ua.edu.chmnu.ki.networks.rsv.serializer.impl;

import ua.edu.chmnu.ki.networks.rsv.protocol.ClientHello;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ClientHelloPacketSerializer extends AbstractPacketSerializer implements PacketSerializer {

    public ClientHelloPacketSerializer(PacketFrame source) {
        super(source);
    }

    @Override
    protected boolean accept() {
        return source instanceof ClientHello;
    }

    @Override
    protected void consume(DataOutputStream dos) throws IOException {
        if (source instanceof ClientHello clientHello) {
            dos.writeByte(clientHello.type().getType());
            dos.write(clientHello.clientName().getBytes(StandardCharsets.UTF_8));
        }
    }
}
