package ua.edu.chmnu.ki.networks.rsv.server.registry.factory;

import ua.edu.chmnu.ki.networks.rsv.server.registry.ClientRegistryService;
import ua.edu.chmnu.ki.networks.rsv.server.registry.RegistryConfig;

public interface ClientRegistryServiceFactory {
    ClientRegistryService fetchBy(RegistryConfig config);
}
