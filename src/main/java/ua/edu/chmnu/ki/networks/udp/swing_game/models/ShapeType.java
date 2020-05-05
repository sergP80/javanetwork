package ua.edu.chmnu.ki.networks.udp.swing_game.models;

import java.io.Serializable;

public enum ShapeType implements Serializable {
    RECTANGLE(1),
    SQUARE(2),
    ELLIPSE(3),
    CIRCLE(4),
    DIAMOND(5);

    private int id;

    ShapeType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
