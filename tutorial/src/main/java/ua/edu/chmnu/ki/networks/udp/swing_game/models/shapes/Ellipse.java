package ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Size;

public class Ellipse implements Shape {
    private Position center;
    private Size size;

    public Ellipse(Position center, Size size) {
        this.center = center;
        this.size = size;
    }

    @Override
    public Position getPosition() {
        return this.center;
    }

    @Override
    public Size getSize() {
        return this.size;
    }

    @Override
    public ShapeType shapeType() {
        return ShapeType.ELLIPSE;
    }

    @Override
    public void draw(ShapeContext context) {
        context.ellipse(center, size);
    }

    @Override
    public String toString() {
        return "Ellipse{" +
                "center=" + center +
                ", size=" + size +
                '}';
    }
}
