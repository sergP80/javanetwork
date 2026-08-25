package ua.edu.chmnu.ki.networks.chat.server;

import ua.edu.chmnu.ki.networks.chat.server.broadcast.ChatBroadcaster;
import ua.edu.chmnu.ki.networks.chat.server.broadcast.DefaultChatBroadcaster;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatServer {

    private final ServerConfig config;
    private final ClientRegistry clientRegistry;
    private final ChatBroadcaster broadcaster;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public ChatServer(ServerConfig config) {
        this.config = config;
        this.clientRegistry = new ClientRegistry();
        this.broadcaster = new DefaultChatBroadcaster(clientRegistry);
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(config.threadPoolSize());

        try (ServerSocket serverSocket = new ServerSocket(config.port())) {
            System.out.println("Server started on port " + config.port());

            while (running.get()) {
                Socket socket = serverSocket.accept();
                executor.submit(new ChatClientConnection(socket, clientRegistry, broadcaster, running));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to start server", e);
        } finally {
            executor.shutdown();
        }
    }
}
