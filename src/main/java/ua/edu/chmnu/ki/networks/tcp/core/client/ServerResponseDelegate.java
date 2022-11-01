package ua.edu.chmnu.ki.networks.tcp.core.client;

@FunctionalInterface
public interface ServerResponseDelegate {
    void handle(Object response) throws Exception;
}
