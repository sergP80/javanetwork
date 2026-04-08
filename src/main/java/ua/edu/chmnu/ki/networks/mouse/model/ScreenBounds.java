package ua.edu.chmnu.ki.networks.mouse.model;

import java.awt.*;
import java.io.Serializable;

public record ScreenBounds(int minX, int minY, int width, int height) implements Serializable {
    public ScreenBounds(Rectangle rectangle) {
        this(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
