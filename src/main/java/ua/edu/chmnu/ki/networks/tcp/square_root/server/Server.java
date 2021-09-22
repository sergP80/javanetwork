
package ua.edu.chmnu.ki.networks.tcp.square_root.server;

import ua.edu.chmnu.ki.networks.tcp.ServerSessionHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Server implements Runnable {

    public static final String DEFAULT_HOST = "0.0.0.0";
    public static final int DEFAULT_PORT = 5558;
    public static final int DEFAULT_BACKLOG = 50;

    private final String host;
    private final int port;
    private final int backlog;
    private final ServerSocket serverSocket;

    private ExecutorService executor;
    private boolean active = true;
    private final ServerSessionHandler handler;

    public Server(String host, int port, int backlog, ServerSessionHandler handler) throws IOException {
        this.host = host;
        this.port = port;
        this.backlog = backlog;
        this.handler = handler;
        this.serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(host));        
    }
    
    public Server(ServerSessionHandler handler) throws IOException {
        this(DEFAULT_HOST, DEFAULT_PORT, DEFAULT_BACKLOG, handler);
    }

    @Override
    public void run() {
        System.out.printf("Server started at [%s:%d]\n", this.host, this.port);
        try(ServerSocket serverSocket = this.serverSocket) {
            while (isActive()) {
                Socket socket = serverSocket.accept();
                executor.submit(new ClientSession(socket, handler));
            }
        } catch (IOException ex) {
            if (this.serverSocket.isClosed())
            {
                System.out.println("Server socket was closed");
            } else {
                System.out.println("Unknown error: " + ex.getMessage());
            }            
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getBacklog() {
        return backlog;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) throws IOException {
        this.active = active;
        if (!this.active) {
            if (!this.serverSocket.isClosed()) {
                this.serverSocket.close();
            }
        }
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }
}
