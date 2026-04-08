package ua.edu.chmnu.ki.networks.common;

import java.util.Scanner;
import java.util.function.Function;

public class ConsoleReader {

    public <T> T read(Function<String, T> converter) {
        try(Scanner scanner = new Scanner(System.in)) {
            if (scanner.hasNextLine()) {
                return converter.apply(scanner.nextLine());
            }
        }

        return null;
    }
}
