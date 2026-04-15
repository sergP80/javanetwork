package ua.edu.chmnu.ki.networks.chat.server.message;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;
import ua.edu.chmnu.ki.networks.chat.common.MessageCodec;

import java.io.PrintWriter;

@AllArgsConstructor
public class SocketMessageSender implements MessageSender{

    private final PrintWriter writer;

    @Override
    public void send(ChatMessage message) {
        writer.println(MessageCodec.serialize(message));
        writer.flush();
    }
}
