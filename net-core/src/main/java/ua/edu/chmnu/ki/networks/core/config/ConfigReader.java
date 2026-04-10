package ua.edu.chmnu.ki.networks.core.config;



import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public interface ConfigReader {
    String read(String key, String defaultValue);

    default Integer readInt(String key, Integer defaultValue) {
        return Optional.ofNullable(read(key, defaultValue.toString()))
                .filter(StringUtils::isNotBlank)
                .map(Integer::parseInt)
                .orElse(defaultValue);
    }
}
