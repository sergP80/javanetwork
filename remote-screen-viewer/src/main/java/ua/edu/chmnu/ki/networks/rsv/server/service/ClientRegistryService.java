package ua.edu.chmnu.ki.networks.rsv.server.service;

import java.net.InetSocketAddress;
import java.util.Set;

public interface ClientRegistryService {

    void register(InetSocketAddress address);

    Set<InetSocketAddress> getClients();

    boolean isEmpty();
}
