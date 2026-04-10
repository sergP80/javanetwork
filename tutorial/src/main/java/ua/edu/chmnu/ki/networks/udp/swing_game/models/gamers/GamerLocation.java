package ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers;

import java.io.Serializable;
import java.util.UUID;

public record GamerLocation(UUID gamerId, Position position) implements Serializable {

    public static GamerLocation of(UUID gamerId, Position location) {
        return new GamerLocation(gamerId, location);
    }

    @Override
    public String toString() {
        return "GamerLocation{" +
                "gamerId=" + gamerId +
                ", position=" + position +
                '}';
    }
}
