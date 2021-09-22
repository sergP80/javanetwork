package ua.edu.chmnu.ki.networks.tcp;

@FunctionalInterface
public interface ServerResponseHandler {
    void handle(Object response) throws Exception;
}
