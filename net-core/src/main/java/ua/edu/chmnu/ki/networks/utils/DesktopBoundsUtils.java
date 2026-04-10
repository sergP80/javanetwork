package ua.edu.chmnu.ki.networks.utils;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

public final class DesktopBoundsUtils {

    private DesktopBoundsUtils() {
    }

    public static Rectangle getVirtualBounds() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = environment.getScreenDevices();

        Rectangle result = null;

        for (GraphicsDevice device : devices) {
            GraphicsConfiguration configuration = device.getDefaultConfiguration();
            Rectangle bounds = configuration.getBounds();

            result = (result == null) ? new Rectangle(bounds) : result.union(bounds);
        }

        if (result == null) {
            throw new IllegalStateException("No screen devices found");
        }

        return result;
    }

    public static double normalize(int value, int min, int size) {
        if (size <= 0) {
            return 0.0d;
        }
        return (value - min) / (double) size;
    }

    public static int denormalize(double normalized, int min, int size) {
        if (size <= 0) {
            return min;
        }

        double clamped = Math.max(0.0d, Math.min(1.0d, normalized));
        return min + (int) Math.round(clamped * size);
    }
}
