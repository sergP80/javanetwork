package ua.edu.chmnu.ki.networks.rsv.server.registry;

import ua.edu.chmnu.ki.networks.rsv.protocol.ClientHello;

import java.net.InetSocketAddress;
import java.util.Set;

public interface ClientRegistryService {

    void register(ClientHello clientHello, InetSocketAddress address);

    Set<InetSocketAddress> getClients();

    boolean isEmpty();
}
