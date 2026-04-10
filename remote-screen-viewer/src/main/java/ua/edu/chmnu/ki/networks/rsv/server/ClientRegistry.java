package ua.edu.chmnu.ki.networks.rsv.server;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientRegistry {

    private final Set<InetSocketAddress> clients = ConcurrentHashMap.newKeySet();

    public void register(InetSocketAddress address) {
        clients.add(address);
    }

    public Set<InetSocketAddress> getClients() {
        return Set.copyOf(clients);
    }

    public boolean isEmpty() {
        return clients.isEmpty();
    }
}
