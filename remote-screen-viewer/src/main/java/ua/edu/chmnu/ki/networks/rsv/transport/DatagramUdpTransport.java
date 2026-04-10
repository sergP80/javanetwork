package ua.edu.chmnu.ki.networks.rsv.transport;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class DatagramUdpTransport implements UdpTransport {

    private DatagramSocket socket;

    @Override
    public void bind(InetSocketAddress address) throws IOException {
        this.socket = new DatagramSocket(address);
    }

    @Override
    public void send(byte[] data, InetSocketAddress target) throws IOException {
        DatagramPacket packet = new DatagramPacket(
                data,
                data.length,
                target.getAddress(),
                target.getPort()
        );
        socket.send(packet);
    }

    @Override
    public DatagramPacket receive(int maxPacketSize) throws IOException {
        byte[] buffer = new byte[maxPacketSize];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return packet;
    }

    @Override
    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
