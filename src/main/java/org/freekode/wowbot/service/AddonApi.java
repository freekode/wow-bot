package org.freekode.wowbot.service;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class AddonApi {
    public static final Point2D addonStart = new Point2D.Double(10, 10);


    public static Color getColor() {
        try {
            Robot robot = new Robot();
            Rectangle rect = new Rectangle(0, 0, 500, 500);
            BufferedImage image = robot.createScreenCapture(rect);

            Color color = robot.getPixelColor(50, 50);
            System.out.println("color = " + color.getBlue());
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
