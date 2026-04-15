package ua.edu.chmnu.ki.networks.rsv.client.worker;

import ua.edu.chmnu.ki.networks.rsv.client.monitor.ConnectionMonitor;
import ua.edu.chmnu.ki.networks.rsv.client.service.ClientScreenAssemblyService;
import ua.edu.chmnu.ki.networks.rsv.deserializer.PacketDeserializeFactory;
import ua.edu.chmnu.ki.networks.rsv.deserializer.PacketDeserializer;
import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunk;
import ua.edu.chmnu.ki.networks.rsv.protocol.HeartBeat;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import java.awt.image.BufferedImage;
import java.net.DatagramPacket;
import java.util.concurrent.BlockingQueue;

public class ClientReceiveWorker implements Runnable {

    private final ClientScreenAssemblyService clientScreenAssemblyService;
    private final PacketDeserializeFactory deserializeFactory;
    private final UdpTransport transport;
    private final BlockingQueue<BufferedImage> frameQueue;
    private final ConnectionMonitor connectionMonitor;
    private final int packetSize;

    public ClientReceiveWorker(ClientScreenAssemblyService clientScreenAssemblyService,
                               PacketDeserializeFactory deserializeFactory,
                               UdpTransport transport,
                               BlockingQueue<BufferedImage> frameQueue,
                               ConnectionMonitor connectionMonitor,
                               int packetSize) {
        this.clientScreenAssemblyService = clientScreenAssemblyService;
        this.deserializeFactory = deserializeFactory;
        this.transport = transport;
        this.frameQueue = frameQueue;
        this.connectionMonitor = connectionMonitor;
        this.packetSize = packetSize;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                DatagramPacket packet = transport.receive(packetSize);

                PacketDeserializer deserializer = deserializeFactory.fetchBy(packet);

                PacketFrame packetFrame = deserializer.deserialize();

                if (packetFrame instanceof HeartBeat) {
                    connectionMonitor.onHeartbeat();
                    continue;
                }

                if (packetFrame instanceof FrameChunk chunk) {
                    BufferedImage image = clientScreenAssemblyService.tryToCompleteWith(chunk);

                    if (image != null) {
                        connectionMonitor.onFrameReceived();
                        frameQueue.offer(image);
                    }
                }


//                ByteBuffer buffer = ByteBuffer.wrap(packet.getData(), 0, packet.getLength());
//
//                PacketType packetType = PacketType.of(buffer.get());
//
//                if (packetType == PacketType.HEARTBEAT) {
//                    connectionMonitor.onHeartbeat();
//                    continue;
//                }
//
//                if (packetType != PacketType.FRAME_CHUNK) {
//                    continue;
//                }
//
//                int frameId = buffer.getInt();
//                int totalChunks = buffer.getInt();
//                int chunkIndex = buffer.getInt();
//                int totalBytes = buffer.getInt();
//                int payloadLength = buffer.getInt();
//
//                byte[] payload = new byte[payloadLength];
//                buffer.get(payload);
//
//                byte[] frameBytes = assembler.accept(
//                        frameId,
//                        totalChunks,
//                        chunkIndex,
//                        totalBytes,
//                        payload
//                );
//
//                if (frameBytes != null) {
//                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(frameBytes));
//                    if (image != null) {
//                        connectionMonitor.onFrameReceived();
//                        frameQueue.offer(image);
//                    }
//                }
            } catch (Exception e) {
                if (!Thread.currentThread().isInterrupted()) {
                    System.err.println("Client receive error: " + e.getMessage());
                }
            }
        }
    }
}
