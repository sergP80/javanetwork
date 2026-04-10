package ua.edu.chmnu.ki.networks.mouse.handler;

import ua.edu.chmnu.ki.networks.utils.DesktopBoundsUtils;
import ua.edu.chmnu.ki.networks.mouse.model.MousePositionEvent;
import ua.edu.chmnu.ki.networks.udp.core.receiver.MessageHandler;

import java.awt.Rectangle;
import java.awt.Robot;

public class MousePositionEventHandler implements MessageHandler<MousePositionEvent> {

    private final Robot robot;
    private final Rectangle receiverVirtualBounds;

    public MousePositionEventHandler(Robot robot, Rectangle receiverVirtualBounds) {
        this.robot = robot;
        this.receiverVirtualBounds = new Rectangle(receiverVirtualBounds);
    }

    @Override
    public void handle(MousePositionEvent event) {
        int translatedX = DesktopBoundsUtils.denormalize(
                event.x(),
                receiverVirtualBounds.x,
                receiverVirtualBounds.width
        );

        int translatedY = DesktopBoundsUtils.denormalize(
                event.y(),
                receiverVirtualBounds.y,
                receiverVirtualBounds.height
        );

        robot.mouseMove(translatedX, translatedY);

        System.out.printf(
                "Handled event normalized=(%.4f, %.4f) -> mouse=(%d,%d)%n",
                event.x(),
                event.y(),
                translatedX,
                translatedY
        );
    }
}
