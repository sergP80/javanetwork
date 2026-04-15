package ua.edu.chmnu.ki.networks.rsv.server;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientRegistryImpl implements ClientRegistry {

    private final Set<InetSocketAddress> clients = ConcurrentHashMap.newKeySet();

    @Override
    public void register(InetSocketAddress address) {
        clients.add(address);
    }

    @Override
    public Set<InetSocketAddress> getClients() {
        return Set.copyOf(clients);
    }

    @Override
    public boolean isEmpty() {
        return clients.isEmpty();
    }
}
