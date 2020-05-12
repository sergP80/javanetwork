package ua.edu.chmnu.ki.networks.udp.swing_game.core;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.GamerLocation;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class UdpSender implements Runnable {

    private String host;
    private int port;
    private DatagramSocket socket;

    private Boolean active = true;

    private ArrayBlockingQueue<GamerLocation> gamerLocationQueue = new ArrayBlockingQueue<>(500);

    public UdpSender(String host, int port) {
        try {
            init(host, port);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public UdpSender() {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getResourceAsStream("/game.properties"));
            String host = properties.getProperty("sender.host", Const.DEFAULT_HOST);
            int port = Optional.ofNullable(properties.getProperty("sender.port"))
                    .map(Integer::parseInt)
                    .orElse(Const.DEFAULT_PORT);
            init(host, port);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void init(String host, int port) throws Exception {
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

    public void submit(GamerLocation gamerLocation) throws InterruptedException {
        gamerLocationQueue.offer(gamerLocation, 10000, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        while (active()) {
            try {
                GamerLocation gamerLocation = gamerLocationQueue.take();

                try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream os = new ObjectOutputStream(bos)) {
                    os.writeObject(gamerLocation);
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
