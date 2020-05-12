package ua.edu.chmnu.ki.networks.udp.swing_game.ui.forms;

import ua.edu.chmnu.ki.networks.udp.swing_game.core.GamerPool;
import ua.edu.chmnu.ki.networks.udp.swing_game.core.UdpSender;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Gamer;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.GamerLocation;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes.ShapeContext;
import ua.edu.chmnu.ki.networks.udp.swing_game.ui.graphics.GraphicShape2DContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GamePanel extends JPanel implements MouseMotionListener {

    private final Gamer currentGamer;
    private final GamerPool gamerPool;
    private final UdpSender udpSender;

    public GamePanel(Gamer currentGamer, GamerPool gamerPool, UdpSender udpSender) {
        this.currentGamer = currentGamer;
        this.gamerPool = gamerPool;
        this.udpSender = udpSender;
        addMouseMotionListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Position newPosition = Position.of(e.getX(), e.getY());
        gamerPool.updateLocation(currentGamer.getId(), newPosition);
        try {
            udpSender.submit(GamerLocation.of(currentGamer.getId(), newPosition));
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        this.gamerPool.drawAll(new GraphicShape2DContext(g));
    }
}
