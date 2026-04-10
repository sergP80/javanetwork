package ua.edu.chmnu.ki.networks.tcp.simple_messenger.model;

import java.io.Serializable;

public record ClientMessage(Long clientId, String message, Object meta) implements Serializable {
}
