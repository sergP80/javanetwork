package ua.edu.chmnu.ki.networks.tcp.core.server;

import java.net.Socket;

@FunctionalInterface
public interface ClientSessionDelegate {
    void handle(Socket socket) throws Exception;
}
