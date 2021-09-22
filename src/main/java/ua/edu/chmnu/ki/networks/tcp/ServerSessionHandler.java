package ua.edu.chmnu.ki.networks.tcp;

import java.net.Socket;

@FunctionalInterface
public interface ServerSessionHandler {
    void handle(Socket socket) throws Exception;
}
