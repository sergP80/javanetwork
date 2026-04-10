package ua.edu.chmnu.ki.networks.rsv.server;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

@AllArgsConstructor
public class HeartbeatWorker implements Runnable {

    private final UdpTransport transport;
    private final ClientRegistry clientRegistry;
    private final long intervalMillis;

    @Override
    public void run() {
        byte[] heartbeat = new byte[]{PacketType.HEARTBEAT};

        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (var client : clientRegistry.getClients()) {
                    transport.send(heartbeat, client);
                }

                Thread.sleep(intervalMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                if (!Thread.currentThread().isInterrupted()) {
                    System.err.println("Heartbeat error: " + e.getMessage());
                }
            }
        }
    }
}
