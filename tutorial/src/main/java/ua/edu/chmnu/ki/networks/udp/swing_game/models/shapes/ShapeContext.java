package ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Size;

public interface ShapeContext {
    void ellipse(Position center, Size size);
    void circle(Position center, int r);
    void rectangle(Position center, Size size);
    void square(Position center, int r);
}
