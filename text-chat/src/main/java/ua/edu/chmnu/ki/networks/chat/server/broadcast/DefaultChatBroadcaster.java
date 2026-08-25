package ua.edu.chmnu.ki.networks.chat.server.broadcast;


import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;
import ua.edu.chmnu.ki.networks.chat.server.ClientRegistry;
import ua.edu.chmnu.ki.networks.chat.server.session.ChatClientSession;

import java.util.Optional;

@AllArgsConstructor
public class DefaultChatBroadcaster implements ChatBroadcaster {

    private final ClientRegistry clientRegistry;

    @Override
    public void sendToAll(ChatMessage message) {
        for (ChatClientSession session : clientRegistry.all()) {
            session.send(message);
        }

    }

    @Override
    public void sendToAllExcept(String excludedUsername, ChatMessage message) {
        clientRegistry.all().stream()
                .filter(session -> !session.getUsername().equals(excludedUsername))
                .forEach(session -> session.send(message));
    }

    @Override
    public boolean sendToPrivate(String recipientUserName, ChatMessage message) {
        return Optional.ofNullable(clientRegistry.get(recipientUserName))
                .map(session -> {
                    session.send(message);
                    return true;
                })
                .orElse(false);
    }
}
