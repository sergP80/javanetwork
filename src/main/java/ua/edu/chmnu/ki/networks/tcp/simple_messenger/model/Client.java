package ua.edu.chmnu.ki.networks.tcp.simple_messenger.model;

import java.io.Serializable;
import java.net.Socket;

public record Client(ClientView clientView, Socket socket) implements Serializable, Comparable<Client> {
    public Long getId() {
        return clientView.id();
    }

    @Override
    public int compareTo(Client o) {
        return clientView.compareTo(o.clientView);
    }
}
