package ua.edu.chmnu.ki.networks.rsv.server;


import ua.edu.chmnu.ki.networks.rsv.AppConfig;
import ua.edu.chmnu.ki.networks.rsv.codec.JpegFrameEncoder;
import ua.edu.chmnu.ki.networks.rsv.transport.DatagramUdpTransport;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScreenServerImpl implements ScreenServer {

    private final AppConfig config;
    private final ExecutorService executor;
    private final UdpTransport transport;
    private final ClientRegistry clientRegistry;

    public ScreenServerImpl(AppConfig config) {
        this.config = config;
        this.executor = Executors.newFixedThreadPool(2);
        this.transport = new DatagramUdpTransport();
        this.clientRegistry = new ClientRegistry();
    }

    @Override
    public void start() throws Exception {
        transport.bind(new InetSocketAddress(config.host(), config.port()));

        ScreenCaptureService captureService = new ScreenCaptureService();
        JpegFrameEncoder encoder = new JpegFrameEncoder();

        executor.submit(new ServerListenerWorker(
                transport,
                clientRegistry,
                config.packetSize()
        ));

        executor.submit(new FrameBroadcastWorker(
                transport,
                clientRegistry,
                captureService,
                encoder,
                config.fps(),
                config.jpegQuality(),
                config.packetSize()
        ));

        executor.submit(new HeartbeatWorker(transport, clientRegistry, config.heartBeatInterval()));

        System.out.println("UDP screen server started on " + config.host() + ":" + config.port());
        System.out.println("Press Ctrl+C to stop.");

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

        while (!Thread.currentThread().isInterrupted()) {
            Thread.sleep(1000);
        }
    }

    @Override
    public void stop() {
        executor.shutdownNow();
        try {
            transport.close();
        } catch (Exception ignored) {
        }
        System.out.println("Server stopped.");
    }
}
