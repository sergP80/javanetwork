package ua.edu.chmnu.net.udp.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiCastSender implements Runnable {

    private final String group;
    private final int groupPort;
    private boolean active = true;
    private final DatagramSocket socket;
    private MultiCastDataAction action;

    public MultiCastSender(String group, int groupPort) throws SocketException {
        this.group = group;
        this.groupPort = groupPort;
        this.socket = new DatagramSocket();
    }

    public String getGroup() {
        return group;
    }

    public int getGroupPort() {
        return groupPort;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;        
    }

    @Override
    public void run() {
        try {
            InetAddress address = InetAddress.getByName(group);
            try (DatagramSocket sender = this.socket) {
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
    
     public MultiCastDataAction getAction() {
        return action;
    }

    public MultiCastSender setAction(MultiCastDataAction action) {
        this.action = action;
        return this;
    }
          
}
