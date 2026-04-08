package ua.edu.chmnu.ki.networks.udp.mouse.receiver;

import ua.edu.chmnu.ki.networks.common.ConsoleReader;
import ua.edu.chmnu.ki.networks.common.DesktopBoundsUtils;
import ua.edu.chmnu.ki.networks.mouse.model.MousePositionEvent;
import ua.edu.chmnu.ki.networks.udp.core.ReceiverApp;
import ua.edu.chmnu.ki.networks.udp.core.receiver.UdpObjectMessageReceiver;
import ua.edu.chmnu.ki.networks.udp.mouse.handler.MousePositionEventHandler;

import java.awt.*;
import java.util.Optional;

public class MouseReceiverApp {

    private static final int DEFAULT_PORT = 5789;

    public static void main(String[] args) throws Exception {
        int port;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            Integer result = new ConsoleReader("Enter port (press Enter to use default):").read(Integer::parseInt);

            port = Optional.ofNullable(result).orElse(DEFAULT_PORT);
        }

        Rectangle virtualBounds = DesktopBoundsUtils.getVirtualBounds();

        Robot robot = new Robot();

        var handler = new MousePositionEventHandler(robot, virtualBounds);

        var receiver = new UdpObjectMessageReceiver<MousePositionEvent>(port);

        new ReceiverApp<>(receiver, handler).runApp(args);

        System.out.println("Receiver started");
        System.out.println("Receiver virtual bounds: " + virtualBounds);
        System.out.println("Listen port: " + port);
        System.out.println("Press Ctrl+C to stop");

    }
}
