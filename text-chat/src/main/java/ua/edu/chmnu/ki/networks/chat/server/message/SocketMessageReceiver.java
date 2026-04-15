package ua.edu.chmnu.ki.networks.chat.server.message;


import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.chat.common.ChatMessage;
import ua.edu.chmnu.ki.networks.chat.common.MessageCodec;

import java.io.BufferedReader;
import java.io.IOException;

@AllArgsConstructor
public class SocketMessageReceiver implements MessageReceiver {

    private final BufferedReader reader;

    @Override
    public ChatMessage receive() throws IOException {
        String line = reader.readLine();
        if (line == null) {
            return null;
        }
        return MessageCodec.deserialize(line);
    }
}
