package ua.edu.chmnu.ki.networks.udp.core;

import lombok.Getter;
import lombok.SneakyThrows;
import ua.edu.chmnu.ki.networks.common.ObjectConverter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.function.Supplier;

public class BasicSender<T> implements Runnable {

    @Getter
    private final String host;

    @Getter
    private final Integer port;

    private final DatagramSocket datagramSocket;

    private final Supplier<T> supplier;

    @Getter
    private boolean isActive = true;

    public BasicSender(String host, Integer port, Supplier<T> supplier) throws SocketException {
        this.host = host;
        this.port = port;
        this.supplier = supplier;
        this.datagramSocket = new DatagramSocket();
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
            T data = supplier.get();

            if (data == null) {
                Thread.sleep(100);
                continue;
            }

            byte[] bytes = ObjectConverter.convertToByte(data);

            InetAddress inetAddress = InetAddress.getByName(host);

            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, inetAddress, port);

            System.out.println("Transfer data: " + data);

            System.out.println("Active: " + isActive);
            System.out.println("Socket closed is: " + datagramSocket.isClosed());

            datagramSocket.send(datagramPacket);

            Thread.sleep(100);
        }
    }
}
