package ua.edu.chmnu.ki.networks.udp.multicast;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiCastSender implements Runnable {

    @Getter
    private final String group;
    @Getter
    private final int groupPort;
    @Setter
    @Getter
    private boolean active = true;
    private final MulticastSocket socket;
    @Getter
    private MultiCastDataAction action;

    public MultiCastSender(String group, int groupPort) throws IOException {
        this.group = group;
        this.groupPort = groupPort;
        this.socket = new MulticastSocket();
    }

    @Override
    public void run() {
        try {
            InetAddress address = InetAddress.getByName(group);
            try (MulticastSocket sender = this.socket) {
                System.out.println("Multicast sender started");
                while (this.action != null && isActive()) {
                    byte[] data = action.getBytes();
                    DatagramPacket packet = 
                            new DatagramPacket(data, data.length, 
                                               address, this.groupPort);
                    sender.send(packet);
                    System.out.println("Sent: " + new String(data));
                    Thread.sleep(100);
                }
            } catch (IOException ex) {
                if (this.socket.isClosed())
                {
                    System.out.println("Closed sender");
                } else {
                    Logger.getLogger(MultiCastSender.class.getName()).log(Level.SEVERE, null, ex);
                }                
            } catch (InterruptedException ex) {
                Logger.getLogger(MultiCastSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(MultiCastSender.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.active = false;
        }
    }

    public MultiCastSender setAction(MultiCastDataAction action) {
        this.action = action;
        return this;
    }
          
}
