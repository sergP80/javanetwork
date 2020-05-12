package ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers;

import java.util.UUID;

public class GamerLocation implements java.io.Serializable {
    private UUID gamerId;

    private Position position;

    public GamerLocation(UUID gamerId, Position position) {
        this.gamerId = gamerId;
        this.position = position;
    }

    public static GamerLocation of(UUID gamerId, Position location) {
        return new GamerLocation(gamerId, location);
    }

    public UUID getGamerId() {
        return gamerId;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "GamerLocation{" +
                "gamerId=" + gamerId +
                ", position=" + position +
                '}';
    }
}
