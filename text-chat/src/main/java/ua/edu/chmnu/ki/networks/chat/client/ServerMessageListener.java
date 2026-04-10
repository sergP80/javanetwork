package ua.edu.chmnu.ki.networks.chat.client;

import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;
import ua.edu.chmnu.ki.networks.chat.server.message.MessageReceiver;
import ua.edu.chmnu.ki.networks.chat.server.message.SocketMessageReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerMessageListener implements Runnable {

    private final BufferedReader reader;
    private final AtomicBoolean running;

    public ServerMessageListener(BufferedReader reader, AtomicBoolean running) {
        this.reader = reader;
        this.running = running;
    }

    @Override
    public void run() {
        try {
            MessageReceiver receiver = new SocketMessageReceiver(reader);

            ChatMessage message;

            while (running.get() && running.get() && (message = receiver.receive()) != null) {
                printMessage(message);
            }
        } catch (IOException e) {
            System.err.println("Disconnected from server: " + e.getMessage());
        }
    }

    private void printMessage(ChatMessage message) {
        switch (message.type()) {
            case SYSTEM:
                System.out.println("[SYSTEM] " + safe(message.text()));
                break;
            case TEXT:
                System.out.println("[" + safe(message.from()) + "] " + safe(message.text()));
                break;
            case PICTURE:
                System.out.println("[" + safe(message.from()) + "] picture: " + safe(message.pictureUrl()));
                break;
            case TEXT_AND_PICTURE:
                System.out.println("[" + safe(message.from()) + "] "
                        + safe(message.text()) + " | picture: " + safe(message.pictureUrl()));
                break;
            default:
                System.out.println("[UNKNOWN MESSAGE]");
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
