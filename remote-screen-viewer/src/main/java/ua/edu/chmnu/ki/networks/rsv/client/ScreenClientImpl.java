package ua.edu.chmnu.ki.networks.rsv.client;


import ua.edu.chmnu.ki.networks.rsv.AppConfig;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.transport.DatagramUdpTransport;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public class ScreenClientImpl implements ScreenClient {

    private final AppConfig config;
    private final UdpTransport transport;
    private final ExecutorService executor;
    private final BlockingQueue<BufferedImage> frameQueue;
    private final FrameAssembler assembler;
    private final ClientWindow window;

    public ScreenClientImpl(AppConfig config) {
        this.config = config;
        this.transport = new DatagramUdpTransport();
        this.executor = Executors.newFixedThreadPool(2);
        this.frameQueue = new LinkedBlockingQueue<>(3);
        this.assembler = new FrameAssembler();
        this.window = new ClientWindow();
    }

    @Override
    public void start() throws Exception {
        transport.bind(new InetSocketAddress(0));
        window.show();

        InetSocketAddress serverAddress = new InetSocketAddress(config.host(), config.port());
        sendHello(serverAddress);

        executor.submit(new ClientReceiveWorker(
                transport,
                assembler,
                frameQueue,
                config.packetSize()
        ));

        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    BufferedImage image = frameQueue.take();
                    SwingUtilities.invokeLater(() -> window.getFramePanel().updateFrame(image));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    private void sendHello(InetSocketAddress serverAddress) throws Exception {
        String clientName = "swing-client";
        byte[] nameBytes = clientName.getBytes(StandardCharsets.UTF_8);
        byte[] hello = new byte[1 + nameBytes.length];
        hello[0] = PacketType.HELLO;
        System.arraycopy(nameBytes, 0, hello, 1, nameBytes.length);
        transport.send(hello, serverAddress);
    }

    @Override
    public void stop() {
        executor.shutdownNow();
        try {
            transport.close();
        } catch (Exception ignored) {
        }
    }
}
