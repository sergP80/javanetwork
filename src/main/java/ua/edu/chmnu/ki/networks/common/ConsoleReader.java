package ua.edu.chmnu.ki.networks.common;

import java.util.Scanner;
import java.util.function.Function;

public class ConsoleReader {

    private final String prompt;

    public ConsoleReader(String prompt) {
        this.prompt = prompt;
    }

    public ConsoleReader() {
        this("");
    }

    public <T> T read(Function<String, T> converter) {

        if (prompt != null && !prompt.isEmpty()) {
            System.out.print(prompt);
        }

        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.hasNextLine()) {
                return converter.apply(scanner.nextLine());
            }
        }

        return null;
    }
}
