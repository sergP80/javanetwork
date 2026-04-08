package ua.edu.chmnu.ki.networks.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

public record EndPoint(String host, int port) {

    public InetAddress getInetAddress() throws UnknownHostException {
        return InetAddress.getByName(host);
    }

    public static EndPoint parseString(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        String[] parts = s.split(":");

        if (parts.length < 2) {
            throw new IllegalArgumentException(s);
        }
        return new EndPoint(parts[0], Integer.parseInt(parts[1]));
    }
}
