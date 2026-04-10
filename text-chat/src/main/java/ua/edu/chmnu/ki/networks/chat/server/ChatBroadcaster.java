package ua.edu.chmnu.ki.networks.chat.server;


import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;

@AllArgsConstructor
public class ChatBroadcaster {

    private final ClientRegistry clientRegistry;

    public void broadcast(ChatMessage message) {
        for (ChatClientSession session : clientRegistry.all()) {
            session.send(message);
        }
    }

    public void broadcastExcept(String excludedUsername, ChatMessage message) {
        for (ChatClientSession session : clientRegistry.all()) {
            if (!session.getUsername().equals(excludedUsername)) {
                session.send(message);
            }
        }
    }
}
