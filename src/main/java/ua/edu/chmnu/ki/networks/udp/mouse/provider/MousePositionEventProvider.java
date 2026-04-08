package ua.edu.chmnu.ki.networks.udp.mouse.provider;

import ua.edu.chmnu.ki.networks.mouse.model.MousePositionEvent;
import ua.edu.chmnu.ki.networks.udp.core.sender.OutgoingMessageProvider;

import java.awt.*;

public class MousePositionEventProvider implements OutgoingMessageProvider<MousePositionEvent> {

    private final Rectangle virtualBounds;

    public MousePositionEventProvider(Rectangle virtualBounds) {
        this.virtualBounds = new Rectangle(virtualBounds);
    }

    @Override
    public MousePositionEvent provide() {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        if (pointerInfo == null) {
            return null;
        }

        Point point = pointerInfo.getLocation();

        return new MousePositionEvent(
                point,
                virtualBounds,
                System.currentTimeMillis()
        );
    }
}
