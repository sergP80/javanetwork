package ua.edu.chmnu.ki.networks.rsv.client;


import ua.edu.chmnu.ki.networks.rsv.AppConfig;
import ua.edu.chmnu.ki.networks.rsv.client.assembly.FrameAssembler;
import ua.edu.chmnu.ki.networks.rsv.client.assembly.FrameAssemblerImpl;
import ua.edu.chmnu.ki.networks.rsv.client.gui.ClientWindow;
import ua.edu.chmnu.ki.networks.rsv.client.monitor.ConnectionMonitor;
import ua.edu.chmnu.ki.networks.rsv.client.monitor.HeartbeatConnectionMonitor;
import ua.edu.chmnu.ki.networks.rsv.client.service.ClientScreenAssemblyService;
import ua.edu.chmnu.ki.networks.rsv.client.service.HelloService;
import ua.edu.chmnu.ki.networks.rsv.client.service.impl.ClientScreenAssemblyServiceImpl;
import ua.edu.chmnu.ki.networks.rsv.client.service.impl.HelloServiceImpl;
import ua.edu.chmnu.ki.networks.rsv.client.worker.ClientConnectionWatchdogWorker;
import ua.edu.chmnu.ki.networks.rsv.client.worker.ClientReceiveWorker;
import ua.edu.chmnu.ki.networks.rsv.common.AppRunner;
import ua.edu.chmnu.ki.networks.rsv.deserializer.PacketDeserializeFactoryImpl;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializeFactoryImpl;
import ua.edu.chmnu.ki.networks.rsv.transport.DatagramUdpTransport;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetSocketAddress;
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
    private final HelloService helloService;

    public ScreenClient(AppConfig config) {
        this.config = config;
        this.transport = new DatagramUdpTransport();
        this.executor = Executors.newFixedThreadPool(4);
        this.frameQueue = new LinkedBlockingQueue<>(3);
        this.assembler = new FrameAssemblerImpl();
        this.window = new ClientWindow(this);
        this.connectionMonitor = new HeartbeatConnectionMonitor(5000);
        this.helloService = new HelloServiceImpl(transport, new PacketSerializeFactoryImpl());
    }

    @Override
    public void start() throws Exception {
        transport.bind(new InetSocketAddress(0));

        InetSocketAddress serverAddress = new InetSocketAddress(config.host(), config.port());

        if (!helloService.sendTo(serverAddress)) {
            return;
        }

        window.show();

        ClientScreenAssemblyService clientScreenAssemblyService = new ClientScreenAssemblyServiceImpl(assembler);

        executor.submit(new ClientReceiveWorker(
                clientScreenAssemblyService,
                new PacketDeserializeFactoryImpl(),
                transport,
                frameQueue,
                connectionMonitor,
                config.packetSize()
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
                window,
                1000
        ));

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    @Override
    public void stop() {
        executor.shutdownNow();
        try {
            transport.close();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void restart() throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(config.host(), config.port());

        helloService.sendTo(serverAddress);
    }
}
