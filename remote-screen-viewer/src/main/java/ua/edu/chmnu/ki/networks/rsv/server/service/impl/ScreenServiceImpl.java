package ua.edu.chmnu.ki.networks.rsv.server.service.impl;

import ua.edu.chmnu.ki.networks.rsv.server.service.ScreenService;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenServiceImpl implements ScreenService {

    private final Robot robot;
    private final Rectangle captureArea;

    public ScreenServiceImpl() throws AWTException {
        this.robot = new Robot();
        this.captureArea = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }

    @Override
    public BufferedImage capture() {
        return robot.createScreenCapture(captureArea);
    }
}
