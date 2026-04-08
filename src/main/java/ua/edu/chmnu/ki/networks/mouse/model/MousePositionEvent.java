package ua.edu.chmnu.ki.networks.mouse.model;

import ua.edu.chmnu.ki.networks.common.DesktopBoundsUtils;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public record MousePositionEvent(double x, double y, ScreenBounds screenBounds, long timestamp) implements Serializable {
    public MousePositionEvent(Point point, Rectangle rectangle, long timestamp) {
        this(
                DesktopBoundsUtils.normalize(point.x, rectangle.x, rectangle.width),
                DesktopBoundsUtils.normalize(point.y, rectangle.y, rectangle.height),
                new ScreenBounds(rectangle),
                timestamp
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MousePositionEvent that = (MousePositionEvent) o;
        return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0 && Objects.equals(screenBounds, that.screenBounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, screenBounds);
    }
}
