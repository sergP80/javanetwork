package ua.edu.chmnu.ki.networks.rsv.serializer.impl;

import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class AbstractPacketSerializer implements PacketSerializer {

    protected final PacketFrame source;

    protected AbstractPacketSerializer(PacketFrame source) {
        this.source = source;
    }

    protected abstract boolean accept();

    protected abstract void consume(DataOutputStream dos) throws IOException;

    @Override
    public byte[] serialize() throws IOException {

        byte[] result = new byte[]{source.type().getType()};

        if (accept()) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 DataOutputStream dos = new DataOutputStream(baos)) {

                consume(dos);
                dos.flush();

                result = baos.toByteArray();
            }
        }

        return result;
    }
}
