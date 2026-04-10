package ua.edu.chmnu.ki.networks.core.config;

import java.util.Scanner;

public class ConsoleConfigReader implements ConfigReader {

    private final Scanner scanner;

    public ConsoleConfigReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String read(String key, String defaultValue) {
        System.out.print("Enter " + key + ": ");

        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }

        return defaultValue;
    }
}
