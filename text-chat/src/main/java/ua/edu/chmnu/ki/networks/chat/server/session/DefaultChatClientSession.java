package ua.edu.chmnu.ki.networks.chat.server.session;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;
import ua.edu.chmnu.ki.networks.chat.server.message.MessageSender;

@AllArgsConstructor
public class DefaultChatClientSession implements ChatClientSession {

    private final String username;

    private final MessageSender sender;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void send(ChatMessage message) {
        sender.send(message);
    }
}
