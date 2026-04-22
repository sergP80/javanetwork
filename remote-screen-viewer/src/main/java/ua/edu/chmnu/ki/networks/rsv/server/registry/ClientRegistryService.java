package ua.edu.chmnu.ki.networks.rsv.server.registry;

import ua.edu.chmnu.ki.networks.rsv.protocol.ClientHello;

import java.net.InetSocketAddress;
import java.util.Collection;

public interface ClientRegistryService {

    void register(ClientHello clientHello, InetSocketAddress address);

    Collection<ClientEntry> getClients();

    boolean isEmpty();
}
