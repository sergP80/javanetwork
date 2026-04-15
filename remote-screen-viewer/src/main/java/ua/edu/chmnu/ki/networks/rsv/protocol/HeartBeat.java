package ua.edu.chmnu.ki.networks.rsv.protocol;

import java.time.LocalDateTime;

public record HeartBeat(PacketType type, LocalDateTime timeStamp) implements PacketFrame {
}
