package ua.edu.chmnu.ki.networks.rsv.server.registry.impl;

import lombok.SneakyThrows;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;
import ua.edu.chmnu.ki.networks.rsv.protocol.ClientHello;
import ua.edu.chmnu.ki.networks.rsv.server.registry.ClientEntry;
import ua.edu.chmnu.ki.networks.rsv.server.registry.ClientRegistryService;

import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class JsonFileClientRegistryService implements ClientRegistryService {

    private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

    private final Path jsonFilePath;

    public JsonFileClientRegistryService(String filPath) {
        this.jsonFilePath = Paths.get(filPath);
    }

    public JsonFileClientRegistryService(Path path) {
        this.jsonFilePath = path;
    }

    @SneakyThrows
    @Override
    public void register(ClientHello clientHello, InetSocketAddress address) {

        List<ClientEntry> clientEntries = new ArrayList<>();

        if (Files.exists(jsonFilePath)) {
            clientEntries = JSON_MAPPER.readValue(jsonFilePath, new TypeReference<>() {
            });
        }

        ClientEntry clientEntry = new ClientEntry(clientHello.clientName(), address);

        clientEntries.add(clientEntry);

        JSON_MAPPER.writeValue(jsonFilePath, clientEntries);
    }

    @Override
    public Collection<ClientEntry> getClients() {
        if (!Files.exists(jsonFilePath)) {
            return Set.of();
        }

        List<ClientEntry> clientEntries = JSON_MAPPER.readValue(jsonFilePath, new TypeReference<>() {
        });

        return Set.copyOf(clientEntries);
    }

    @Override
    public boolean isEmpty() {
        if (!Files.exists(jsonFilePath)) {
            return false;
        }

        List<ClientEntry> clientEntries = JSON_MAPPER.readValue(jsonFilePath, new TypeReference<>() {
        });

        return clientEntries == null || clientEntries.isEmpty();
    }
}
