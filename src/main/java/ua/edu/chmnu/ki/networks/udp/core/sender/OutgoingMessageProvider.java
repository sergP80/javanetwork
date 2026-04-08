package ua.edu.chmnu.ki.networks.udp.core.sender;

public interface OutgoingMessageProvider<T> {

    T provide() throws Exception;
}
