package org.freekode.wowbot.beans.impl;

import org.freekode.wowbot.beans.interfaces.Character;
import org.freekode.wowbot.beans.interfaces.Intelligence;
import org.freekode.wowbot.tools.StaticFunc;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class FishingAI implements Intelligence {
    public static final double STANDARD_PITCH = -0.25;
    public static final int FISH_BUTTON = KeyEvent.VK_EQUALS;
    public static final int FISHING_TIME_SEC = 20;
    public static final int FAIL_TRYINGS = 10;
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

    // white-yellow colors
    public static final Color[] THIRD_COLORS = {
            Color.decode("#837056"),
            Color.decode("#454033"),
            Color.decode("#9d805d"),
            Color.decode("#886847"),
    };

    // metal stick of bobber
    public static final Color[] FOURTH_COLORS = {
            Color.decode("#4b5054"),
            Color.decode("#6e6865"),
            Color.decode("#62696e"),
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

        System.out.println("start fishing");
        for (int i = 0; i < FAIL_TRYINGS; i++) {
            System.out.println("try = " + i);
            fish();

            BufferedImage image = StaticFunc.cutImage(StaticFunc.calculateCutSquare(windowArea, SEARCH_SQUARE), false);
            int[] bobberPoint = StaticFunc.findColor(image, FIRST_COLORS, 6);
            if (bobberPoint != null) {
                System.out.println("first color");
                Rectangle bobberSquare = new Rectangle(bobberPoint[0] - 30, bobberPoint[1] - 20, 80, 50);
                BufferedImage bobberImage = StaticFunc.cutImage(StaticFunc.calculateCutSquare(windowArea,
                        StaticFunc.calculateCutSquare(SEARCH_SQUARE, bobberSquare)), false);

                if (StaticFunc.findColor(bobberImage, SECOND_COLORS, 5) != null) {
                    System.out.println("second color");
                    if (StaticFunc.findColor(bobberImage, THIRD_COLORS, 5) != null) {
                        System.out.println("third color");
                        int[] stickCoordinates = StaticFunc.findColor(bobberImage, FOURTH_COLORS, 5);
                        if (stickCoordinates != null) {
                            System.out.println("fourth = " + Arrays.toString(stickCoordinates));

                            Rectangle stickSquare = new Rectangle(stickCoordinates[0] - 13, stickCoordinates[1] - 13, 26, 26);
                            Rectangle trackSquare = StaticFunc.calculateCutSquare(windowArea,
                                    StaticFunc.calculateCutSquare(SEARCH_SQUARE,
                                            StaticFunc.calculateCutSquare(bobberSquare, stickSquare)));

                            System.out.println(new Color(stickCoordinates[2]).toString());
                            trackingSquare(trackSquare, new Color(stickCoordinates[2]));

                            break;
                        }
                    }
                }
            }

            robot.delay(500);
        }
    }

    public void trackingSquare(Rectangle rectangle, Color color) {
        System.out.println("start tracking");
        long endTime = System.currentTimeMillis() / 1000 + FISHING_TIME_SEC;

        while ((System.currentTimeMillis() / 1000) <= endTime) {
            try {
                BufferedImage trackImage = StaticFunc.cutImage(rectangle, false);
                int[] trackCoordinates = StaticFunc.findColor(trackImage, new Color[]{color}, 8);
                if (trackCoordinates == null) {
                    StaticFunc.cutImage(rectangle, true);
                    System.out.println("wow!!!");
                    break;
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void fish() {
        character.getControl().pressKey(FISH_BUTTON);
        robot.delay(1000);
    }
}
