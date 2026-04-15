package ua.edu.chmnu.ki.networks.rsv.server.service.impl;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.server.service.ClientBroadcastService;
import ua.edu.chmnu.ki.networks.rsv.server.service.ClientRegistryService;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Set;

@AllArgsConstructor
public class ClientBroadcastServiceImpl implements ClientBroadcastService {

    private final ClientRegistryService clientRegistryService;

    private final UdpTransport transport;

    @Override
    public void broadcast(byte[] payload) throws IOException {
        Set<InetSocketAddress> clients = clientRegistryService.getClients();

        for (InetSocketAddress client: clients) {
            transport.send(payload, client);
        }
    }
}
