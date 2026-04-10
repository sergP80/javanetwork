package ua.edu.chmnu.ki.networks.chat.server.message;


import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;

import java.io.IOException;

public interface MessageReceiver {
    ChatMessage receive() throws IOException;
}
