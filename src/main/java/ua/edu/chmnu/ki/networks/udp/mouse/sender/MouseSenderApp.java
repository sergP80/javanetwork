package ua.edu.chmnu.ki.networks.udp.mouse.sender;

import ua.edu.chmnu.ki.networks.common.ConsoleReader;
import ua.edu.chmnu.ki.networks.common.DesktopBoundsUtils;
import ua.edu.chmnu.ki.networks.mouse.model.MousePositionEvent;
import ua.edu.chmnu.ki.networks.udp.core.SenderApp;
import ua.edu.chmnu.ki.networks.udp.core.sender.UdpObjectMessageSender;
import ua.edu.chmnu.ki.networks.udp.mouse.provider.MousePositionEventProvider;

import java.awt.*;
import java.util.function.Function;

/**
 * @author svpuzyrov
 */
public class MouseSenderApp {
    public static void main(String[] args) throws Exception {

        String connectionUrl;

        if (args == null || args.length == 0) {
            connectionUrl = new ConsoleReader().read(Function.identity());
        } else {
            connectionUrl = args[0];
        }

        Rectangle virtualBounds = DesktopBoundsUtils.getVirtualBounds();

        var provider = new MousePositionEventProvider(virtualBounds);

        try (var sender = new UdpObjectMessageSender<MousePositionEvent>(connectionUrl)) {
            new SenderApp<>(sender, provider).runApp(args);
            System.out.println("Sender started");
            System.out.println("Sender virtual bounds: " + virtualBounds);
            System.out.println("Target: " + connectionUrl);
            System.out.println("Press Ctrl+C to stop");
        }
    }
}