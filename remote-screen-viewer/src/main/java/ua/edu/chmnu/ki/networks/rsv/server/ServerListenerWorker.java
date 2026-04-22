package ua.edu.chmnu.ki.networks.rsv.server;


import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.deserializer.PacketDeserializeFactory;
import ua.edu.chmnu.ki.networks.rsv.deserializer.PacketDeserializer;
import ua.edu.chmnu.ki.networks.rsv.protocol.ClientHello;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;
import ua.edu.chmnu.ki.networks.rsv.server.registry.ClientRegistryService;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

@AllArgsConstructor
public class ServerListenerWorker implements Runnable {

    private final UdpTransport transport;

    private final ClientRegistryService clientRegistryService;

    private final PacketDeserializeFactory deserializeFactory;

    private final int packetSize;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                DatagramPacket packet = transport.receive(packetSize);

                if (packet.getLength() < 1) {
                    continue;
                }

                PacketDeserializer deserializer = deserializeFactory.fetchBy(packet);

                PacketFrame packetFrame = deserializer.deserialize();

                if (packetFrame instanceof ClientHello clientHello) {
                    InetSocketAddress address = new InetSocketAddress(packet.getAddress(), packet.getPort());
                    clientRegistryService.register(clientHello, address);
                    System.out.println("Registered client: " + clientHello.clientName() + " at " + address);
                }

            } catch (Exception e) {
                if (!Thread.currentThread().isInterrupted()) {
                    System.err.println("Listener error: " + e.getMessage());
                }
            }
        }
    }
}
