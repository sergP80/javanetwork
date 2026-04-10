package ua.edu.chmnu.ki.networks.core.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class EnvConfigReader implements ConfigReader {

    @Override
    public String read(String key, String defaultValue) {
        return Optional.ofNullable(System.getenv(key))
                .filter(StringUtils::isNotBlank)
                .orElse(defaultValue);
    }
}
