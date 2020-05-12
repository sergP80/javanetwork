package ua.edu.chmnu.ki.networks.udp.swing_game.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final String DEFAULT_TITLE = "Mover";
    private static final int WIDTH  = 320;
    private static final int HEIGHT = 280;

    public MainFrame(String title) throws HeadlessException {
        setSize(WIDTH, HEIGHT);
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        LoginDialog loginDialog = new LoginDialog();
        loginDialog.setVisible(true);

        add(new RootPanel(loginDialog.getGamerInfo()));
    }

    public MainFrame() throws HeadlessException {
        this(DEFAULT_TITLE);
    }
}
