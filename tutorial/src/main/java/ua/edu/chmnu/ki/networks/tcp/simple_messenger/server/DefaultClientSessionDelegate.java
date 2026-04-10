package ua.edu.chmnu.ki.networks.tcp.simple_messenger.server;

import ua.edu.chmnu.ki.networks.tcp.core.server.ClientSessionDelegate;
import ua.edu.chmnu.ki.networks.tcp.simple.ServerClientSession;
import ua.edu.chmnu.ki.networks.tcp.simple_messenger.model.Client;
import ua.edu.chmnu.ki.networks.tcp.simple_messenger.model.ClientChat;
import ua.edu.chmnu.ki.networks.tcp.simple_messenger.model.ClientMessage;
import ua.edu.chmnu.ki.networks.tcp.simple_messenger.model.ClientStorage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultClientSessionDelegate implements ClientSessionDelegate {

    private final ClientStorage clientStorage = new ClientStorage();

    private final ClientMessageSender messageSender = new ClientMessageSender(clientStorage);

    @Override
    public void handle(Socket socket) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            Client client = (Client) in.readObject();

            clientStorage.add(client);

            System.out.println("Connected new: " + client);

            ClientChat currentChat = clientStorage.getChat();

            out.writeObject(currentChat);

            clientStorage.add(client);

            while (!socket.isClosed()) {
                ClientMessage clientMessage = (ClientMessage) in.readObject();

                messageSender.sendTo(clientMessage);
            }

            clientStorage.remove(client);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
