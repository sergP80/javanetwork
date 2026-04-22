package ua.edu.chmnu.ki.networks.rsv.server.registry.factory;

import ua.edu.chmnu.ki.networks.rsv.server.registry.ClientRegistryService;
import ua.edu.chmnu.ki.networks.rsv.server.registry.RegistryConfig;
import ua.edu.chmnu.ki.networks.rsv.server.registry.RegistryType;
import ua.edu.chmnu.ki.networks.rsv.server.registry.impl.InMemoryClientRegistryService;
import ua.edu.chmnu.ki.networks.rsv.server.registry.impl.JsonFileClientRegistryService;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ClientRegistryServiceFactoryImpl implements ClientRegistryServiceFactory {

    private static final Map<RegistryType, Function<RegistryConfig, ClientRegistryService>> REGISTRY_BUILD_MAPPER = Map.of(
            RegistryType.IN_MEMORY, config -> new InMemoryClientRegistryService(),
            RegistryType.JSON_FILE, config -> new JsonFileClientRegistryService(config.registryName())
    );

    @Override
    public ClientRegistryService fetchBy(RegistryConfig config) {
        RegistryType type = config.type();

        Function<RegistryConfig, ClientRegistryService> builder = Optional.ofNullable(REGISTRY_BUILD_MAPPER.get(type))
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported registry type: " + type));


        return builder.apply(config);
    }
}
