package ua.edu.chmnu.ki.networks.rsv.client.service.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ua.edu.chmnu.ki.networks.rsv.client.service.HelloService;
import ua.edu.chmnu.ki.networks.rsv.protocol.ClientHello;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializeFactory;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializer;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;

@AllArgsConstructor
public class HelloServiceImpl implements HelloService {

    private final UdpTransport transport;

    private final PacketSerializeFactory serializeFactory;

    @Override
    public boolean sendTo(InetSocketAddress target) throws IOException {
        String clientName = readClientName();

        if (StringUtils.isBlank(clientName)) {
            return false;
        }

        ClientHello clientHello = new ClientHello(clientName);

        PacketSerializer serializer = serializeFactory.fetchBy(clientHello);

        byte[] serializedPayload = serializer.serialize();

        transport.send(serializedPayload, target);

        return true;
    }

    private String readClientName() {
        return JOptionPane.showInputDialog(null, "Enter client name", "Watcher");
    }
}
