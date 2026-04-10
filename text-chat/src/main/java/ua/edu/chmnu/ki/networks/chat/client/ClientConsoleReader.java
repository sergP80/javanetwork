package ua.edu.chmnu.ki.networks.chat.client;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;
import ua.edu.chmnu.ki.networks.chat.common.MessageCodec;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import static ua.edu.chmnu.ki.networks.chat.common.MessageUtils.QUIT_COMMAND;
import static ua.edu.chmnu.ki.networks.chat.common.MessageUtils.toMessage;

@AllArgsConstructor
public class ClientConsoleReader implements Runnable {

    private final Scanner scanner;

    private final PrintWriter writer;

    private final AtomicBoolean running;

    private final String username;

    @Override
    public void run() {
        while (running.get()) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (!scanner.hasNextLine()) {
                continue;
            }

            String line = scanner.nextLine();

            ChatMessage message = toMessage(username, line);

            if (message == null) {
                continue;
            }

            writer.println(MessageCodec.encode(message));
            writer.flush();

            if (QUIT_COMMAND.equalsIgnoreCase(line.trim())) {
                break;
            }
        }

    }
}
