package ua.edu.chmnu.ki.networks.udp.swing_game.ui.graphics;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Size;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes.ShapeContext;

import java.awt.*;

public class GraphicShape2DContext implements ShapeContext {
    private final Graphics graphics;

    public GraphicShape2DContext(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void ellipse(Position center, Size size) {
        graphics.fillOval(center.x - size.width/2, center.y - size.height/2, size.width, size.height);
    }

    @Override
    public void circle(Position center, int r) {
        graphics.fillOval(center.x - r, center.y - r, r, r);
    }

    @Override
    public void rectangle(Position center, Size size) {
        graphics.fillRect(center.x - size.width/2, center.y - size.height/2, size.width, size.height);
    }

    @Override
    public void square(Position center, int size) {
        graphics.fillRect(center.x - size / 2, center.y - size / 2, size, size);
    }
}
