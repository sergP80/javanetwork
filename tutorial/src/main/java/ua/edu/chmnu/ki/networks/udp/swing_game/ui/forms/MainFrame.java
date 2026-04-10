package ua.edu.chmnu.ki.networks.udp.swing_game.ui.forms;

import ua.edu.chmnu.ki.networks.udp.swing_game.core.GamerPool;
import ua.edu.chmnu.ki.networks.udp.swing_game.core.UdpSender;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final String DEFAULT_TITLE = "Mover";
    private static final int WIDTH  = 640;
    private static final int HEIGHT = 480;

    private GamePanel gamePanel;

    private final GamerPool gamerPool;
    private final UdpSender udpSender;

    public MainFrame(String title, GamerPool gamerPool, UdpSender udpSender) throws HeadlessException {
        this.gamerPool = gamerPool;
        this.udpSender = udpSender;

        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationByPlatform(true);

        LoginDialog loginDialog = new LoginDialog(this, this.gamerPool);
        loginDialog.setVisible(true);

        gamePanel = new GamePanel(loginDialog.getCurrentGamer(), this.gamerPool, this.udpSender);
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
