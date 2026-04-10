package ua.edu.chmnu.ki.networks.rsv.protocol;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PacketType {
    public static final byte HELLO = 1;
    public static final byte FRAME_CHUNK = 2;
    public static final byte HEARTBEAT = 3;
}
