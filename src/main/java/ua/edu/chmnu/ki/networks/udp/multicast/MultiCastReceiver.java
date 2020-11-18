package ua.edu.chmnu.ki.networks.udp.multicast;

import ua.edu.chmnu.ki.networks.common.NetworkUtils;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiCastReceiver implements Runnable {

    private final String group;
    private final int groupPort;
    private boolean active = true;
    private final int timeout;
    private final MulticastSocket socket;

    public MultiCastReceiver(String group, int groupPort, int timeout) throws IOException {
        this.group = group;
        this.groupPort = groupPort;
        List<InetAddress> addrList = NetworkUtils.getAvailableIPv4Adresses();
        if (addrList.isEmpty()) {
            throw new IOException("No available IP v4 interfaces");
        }
        this.socket = new MulticastSocket(groupPort);
        this.socket.setSoTimeout(this.timeout = timeout);
        this.socket.setInterface(addrList.get(0));
    }
    
    public MultiCastReceiver(String group, int groupPort) throws IOException {
        this(group, groupPort, 0);
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
    
    public int getTimeout() {
        return timeout;
    }

    @Override
    public void run() {
        try {
            InetAddress address = InetAddress.getByName(group);
            try (MulticastSocket clientSocket = this.socket) {
                clientSocket.joinGroup(address);
                byte buffer[] = new byte[1024 * 2];
                int count = 0;
                while (isActive() && Thread.currentThread().isAlive() && count < 100) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    try {
                        clientSocket.receive(packet);
                        String data = new String(buffer, 0, packet.getLength());
                        System.out.println("Received: " + data);
                    } catch (SocketTimeoutException ex) {
                        System.out.println("No packets");
                    }
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
