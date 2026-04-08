package ua.edu.chmnu.ki.networks.mouse.model;

import java.awt.*;
import java.io.Serializable;

public record MouseCapture(int x, int y) implements Serializable {
    public MouseCapture(Point source) {
        this(source.x, source.y);
    }
}
