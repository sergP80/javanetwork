package ua.edu.chmnu.ki.networks.udp.swing_game.ui;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.GamerInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class RootPanel extends JPanel implements MouseMotionListener {
    private GamerInfo gamerInfo;

    public RootPanel(GamerInfo gamerInfo) {
        this.gamerInfo = gamerInfo;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Graphics graphics = getGraphics();
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.drawOval(e.getX(), e.getY(), gamerInfo.getDimension().width, gamerInfo.getDimension().height);
        gamerInfo.setX(e.getX());
        gamerInfo.setY(e.getY());
        /**
         * TODO send gamer info via network
         */
    }
}
