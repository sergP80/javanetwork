package ua.edu.chmnu.ki.networks.rsv.server;


import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class ServerListenerWorker implements Runnable {

    private final UdpTransport transport;
    private final ClientRegistry clientRegistry;
    private final int packetSize;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                DatagramPacket packet = transport.receive(packetSize);
                byte[] data = packet.getData();
                int length = packet.getLength();

                if (length < 1) {
                    continue;
                }

                byte type = data[0];
                if (type == PacketType.HELLO.getType()) {
                    String clientName = new String(data, 1, length - 1, StandardCharsets.UTF_8);
                    InetSocketAddress address = new InetSocketAddress(packet.getAddress(), packet.getPort());
                    clientRegistry.register(address);
                    System.out.println("Registered client: " + clientName + " at " + address);
                }
            } catch (Exception e) {
                if (!Thread.currentThread().isInterrupted()) {
                    System.err.println("Listener error: " + e.getMessage());
                }
            }
        }
    }
}
