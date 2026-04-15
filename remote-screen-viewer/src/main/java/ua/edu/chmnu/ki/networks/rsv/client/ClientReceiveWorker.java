package ua.edu.chmnu.ki.networks.rsv.client;

import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;

public class ClientReceiveWorker implements Runnable {

    private final UdpTransport transport;
    private final FrameAssembler assembler;
    private final BlockingQueue<BufferedImage> frameQueue;
    private final int packetSize;
    private final ConnectionMonitor connectionMonitor;

    public ClientReceiveWorker(UdpTransport transport,
                               FrameAssembler assembler,
                               BlockingQueue<BufferedImage> frameQueue,
                               int packetSize, ConnectionMonitor connectionMonitor) {
        this.transport = transport;
        this.assembler = assembler;
        this.frameQueue = frameQueue;
        this.packetSize = packetSize;
        this.connectionMonitor = connectionMonitor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                DatagramPacket packet = transport.receive(packetSize);
                ByteBuffer buffer = ByteBuffer.wrap(packet.getData(), 0, packet.getLength());

                PacketType packetType = PacketType.of(buffer.get());

                if (packetType == PacketType.HEARTBEAT) {
                    connectionMonitor.onHeartbeat();
                    continue;
                }

                if (packetType != PacketType.FRAME_CHUNK) {
                    continue;
                }

                int frameId = buffer.getInt();
                int totalChunks = buffer.getInt();
                int chunkIndex = buffer.getInt();
                int totalBytes = buffer.getInt();
                int payloadLength = buffer.getInt();

                byte[] payload = new byte[payloadLength];
                buffer.get(payload);

                byte[] frameBytes = assembler.accept(
                        frameId,
                        totalChunks,
                        chunkIndex,
                        totalBytes,
                        payload
                );

                if (frameBytes != null) {
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(frameBytes));
                    if (image != null) {
                        connectionMonitor.onFrameReceived();
                        frameQueue.offer(image);
                    }
                }
            } catch (Exception e) {
                if (!Thread.currentThread().isInterrupted()) {
                    System.err.println("Client receive error: " + e.getMessage());
                }
            }
        }
    }
}
