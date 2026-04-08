package ua.edu.chmnu.ki.networks.udp.core.receiver;

public interface MessageReceiver<T> extends AutoCloseable {

    T receive() throws Exception;

    @Override
    void close() throws RuntimeException;
}
