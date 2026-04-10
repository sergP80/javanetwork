package ua.edu.chmnu.ki.networks.chat.server;

import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;

public interface ChatClientSession {
    String getUsername();

    void send(ChatMessage message);
}
