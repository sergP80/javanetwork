package ua.edu.chmnu.ki.networks.tcp.simple_messenger.model;

import java.io.Serializable;
import java.util.List;

public record ClientChat(List<ClientView> clients) implements Serializable {
}
