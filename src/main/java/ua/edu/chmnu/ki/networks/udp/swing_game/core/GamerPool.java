package ua.edu.chmnu.ki.networks.udp.swing_game.core;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Gamer;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes.ShapeContext;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamerPool {

    private final CopyOnWriteArrayList<Gamer> participants = new CopyOnWriteArrayList<>();

    public synchronized void add(Gamer gamer) {
        participants.removeIf(gi -> gi.getId().equals(gamer.getId()));
        participants.add(gamer);
    }

    public synchronized void remove(UUID uuid) {
        participants.removeIf(gi -> gi.getId().equals(uuid));
    }

    public synchronized void updateLocation(UUID uuid, Position position) {
        participants.stream()
                .filter(gi -> gi.getId().equals(uuid))
                .findFirst()
                .ifPresent(gi -> gi.setPosition(position));
    }

    public synchronized void drawAll(ShapeContext context) {
        this.participants.forEach(gi -> {
            gi.getShape().draw(context);
        });
    }
}
