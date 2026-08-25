package ua.edu.chmnu.ki.networks.udp.core.receiver;

public interface MessageHandler<T> {

    void handle(T message);
}
