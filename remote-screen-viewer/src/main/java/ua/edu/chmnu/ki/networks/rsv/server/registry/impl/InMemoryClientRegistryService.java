package ua.edu.chmnu.ki.networks.rsv.server.registry.impl;

import ua.edu.chmnu.ki.networks.rsv.protocol.ClientHello;
import ua.edu.chmnu.ki.networks.rsv.server.registry.ClientEntry;
import ua.edu.chmnu.ki.networks.rsv.server.registry.ClientRegistryService;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryClientRegistryService implements ClientRegistryService {

    private final Set<ClientEntry> clients = ConcurrentHashMap.newKeySet();

    @Override
    public void register(ClientHello clientHello, InetSocketAddress address) {
        clients.add(new ClientEntry(clientHello.clientName(), address));
    }

    @Override
    public Set<InetSocketAddress> getClients() {
        return Set.copyOf(clients.stream().map(ClientEntry::address).toList());
    }

    @Override
    public boolean isEmpty() {
        return clients.isEmpty();
    }


}
