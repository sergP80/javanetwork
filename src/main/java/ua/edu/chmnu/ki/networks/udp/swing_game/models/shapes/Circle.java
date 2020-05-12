package ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Size;

public class Circle implements Shape {
    private Position center;
    private int radius;

    public Circle(Position center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Position getPosition() {
        return this.center;
    }

    @Override
    public Size getSize() {
        return Size.of(radius, radius);
    }

    @Override
    public ShapeType shapeType() {
        return ShapeType.CIRCLE;
    }

    @Override
    public void draw(ShapeContext context) {
        context.circle(center, radius);
    }

    @Override
    public void scale(float scaleFactor) {
        this.radius = (int)(this.radius * scaleFactor);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
}
