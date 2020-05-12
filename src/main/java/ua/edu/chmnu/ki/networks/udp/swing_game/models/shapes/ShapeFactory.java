package ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Size;

import java.util.function.Function;

public class ShapeFactory {
    private ShapeFactory() {
    }

    public static Function<Position, Shape> shapeBuilder(ShapeType shapeType, Size size) {
        return (pos) -> shapeType.builder().apply(pos, size);
    }
}
