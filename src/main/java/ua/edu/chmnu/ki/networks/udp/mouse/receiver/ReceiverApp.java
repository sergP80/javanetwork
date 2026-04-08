package ua.edu.chmnu.ki.networks.udp.mouse.receiver;

import ua.edu.chmnu.ki.networks.mouse.model.MouseCapture;
import ua.edu.chmnu.ki.networks.udp.core.AbstractReceiverApp;

import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

public class ReceiverApp extends AbstractReceiverApp {

    public ReceiverApp() {
        super();
    }

    public static void main(String[] args) throws IOException {
        new ReceiverApp().runApp(args, (Consumer<MouseCapture>) (mouseCapture) -> {
            try {
                Robot robot = new Robot();
                System.out.println("Position of target: " + mouseCapture);
                robot.mouseMove(mouseCapture.x(), mouseCapture.y());

            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
