package ua.edu.chmnu.ki.networks.rsv.protocol;

public record ClientHello(PacketType type, String clientName) implements PacketFrame {
}
