package ua.edu.chmnu.ki.networks.rsv.client;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.image.BufferedImage;

public class ClientWindow {

    private final FramePanel framePanel = new FramePanel();

    public void show() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Remote Screen Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(framePanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            Timer timer = new Timer(33, e -> framePanel.repaint());
            timer.start();
        });
    }

    public void updateFrame(BufferedImage image) {
        this.framePanel.updateFrame(image);
    }

}
