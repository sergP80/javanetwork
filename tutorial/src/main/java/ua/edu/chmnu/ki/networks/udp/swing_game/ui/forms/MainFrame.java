package ua.edu.chmnu.ki.networks.udp.swing_game.ui.forms;

import ua.edu.chmnu.ki.networks.udp.swing_game.core.GamerPool;
import ua.edu.chmnu.ki.networks.udp.swing_game.core.UdpSender;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final String DEFAULT_TITLE = "Mover";
    private static final int WIDTH  = 640;
    private static final int HEIGHT = 480;

    public MainFrame(String title, GamerPool gamerPool, UdpSender udpSender) throws HeadlessException {

        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationByPlatform(true);

        LoginDialog loginDialog = new LoginDialog(this, gamerPool);
        loginDialog.setVisible(true);

        GamePanel gamePanel = new GamePanel(loginDialog.getCurrentGamer(), gamerPool, udpSender);
        add(gamePanel);
        setLocationAndSize();
        setVisible(true);
    }

    private void setLocationAndSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(WIDTH, HEIGHT);
        setLocation(screenSize.width/2 - WIDTH/2, screenSize.height/2 - HEIGHT / 2);
    }

    public MainFrame(GamerPool gamerPool, UdpSender udpSender) throws HeadlessException {
        this(DEFAULT_TITLE, gamerPool, udpSender);
    }
}
