package ua.edu.chmnu.ki.networks.udp.core.sender;

import ua.edu.chmnu.ki.networks.common.SerializeUtils;
import ua.edu.chmnu.ki.networks.core.EndPoint;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpObjectMessageSender<T> implements MessageSender<T> {

    private final DatagramSocket socket;
    private final EndPoint endPoint;

    public UdpObjectMessageSender(EndPoint endPoint) throws IOException {
        this.endPoint = endPoint;
        this.socket = new DatagramSocket();
    }

    public UdpObjectMessageSender(String endPoint) throws IOException {
        this(EndPoint.parseString(endPoint));
    }

    @Override
    public void send(T message) throws IOException {
        byte[] payload = SerializeUtils.serialize(message);
        DatagramPacket packet = new DatagramPacket(payload, payload.length, endPoint.getInetAddress(), endPoint.port());
        socket.send(packet);
    }

    @Override
    public void close() {
        socket.close();
    }
}
