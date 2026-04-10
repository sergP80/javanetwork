package ua.edu.chmnu.ki.networks.udp.swing_game;

import ua.edu.chmnu.ki.networks.udp.swing_game.core.GamerPool;
import ua.edu.chmnu.ki.networks.udp.swing_game.core.UdpReceiver;
import ua.edu.chmnu.ki.networks.udp.swing_game.core.UdpSender;
import ua.edu.chmnu.ki.networks.udp.swing_game.ui.forms.MainFrame;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        final GamerPool gamerPool = new GamerPool();
        final UdpSender sender = new UdpSender();
        executorService.submit(sender);

        final UdpReceiver receiver = new UdpReceiver(gamerPool);
        executorService.submit(receiver);

        EventQueue.invokeLater(() -> new MainFrame(gamerPool, sender));
    }
}
