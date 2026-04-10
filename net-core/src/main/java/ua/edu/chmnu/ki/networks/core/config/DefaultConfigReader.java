package ua.edu.chmnu.ki.networks.core.config;


import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class DefaultConfigReader implements ConfigReader {

    private final Scanner scanner;

    private final ConfigReader envReader = new EnvConfigReader();

    private final ConfigReader consoleReader;

    public DefaultConfigReader(Scanner scanner) {
        this.scanner = scanner;
        this.consoleReader = new ConsoleConfigReader(scanner);
    }

    @Override
    public String read(String key, String defaultValue) {
        String envValue = envReader.read(key, "");

        if (StringUtils.isNotBlank(envValue)) {
            return envValue;
        }

        System.out.print(key + " not found in environment. Use default " + defaultValue + "? (y/n): ");
        String answer = scanner.nextLine();

        if ("y".equalsIgnoreCase(answer)) {
            return defaultValue;
        }

        return consoleReader.read(key, defaultValue);
    }
}
