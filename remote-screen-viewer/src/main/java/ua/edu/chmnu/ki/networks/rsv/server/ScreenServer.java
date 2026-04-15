package ua.edu.chmnu.ki.networks.rsv.server;


import ua.edu.chmnu.ki.networks.rsv.AppConfig;
import ua.edu.chmnu.ki.networks.rsv.codec.FrameEncoder;
import ua.edu.chmnu.ki.networks.rsv.codec.FrameEncoderFactory;
import ua.edu.chmnu.ki.networks.rsv.codec.FrameEncoderFactoryImpl;
import ua.edu.chmnu.ki.networks.rsv.common.AppRunner;
import ua.edu.chmnu.ki.networks.rsv.deserializer.PacketDeserializeFactoryImpl;
import ua.edu.chmnu.ki.networks.rsv.serializer.PacketSerializeFactoryImpl;
import ua.edu.chmnu.ki.networks.rsv.server.service.ClientBroadcastService;
import ua.edu.chmnu.ki.networks.rsv.server.service.ClientRegistryService;
import ua.edu.chmnu.ki.networks.rsv.server.service.ScreenBroadcastService;
import ua.edu.chmnu.ki.networks.rsv.server.service.impl.ClientBroadcastServiceImpl;
import ua.edu.chmnu.ki.networks.rsv.server.service.impl.ClientRegistryServiceImpl;
import ua.edu.chmnu.ki.networks.rsv.server.service.impl.ScreenBroadcastServiceImpl;
import ua.edu.chmnu.ki.networks.rsv.server.service.impl.ScreenServiceImpl;
import ua.edu.chmnu.ki.networks.rsv.transport.DatagramUdpTransport;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScreenServer implements AppRunner {

    private final AppConfig config;
    private final ExecutorService executor;
    private final UdpTransport transport;
    private final ClientRegistryService clientRegistryService;
    private final FrameEncoderFactory frameEncoderFactory;

    public ScreenServer(AppConfig config) {
        this.config = config;
        this.executor = Executors.newFixedThreadPool(4);
        this.transport = new DatagramUdpTransport();
        this.clientRegistryService = new ClientRegistryServiceImpl();
        this.frameEncoderFactory = new FrameEncoderFactoryImpl();
    }

    @Override
    public void start() throws Exception {
        transport.bind(new InetSocketAddress(config.host(), config.port()));

        FrameEncoder frameEncoder = frameEncoderFactory.fetchBy(config.encoderType(), config.quality());

        ClientBroadcastService clientBroadcastService = new ClientBroadcastServiceImpl(this.clientRegistryService, this.transport);

        executor.submit(new ServerListenerWorker(
                transport,
                clientRegistryService,
                new PacketDeserializeFactoryImpl(),
                config.packetSize()
        ));

        ScreenBroadcastService screenBroadcastService = new ScreenBroadcastServiceImpl(
                new ScreenServiceImpl(),
                frameEncoder,
                clientBroadcastService,
                new PacketSerializeFactoryImpl(),
                config.packetSize()
        );

        executor.submit(new FrameBroadcastWorker(
                clientRegistryService,
                screenBroadcastService,
                config.fps()
        ));

        executor.submit(new HeartbeatWorker(
                        clientBroadcastService,
                        new PacketSerializeFactoryImpl(),
                        config.heartBeatInterval()
                )
        );

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
