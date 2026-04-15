package ua.edu.chmnu.ki.networks.rsv.client;


import ua.edu.chmnu.ki.networks.rsv.AppConfig;
import ua.edu.chmnu.ki.networks.rsv.common.AppRunner;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.transport.DatagramUdpTransport;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ScreenClient implements AppRunner {

    private final AppConfig config;
    private final UdpTransport transport;
    private final ExecutorService executor;
    private final BlockingQueue<BufferedImage> frameQueue;
    private final FrameAssembler assembler;
    private final ClientWindow window;
    private final ConnectionMonitor connectionMonitor;

    public ScreenClient(AppConfig config) {
        this.config = config;
        this.transport = new DatagramUdpTransport();
        this.executor = Executors.newFixedThreadPool(3);
        this.frameQueue = new LinkedBlockingQueue<>(3);
        this.assembler = new FrameAssembler();
        this.window = new ClientWindow();
        this.connectionMonitor = new HeartbeatConnectionMonitor(5000);
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
                config.packetSize(),
                connectionMonitor
        ));

        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    BufferedImage image = frameQueue.take();
                    SwingUtilities.invokeLater(() -> window.updateFrame(image));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        executor.submit(new ClientConnectionWatchdogWorker(
                connectionMonitor,
                this::shutdownClient,
                1000
        ));

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

    private void shutdownClient() {
        System.out.println("Server is unavailable. Closing client...");
        stop();

        SwingUtilities.invokeLater(() -> {
            for (Window openedWindow : Window.getWindows()) {
                openedWindow.dispose();
            }
            System.exit(0);
        });
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
