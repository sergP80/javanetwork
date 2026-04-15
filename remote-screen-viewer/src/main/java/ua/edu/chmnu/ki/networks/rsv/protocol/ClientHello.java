package ua.edu.chmnu.ki.networks.rsv.protocol;

public record ClientHello(String clientName) implements PacketFrame {
    @Override
    public PacketType type() {
        return PacketType.HELLO;
    }
}
