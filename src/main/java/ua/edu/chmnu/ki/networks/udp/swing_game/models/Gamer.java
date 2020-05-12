package ua.edu.chmnu.ki.networks.udp.swing_game.models;

import java.util.UUID;

public class Gamer implements java.io.Serializable {
    private UUID uuid;
    private String name;

    public Gamer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getId() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
