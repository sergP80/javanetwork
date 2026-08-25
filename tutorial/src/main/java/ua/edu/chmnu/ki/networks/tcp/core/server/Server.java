
package ua.edu.chmnu.ki.networks.tcp.core.server;

import lombok.Getter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Server implements Runnable {

    public static final String DEFAULT_HOST = "0.0.0.0";
    public static final int DEFAULT_PORT = 5558;
    public static final int DEFAULT_BACKLOG = 50;

    @Getter
    private final String host;
    @Getter
    private final int port;
    @Getter
    private final int backlog;
    @Getter
    private final ServerSocket serverSocket;

    @Getter
    private final ExecutorService executor;
    @Getter
    private boolean active = true;
    private final ClientSessionDelegate handler;

    public Server(String host, int port, int backlog, ClientSessionDelegate handler, ExecutorService executor) throws IOException {
        this.host = host;
        this.port = port;
        this.backlog = backlog;
        this.handler = handler;
        this.executor = executor;
        this.serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(host));        
    }
    
    public Server(ClientSessionDelegate handler, ExecutorService executor) throws IOException {
        this.host = System.getProperty("server.host", DEFAULT_HOST);
        this.port = Integer.parseInt(System.getProperty("server.port", "" + DEFAULT_PORT));
        this.backlog = Integer.parseInt(System.getProperty("server.back-log-size", "" + DEFAULT_BACKLOG));
        this.handler = handler;
        this.executor = executor;
        this.serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(host));
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

    public void setActive(boolean active) throws IOException {
        this.active = active;
        if (!this.active) {
            if (!this.serverSocket.isClosed()) {
                this.serverSocket.close();
            }
        }
    }

}
