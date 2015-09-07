package org.freekode.wowbot.beans.impl;

import org.freekode.wowbot.beans.interfaces.Character;
import org.freekode.wowbot.beans.interfaces.Intelligence;
import org.freekode.wowbot.tools.StaticFunc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FishingAI implements Intelligence {
    public static final double STANDARD_PITCH = -0.25;
    public static final int FISH_BUTTON = KeyEvent.VK_EQUALS;
    public static final int FAIL_TRYINGS = 1;
    public static final Rectangle SEARCH_SQUARE = new Rectangle(400, 110, 440, 390);

    // red colors
    public static final Color[] FIRST_COLORS = {
            Color.decode("#591f0a"),
            Color.decode("#481607"),
            Color.decode("#3f1510"),
            Color.decode("#4a190a"),
            Color.decode("#4e1b0a"),
    };

    // blue colors
    public static final Color[] SECOND_COLORS = {
            Color.decode("#2b323e"),
            Color.decode("#272d3c"),
            Color.decode("#1a1c2b"),
            Color.decode("#1f202d"),
    };


    private Character character;
    private Rectangle windowArea;
    private Robot robot;


    public FishingAI(Character character, Rectangle windowArea) {
        this.character = character;
        this.windowArea = windowArea;


        try {
            robot = new Robot();
            robot.setAutoDelay(30);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        character.pitch(STANDARD_PITCH);
        character.fpv();

        System.out.println("get square");

        for (int i = 0; i < FAIL_TRYINGS; i++) {
            character.fish(FISH_BUTTON);
            System.out.println("try = " + i);
            BufferedImage image = cutImage(calculateCutSquare(windowArea, SEARCH_SQUARE));
            int[] bobberPoint = findColor(image, FIRST_COLORS);
            if (bobberPoint != null) {
                BufferedImage bobberImage = cutImage(calculateCutSquare(windowArea, calculateCutSquare(SEARCH_SQUARE, new Rectangle(bobberPoint[0] - 30, bobberPoint[1] - 20, 80, 50))));
                if (findColor(bobberImage, SECOND_COLORS) != null) {
                    System.out.println("second");
                }
            }
            robot.delay(500);
        }
    }

    public BufferedImage cutImage(Rectangle rectangle) {
        BufferedImage image = robot.createScreenCapture(rectangle);

        File file = new File("test.png");
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public int[] findColor(BufferedImage image, Color[] colors) {
        int[][] result = StaticFunc.convertTo2DWithoutUsingGetRGB(image);


        for (int y = 0; y < result.length; y++) {
            for (int x = 0; x < result[y].length; x++) {
                Color imageColor = new Color(result[y][x]);
                for (Color color : colors) {
                    if (StaticFunc.isSimilarColor(imageColor, color, 11)) {
                        return new int[]{x, y};
                    }
                }
            }
        }

        return null;
    }

    public Rectangle calculateCutSquare(Rectangle main, Rectangle sub) {
        int startX = (int) (main.getX() + sub.getX());
        int startY = (int) (main.getY() + sub.getY());
        int width = (int) sub.getWidth();
        int height = (int) sub.getHeight();

        return new Rectangle(startX, startY, width, height);
    }
}
