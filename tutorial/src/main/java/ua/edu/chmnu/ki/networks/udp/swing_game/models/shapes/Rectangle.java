package ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Size;

public class Rectangle implements Shape {
    private Position center;
    private Size size;

    public Rectangle(Position center, Size size) {
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
        return ShapeType.RECTANGLE;
    }

    @Override
    public void draw(ShapeContext context) {
        context.rectangle(center, size);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "center=" + center +
                ", size=" + size +
                '}';
    }
}
