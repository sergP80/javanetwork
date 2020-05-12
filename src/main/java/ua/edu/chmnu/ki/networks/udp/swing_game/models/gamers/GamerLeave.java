package ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers;

import java.util.UUID;

public class GamerLeave implements java.io.Serializable {
    private UUID gamerId;

    public GamerLeave(UUID gamerId) {
        this.gamerId = gamerId;
    }

    public UUID getGamerId() {
        return gamerId;
    }
}
