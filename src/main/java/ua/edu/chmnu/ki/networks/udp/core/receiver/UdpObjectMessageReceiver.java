package ua.edu.chmnu.ki.networks.udp.core.receiver;

import ua.edu.chmnu.ki.networks.common.SerializeUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpObjectMessageReceiver<T> implements MessageReceiver<T> {
    private static final int DEFAULT_BUFFER_SIZE = 4 * 1024;

    private final DatagramSocket socket;
    private final int bufferSize;

    public UdpObjectMessageReceiver(int port, int bufferSize) throws IOException {
        this.socket = new DatagramSocket(port);
        this.bufferSize = bufferSize;
    }

    public UdpObjectMessageReceiver(int port) throws IOException {
        this(port, Integer.parseInt(System.getProperty("udp.message.receiver.buffer.size", "" + DEFAULT_BUFFER_SIZE)));
    }

    @Override
    public T receive() throws IOException {
        byte[] buffer = new byte[bufferSize];

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        socket.receive(packet);

        return SerializeUtils.deserialize(packet.getData(), packet.getLength());
    }

    @Override
    public void close() {
        socket.close();
    }
}
