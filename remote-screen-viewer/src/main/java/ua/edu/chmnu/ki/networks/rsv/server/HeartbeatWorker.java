package ua.edu.chmnu.ki.networks.rsv.server;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.protocol.HeartBeat;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializeFactory;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializer;
import ua.edu.chmnu.ki.networks.rsv.server.service.ClientBroadcastService;

import java.time.LocalDateTime;

@AllArgsConstructor
public class HeartbeatWorker implements Runnable {

    private final ClientBroadcastService clientBroadcastService;

    private final PacketSerializeFactory packetSerializeFactory;

    private final long intervalMillis;

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {

                HeartBeat heartBeat = new HeartBeat(LocalDateTime.now());

                PacketSerializer serializer = packetSerializeFactory.fetchBy(heartBeat);

                byte[] serializedPayload = serializer.serialize();

                clientBroadcastService.broadcast(serializedPayload);

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
