package ua.edu.chmnu.ki.networks.udp.swing_game.core;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.GamerInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class UdpReceiver implements Runnable {
    private DatagramSocket socket;

    private Boolean active = true;

    private ArrayBlockingQueue<GamerInfo> gamerInfoQueue = new ArrayBlockingQueue<>(500);

    public UdpReceiver() throws SocketException {
        this.socket = new DatagramSocket();
    }

    public Boolean active() {
        return active;
    }

    public void active(Boolean active) {
        this.active = active;
    }

    public GamerInfo poll() {
        return gamerInfoQueue.poll();
    }
    public boolean isEmpty() {
        return gamerInfoQueue.peek() == null;
    }

    @Override
    public void run() {
        while (active()) {
            try {
                byte[] buffer = new byte[1024*4];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                this.socket.receive(packet);

                try(ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
                    ObjectInputStream is = new ObjectInputStream(bis)) {
                    GamerInfo gamerInfo = (GamerInfo)is.readObject();
                    gamerInfoQueue.offer(gamerInfo, 10000, TimeUnit.SECONDS);
                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
