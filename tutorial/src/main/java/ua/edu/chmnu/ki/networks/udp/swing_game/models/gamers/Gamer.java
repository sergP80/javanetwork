package ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers;

import lombok.Getter;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes.Shape;

import java.util.UUID;

public class Gamer implements java.io.Serializable {
    private final UUID uuid;
    @Getter
    private final String name;
    @Getter
    private final Shape shape;

    public Gamer(UUID uuid, String name, Shape shape) {
        this.uuid = uuid;
        this.name = name;
        this.shape = shape;
    }

    public UUID getId() {
        return uuid;
    }

    public void setPosition(Position position) {
        this.shape.getPosition().reset(position);
    }
}
