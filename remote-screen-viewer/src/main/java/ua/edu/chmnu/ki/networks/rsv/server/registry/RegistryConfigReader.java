package ua.edu.chmnu.ki.networks.rsv.server.registry;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegistryConfigReader {

    public static RegistryConfig readFromEnv() {
        String registryType = System.getenv().getOrDefault("REGISTRY_TYPE", "IN_MEMORY");

        String registryName = System.getenv().getOrDefault("REGISTRY_NAME", "");

        return new RegistryConfig(RegistryType.valueOf(registryType), registryName);
    }
}
