package ua.edu.chmnu.ki.networks.rsv.server.service.impl;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.server.registry.ClientEntry;
import ua.edu.chmnu.ki.networks.rsv.server.registry.ClientRegistryService;
import ua.edu.chmnu.ki.networks.rsv.server.service.ClientBroadcastService;
import ua.edu.chmnu.ki.networks.rsv.transport.UdpTransport;

import java.io.IOException;
import java.util.Collection;

@AllArgsConstructor
public class ClientBroadcastServiceImpl implements ClientBroadcastService {

    private final ClientRegistryService clientRegistryService;

    private final UdpTransport transport;

    @Override
    public void broadcast(byte[] payload) throws IOException {
        Collection<ClientEntry> clients = clientRegistryService.getClients();

        for (ClientEntry client : clients) {
            transport.send(payload, client.address());
        }
    }
}
