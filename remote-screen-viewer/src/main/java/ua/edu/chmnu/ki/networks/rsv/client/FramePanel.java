package ua.edu.chmnu.ki.networks.rsv.client;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;

public class FramePanel extends JPanel {

    private final AtomicReference<BufferedImage> currentFrame = new AtomicReference<>();

    public FramePanel() {
        setPreferredSize(new Dimension(1000, 700));
    }

    public void updateFrame(BufferedImage image) {
        currentFrame.set(image);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage frame = currentFrame.get();
        if (frame == null) {
            return;
        }

        Image scaled = frame.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST);
        g.drawImage(scaled, 0, 0, null);
    }
}
