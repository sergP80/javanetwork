package ua.edu.chmnu.ki.networks.udp.core.sender;

public interface MessageSender<T> extends AutoCloseable {

    void send(T message) throws Exception;

    @Override
    void close() throws RuntimeException;
}
