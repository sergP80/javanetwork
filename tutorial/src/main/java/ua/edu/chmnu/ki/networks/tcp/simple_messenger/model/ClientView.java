package ua.edu.chmnu.ki.networks.tcp.simple_messenger.model;

import java.io.Serializable;

public record ClientView(Long id, String nick, Object meta) implements Serializable, Comparable<ClientView> {

    @Override
    public int compareTo(ClientView o) {
        if (id < o.id) {
            return -1;
        }

        if (id > o.id) {
            return 1;
        }

        return nick.compareTo(o.nick);
    }
}
