package ua.edu.chmnu.ki.networks.udp.swing_game.core;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.GamerInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class UdpSender implements Runnable {
    private String host;
    private int port;
    private DatagramSocket socket;

    private Boolean active = true;

    private ArrayBlockingQueue<GamerInfo> gamerInfoQueue = new ArrayBlockingQueue<>(500);

    public UdpSender(String host, int port) throws SocketException {
        this.host = host;
        this.port = port;
        this.socket = new DatagramSocket();
    }

    public Boolean active() {
        return active;
    }

    public void active(Boolean active) {
        this.active = active;
    }

    public void submit(GamerInfo gamerInfo) throws InterruptedException {
        gamerInfoQueue.offer(gamerInfo, 10000, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        while (active()) {
            try {
                GamerInfo gamerInfo = gamerInfoQueue.take();

                try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream os = new ObjectOutputStream(bos)) {
                    os.writeObject(gamerInfo);
                    os.flush();
                    byte[] rowData = bos.toByteArray();
                    DatagramPacket packet = new DatagramPacket(rowData, rowData.length, InetAddress.getByName(this.host), this.port);
                    this.socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
