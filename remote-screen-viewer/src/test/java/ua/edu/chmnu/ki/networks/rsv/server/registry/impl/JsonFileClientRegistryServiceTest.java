package ua.edu.chmnu.ki.networks.rsv.server.registry.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import ua.edu.chmnu.ki.networks.converter.InetSocketAddressConverter;
import ua.edu.chmnu.ki.networks.rsv.server.registry.ClientRegistryService;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonFileClientRegistryServiceTest {

    private static final String JSON_REGISTRY_NAME = "json/client-registry.json";

    private static final Path JSON_REGISTRY_PATH;

    static {
        try {
            JSON_REGISTRY_PATH = getResourcePath(JSON_REGISTRY_NAME);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private final ClientRegistryService service = new JsonFileClientRegistryService(JSON_REGISTRY_PATH);

    @Test
    void shouldCheckNonEmptyRegistry() {
        assertFalse(service.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "127.0.0.1:59382",
            "127.0.0.1:50009",
    })
    void shouldContainsClientByAddress(@ConvertWith(InetSocketAddressConverter.class) InetSocketAddress address) {
        Set<InetSocketAddress> clients = service.getClients();

        Optional<InetSocketAddress> result = clients.stream().filter(i -> i.equals(address)).findFirst();

        assertTrue(result.isPresent());

        assertEquals(address, result.get());
    }

    private static Path getResourcePath(String resourcePath) throws URISyntaxException {
        URL url = JsonFileClientRegistryServiceTest.class.getClassLoader().getResource(resourcePath);

        if (url == null) {
            throw new IllegalStateException("Non-existed resource: " + resourcePath);
        }

        return Paths.get(url.toURI());
    }
}