package ua.edu.chmnu.ki.networks.rsv.protocol;

import java.time.LocalDateTime;

public record HeartBeat(LocalDateTime timeStamp) implements PacketFrame {
    @Override
    public PacketType type() {
        return PacketType.HEARTBEAT;
    }
}
