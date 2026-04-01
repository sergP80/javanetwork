package ua.edu.chmnu.ki.networks.tcp.simple_messenger.model;

import lombok.Getter;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Predicate;

@Getter
public class ClientStorage {

    private final ConcurrentSkipListSet<Client> clients = new ConcurrentSkipListSet<>();

    public void add(Client client) {
        clients.add(client);
    }

    public void remove(Client client) {
        Predicate<Client> removePredicate = c -> Objects.equals(c.getId(), client.getId());

        clients.stream()
                .filter(removePredicate)
                .findFirst()
                .ifPresent(c -> {
                    try (var socket = c.socket()) {
                        System.out.println("Removed [" + client.clientView().nick() + "]");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        clients.removeIf(removePredicate);
    }

    synchronized public ClientChat getChat() {
        return new ClientChat(
          clients.stream().map(Client::clientView).toList()
        );
    }
}
