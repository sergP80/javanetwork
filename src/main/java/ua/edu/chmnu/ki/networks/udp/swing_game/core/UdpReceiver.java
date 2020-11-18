package ua.edu.chmnu.ki.networks.udp.swing_game.core;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Gamer;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.GamerLeave;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.GamerLocation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.TimeUnit;

public class UdpReceiver implements Runnable {
    private DatagramSocket socket;

    private Boolean active = true;

    private final GamerPool gamerPool;

    public UdpReceiver(GamerPool gamerPool) {
        try {
            init();
            this.gamerPool = gamerPool;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void init() throws Exception {
        this.socket = new DatagramSocket();
        System.out.println("Used port [" + this.socket.getLocalPort() + "]");
    }

    public Boolean active() {
        return active;
    }

    public void active(Boolean active) {
        this.active = active;
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
                    Object data = is.readObject();
                    if (data instanceof Gamer) {
                        gamerPool.add((Gamer)data);
                    } else if (data instanceof GamerLocation) {
                        GamerLocation location = (GamerLocation)data;
                        gamerPool.updateLocation(location.getGamerId(), location.getPosition());
                    } else if (data instanceof GamerLeave) {
                        GamerLeave leave = (GamerLeave) data;
                        gamerPool.remove(leave.getGamerId());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
