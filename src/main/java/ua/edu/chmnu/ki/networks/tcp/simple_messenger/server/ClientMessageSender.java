package ua.edu.chmnu.ki.networks.tcp.simple_messenger.server;

import lombok.RequiredArgsConstructor;
import ua.edu.chmnu.ki.networks.tcp.simple_messenger.model.Client;
import ua.edu.chmnu.ki.networks.tcp.simple_messenger.model.ClientMessage;
import ua.edu.chmnu.ki.networks.tcp.simple_messenger.model.ClientStorage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

@RequiredArgsConstructor
public class ClientMessageSender {
    final ClientStorage clientStorage;

    public void sendTo(ClientMessage message) {
        clientStorage.getClients()
                .stream()
                .filter(c -> Objects.equals(c.clientView().id(), message.clientId()))
                .findFirst()
                .ifPresent(recipient -> this.sendTo(recipient, message));
    }

    private void sendTo(Client recipient, ClientMessage message) {
        try {
           var out = new ObjectOutputStream(recipient.socket().getOutputStream());

           out.writeObject(message);
        } catch (IOException e) {


        }
    }
}
