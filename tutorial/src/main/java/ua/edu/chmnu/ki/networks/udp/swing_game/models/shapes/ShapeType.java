package ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Size;

import java.io.Serializable;
import java.util.function.BiFunction;

public enum ShapeType implements Serializable {
    RECTANGLE(1) {
        @Override
        public BiFunction<Position, Size, Shape> builder() {
            return Rectangle::new;
        }
    } ,
    SQUARE(2) {
        @Override
        public BiFunction<Position, Size, Shape> builder() {
            return (pos, size) -> new Square(pos, Math.min(size.width, size.height));
        }
    },
    ELLIPSE(3) {
        @Override
        public BiFunction<Position, Size, Shape> builder() {
            return Ellipse::new;
        }
    },
    CIRCLE(4) {
        @Override
        public BiFunction<Position, Size, Shape> builder() {
            return (pos, size) -> new Circle(pos, Math.min(size.width, size.height));
        }
    };

    private int id;

    ShapeType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract BiFunction<Position, Size, Shape> builder();
}
