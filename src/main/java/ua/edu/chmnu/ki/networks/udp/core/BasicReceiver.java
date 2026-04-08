package ua.edu.chmnu.ki.networks.udp.core;

import lombok.Getter;
import lombok.SneakyThrows;
import ua.edu.chmnu.ki.networks.common.ObjectConverter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.function.Consumer;

public class BasicReceiver<T> implements Runnable {

    @Getter
    private final int port;

    private final DatagramSocket datagramSocket;

    private final Consumer<T> consumer;

    private final int bufferSize;

    private final byte[] buffer;

    @Getter
    private boolean isActive = true;

    public BasicReceiver(int port, int bufferSize, Consumer<T> consumer) throws SocketException {
        this.port = port;
        this.consumer = consumer;
        this.bufferSize = bufferSize;
        this.buffer = new byte[bufferSize];
        this.datagramSocket = new DatagramSocket();
    }

    public BasicReceiver(int port, Consumer<T> consumer) throws SocketException {
        this(port, 4 * 1024, consumer);
    }

    public void setActive(boolean active) {
        isActive = active;

        if (!isActive) {
            datagramSocket.close();
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        while (isActive && !datagramSocket.isClosed()) {
            DatagramPacket datagramPacket = new DatagramPacket(buffer, bufferSize);

            datagramSocket.receive(datagramPacket);

            byte[] buffer = datagramPacket.getData();

            int length = datagramPacket.getLength();

            T data = ObjectConverter.convertFromByte(buffer, length);

            consumer.accept(data);
        }
    }
}
