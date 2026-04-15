package ua.edu.chmnu.ki.networks.rsv.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PacketType {
    UNKNOWN((byte) 0),
    HELLO((byte) 1),
    FRAME_CHUNK((byte) 2),
    HEARTBEAT((byte) 3);

    private final byte type;

    public static PacketType of(byte type) {
        return Arrays.stream(values())
                .filter(v -> v.getType() == type)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
