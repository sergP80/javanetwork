package ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Size;

public interface Shape extends java.io.Serializable {
    Position getPosition();
    Size getSize();
    ShapeType shapeType();
    void draw(ShapeContext context);

    default void move(int x, int y) {
        getPosition().move(x, y);
    }

    default void scale(float scaleFactor) {
        getSize().scale(scaleFactor);
    }
}
