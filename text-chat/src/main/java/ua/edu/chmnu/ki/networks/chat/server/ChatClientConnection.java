package ua.edu.chmnu.ki.networks.chat.server;


import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;
import ua.edu.chmnu.ki.networks.chat.common.MessageType;
import ua.edu.chmnu.ki.networks.chat.server.message.MessageReceiver;
import ua.edu.chmnu.ki.networks.chat.server.message.MessageSender;
import ua.edu.chmnu.ki.networks.chat.server.message.SocketMessageReceiver;
import ua.edu.chmnu.ki.networks.chat.server.message.SocketMessageSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import static ua.edu.chmnu.ki.networks.chat.common.MessageUtils.normalizeIncomingMessage;

@AllArgsConstructor
public class ChatClientConnection implements Runnable {

    private static final String QUIT_COMMAND = "/quit";

    private final Socket socket;
    private final ClientRegistry clientRegistry;
    private final ChatBroadcaster broadcaster;

    private final AtomicBoolean running;

    @Override
    public void run() {
        String username = null;

        try (Socket clientSocket = socket;
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            MessageSender sender = new SocketMessageSender(writer);

            writer.println("Enter username:");
            writer.flush();

            username = reader.readLine();
            if (username == null || username.trim().isEmpty()) {
                sender.send(ChatMessage.system("Username is empty. Connection closed."));
                return;
            }

            username = username.trim();

            if (clientRegistry.exists(username)) {
                sender.send(ChatMessage.system("Username already in use. Connection closed."));
                return;
            }

            ChatClientSession session = new ChatClientSessionSender(username, sender);
            clientRegistry.register(session);

            sender.send(ChatMessage.system("Connected as " + username));
            sender.send(ChatMessage.system("Use:"));
            sender.send(ChatMessage.system("  text message"));
            sender.send(ChatMessage.system("  /pic <url>"));
            sender.send(ChatMessage.system("  /both <text> | <url>"));
            sender.send(ChatMessage.system("  /pm <username> <text>"));
            sender.send(ChatMessage.system("  /quit"));

            broadcaster.broadcastExcept(username, ChatMessage.system(username + " joined the chat."));

            MessageReceiver receiver = new SocketMessageReceiver(reader);

            ChatMessage message;
            while (running.get() && (message = receiver.receive()) != null) {
                if (message.text() != null && QUIT_COMMAND.equalsIgnoreCase(message.text().trim())) {
                    sender.send(ChatMessage.system("Bye."));
                    break;
                }

                ChatMessage normalized = normalizeIncomingMessage(username, message);
                if (normalized != null) {

                    if (normalized.type() == MessageType.PRIVATE) {
                        boolean delivered = broadcaster.sendPrivate(normalized.to(), normalized);
                        if (delivered) {
                            sender.send(ChatMessage.system("Message sent to " + normalized.to()));
                        } else {
                            sender.send(ChatMessage.system("User " + normalized.to() + " not found"));
                        }


                    } else {
                        broadcaster.broadcast(normalized);

                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Client connection error: " + e.getMessage());
        } finally {
            clientRegistry.unregister(username);
            if (username != null) {
                broadcaster.broadcast(ChatMessage.system(username + " left the chat."));
            }
        }
    }


}
