package ua.edu.chmnu.net.udp.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiCastReceiver implements Runnable {

    private final String group;
    private final int groupPort;
    private boolean active = true;
    private final MulticastSocket socket;

    public MultiCastReceiver(String group, int groupPort) throws IOException {
        this.group = group;
        this.groupPort = groupPort;
        this.socket = new MulticastSocket(groupPort);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getGroup() {
        return group;
    }

    public int getGroupPort() {
        return groupPort;
    }

    @Override
    public void run() {
        try {
            InetAddress address = InetAddress.getByName(group);
            try (MulticastSocket clientSocket = this.socket) {
                clientSocket.joinGroup(address);
                byte buffer[] = new byte[1024*2];
                int count = 0;
                while(isActive() && Thread.currentThread().isAlive() && count < 100)
                {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    clientSocket.receive(packet);
                    String data = new String(buffer, 0, buffer.length);
                    System.out.println("Received: " + data);
                    Thread.sleep(100);
                    ++count;
                }
                clientSocket.leaveGroup(address);
            } catch (IOException ex) {
                Logger.getLogger(MultiCastReceiver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(MultiCastReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(MultiCastReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
