package ua.edu.chmnu.ki.networks.rsv.server;

import java.net.InetSocketAddress;
import java.util.Set;

public interface ClientRegistry {

    void register(InetSocketAddress address);

    Set<InetSocketAddress> getClients();

    boolean isEmpty();
}
