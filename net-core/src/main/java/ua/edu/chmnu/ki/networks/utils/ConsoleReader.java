package ua.edu.chmnu.ki.networks.utils;

import java.util.Scanner;
import java.util.function.Function;

public class ConsoleReader {

    private final String prompt;

    public ConsoleReader(String prompt) {
        this.prompt = prompt;
    }

    public <T> T read(Function<String, T> converter) {

        if (prompt != null && !prompt.isEmpty()) {
            System.out.print(prompt);
        }

        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line == null || line.isEmpty()) {
                    return null;
                }

                return converter.apply(line);
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }
}
