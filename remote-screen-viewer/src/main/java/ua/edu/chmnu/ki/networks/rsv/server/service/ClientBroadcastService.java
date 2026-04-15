package ua.edu.chmnu.ki.networks.rsv.server.service;

import java.io.IOException;

public interface ClientBroadcastService {

    void broadcast(byte[] payload) throws IOException;
}
