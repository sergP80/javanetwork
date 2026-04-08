package ua.edu.chmnu.ki.networks.mouse.model;

import ua.edu.chmnu.ki.networks.common.DesktopBoundsUtils;

import java.awt.*;
import java.io.Serializable;

public record MousePositionEvent(double x, double y, ScreenBounds screenBounds, long timestamp) implements Serializable {
    public MousePositionEvent(Point point, Rectangle rectangle, long timestamp) {
        this(
                DesktopBoundsUtils.normalize(point.x, rectangle.x, rectangle.width),
                DesktopBoundsUtils.normalize(point.y, rectangle.y, rectangle.height),
                new ScreenBounds(rectangle),
                timestamp
        );
    }
}
