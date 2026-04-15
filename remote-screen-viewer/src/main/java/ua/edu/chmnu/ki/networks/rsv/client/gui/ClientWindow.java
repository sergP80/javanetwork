package ua.edu.chmnu.ki.networks.rsv.client.gui;

import ua.edu.chmnu.ki.networks.rsv.client.ScreenClient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ClientWindow {

    private final FramePanel framePanel = new FramePanel();
    private final JLabel statusLabel = new JLabel("Heartbeat: UNKNOWN");
    private JFrame frame;

    private final ScreenClient screenClient;

    public ClientWindow(ScreenClient screenClient) {
        this.screenClient = screenClient;
    }

    public void show() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Remote Screen Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setJMenuBar(createMenuBar());
            frame.setLayout(new BorderLayout());
            frame.add(framePanel, BorderLayout.CENTER);
            frame.add(createStatusBar(), BorderLayout.SOUTH);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            Timer timer = new Timer(33, e -> framePanel.repaint());
            timer.start();
        });
    }

    public void updateFrame(BufferedImage image) {
        framePanel.updateFrame(image);
    }

    public void updateHeartbeatStatus(boolean alive) {
        SwingUtilities.invokeLater(() -> statusLabel.setText("Heartbeat: " + (alive ? "ALIVE" : "LOST")));
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem reconnect = new JMenuItem(new AbstractAction("Reconnect") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (screenClient != null) {
                    try {
                        screenClient.restart();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        JMenuItem exitItem = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frame != null) {
                    frame.dispose();
                }
                System.exit(0);
            }
        });

        fileMenu.add(reconnect);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        return menuBar;
    }

    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(new EmptyBorder(4, 8, 4, 8));
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusBar.add(statusLabel, BorderLayout.WEST);
        return statusBar;
    }
}
