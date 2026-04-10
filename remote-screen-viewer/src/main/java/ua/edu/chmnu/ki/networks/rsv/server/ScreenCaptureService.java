package ua.edu.chmnu.ki.networks.rsv.server;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ScreenCaptureService {

    private final Robot robot;
    private final Rectangle captureArea;

    public ScreenCaptureService() throws AWTException {
        this.robot = new Robot();
        this.captureArea = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }

    public BufferedImage capture() {
        return robot.createScreenCapture(captureArea);
    }
}
