package ua.edu.chmnu.ki.networks.chat.server.message;


import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;

public interface MessageSender {
    void send(ChatMessage message);
}
