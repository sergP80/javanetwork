package ua.edu.chmnu.ki.networks.chat.server.broadcast;


import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;

public interface ChatBroadcaster {

    void sendToAll(ChatMessage message);

    void sendToAllExcept(String excludedUsername, ChatMessage message);

    boolean sendToPrivate(String recipientUserName, ChatMessage message);
}
