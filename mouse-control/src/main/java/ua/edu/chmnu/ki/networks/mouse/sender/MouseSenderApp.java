package ua.edu.chmnu.ki.networks.mouse.sender;

import ua.edu.chmnu.ki.networks.utils.ConsoleReader;
import ua.edu.chmnu.ki.networks.utils.DesktopBoundsUtils;
import ua.edu.chmnu.ki.networks.mouse.model.MousePositionEvent;
import ua.edu.chmnu.ki.networks.udp.core.SenderApp;
import ua.edu.chmnu.ki.networks.udp.core.sender.UdpObjectMessageSender;
import ua.edu.chmnu.ki.networks.mouse.provider.MousePositionEventProvider;

import java.awt.*;
import java.util.Optional;
import java.util.function.Function;


public class MouseSenderApp {
    public static void main(String[] args) throws Exception {

        String connectionUrl;

        if (args == null || args.length == 0) {
            connectionUrl = Optional.ofNullable(new ConsoleReader("Type target end-point <host:port>:").read(Function.identity()))
                    .orElseThrow(() -> new IllegalArgumentException("Connection string is not present"));
        } else {
            connectionUrl = args[0];
        }

        Rectangle virtualBounds = DesktopBoundsUtils.getVirtualBounds();

        var provider = new MousePositionEventProvider(virtualBounds);

        var sender = new UdpObjectMessageSender<MousePositionEvent>(connectionUrl);

        new SenderApp<>(sender, provider).runApp(args);
        System.out.println("Sender started");
        System.out.println("Sender virtual bounds: " + virtualBounds);
        System.out.println("Target: " + connectionUrl);
        System.out.println("Press Ctrl+C to stop");

    }
}