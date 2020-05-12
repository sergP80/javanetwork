package ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Size;

public class Square implements Shape {
    private Position center;
    private int size;

    public Square(Position center, int size) {
        this.center = center;
        this.size = size;
    }

    @Override
    public Position getPosition() {
        return this.center;
    }

    @Override
    public Size getSize() {
        return Size.of(size, size);
    }

    @Override
    public ShapeType shapeType() {
        return ShapeType.SQUARE;
    }

    @Override
    public void draw(ShapeContext context) {
        context.square(center, size);
    }

    @Override
    public void scale(float scaleFactor) {
        this.size = (int)(this.size * scaleFactor);
    }

    @Override
    public String toString() {
        return "Square{" +
                "center=" + center +
                ", size=" + size +
                '}';
    }
}
