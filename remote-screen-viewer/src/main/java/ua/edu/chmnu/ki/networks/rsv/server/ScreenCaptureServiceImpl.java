package ua.edu.chmnu.ki.networks.rsv.server;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenCaptureServiceImpl implements ScreenCaptureService {

    private final Robot robot;
    private final Rectangle captureArea;

    public ScreenCaptureServiceImpl() throws AWTException {
        this.robot = new Robot();
        this.captureArea = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }

    @Override
    public BufferedImage capture() {
        return robot.createScreenCapture(captureArea);
    }
}
