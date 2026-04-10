package ua.edu.chmnu.ki.networks.rsv.transport;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public interface UdpTransport extends Closeable {

    void bind(InetSocketAddress address) throws IOException;

    void send(byte[] data, InetSocketAddress target) throws IOException;

    DatagramPacket receive(int maxPacketSize) throws IOException;
}
