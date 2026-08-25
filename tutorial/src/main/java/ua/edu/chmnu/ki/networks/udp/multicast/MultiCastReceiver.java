package ua.edu.chmnu.ki.networks.udp.multicast;

import lombok.Getter;
import lombok.Setter;
import ua.edu.chmnu.ki.networks.utils.NetworkUtils;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiCastReceiver implements Runnable {

    @Getter
    private final String group;
    @Getter
    private final int groupPort;
    @Setter
    @Getter
    private boolean active = true;
    @Getter
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

    @Override
    public void run() {
        try {
            InetAddress address = InetAddress.getByName(group);
            try (MulticastSocket clientSocket = this.socket) {
                clientSocket.joinGroup(address);
                byte[] buffer = new byte[1024 * 2];
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
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(MultiCastReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(MultiCastReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
