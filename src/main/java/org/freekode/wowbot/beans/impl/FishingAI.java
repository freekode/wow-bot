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
            Color.decode("#6b240e"),
            Color.decode("#4d160e"),

            Color.decode("#c62f12"),
            Color.decode("#94260b"),

            Color.decode("#49150a"),
            Color.decode("#341209"),

//            Color.decode("#591f0a"),
//            Color.decode("#481607"),
//            Color.decode("#3f1510"),
//            Color.decode("#4a190a"),
//            Color.decode("#4e1b0a"),
    };

    // blue colors
    public static final Color[] SECOND_COLORS = {
            Color.decode("#353c59"),
            Color.decode("#2f3756"),

            Color.decode("#4d5363"),
            Color.decode("#626574"),

            Color.decode("#1e2d4a"),
            Color.decode("#17263d"),

//            Color.decode("#2b323e"),
//            Color.decode("#272d3c"),
//            Color.decode("#1a1c2b"),
//            Color.decode("#1f202d"),
    };

    // white-yellow colors
    public static final Color[] THIRD_COLORS = {
            Color.decode("#6a5344"),
            Color.decode("#756051"),

            Color.decode("#4d4030"),
            Color.decode("#624d38"),

            Color.decode("#504d3e"),
            Color.decode("#42453a"),
//            Color.decode("#837056"),
//            Color.decode("#454033"),
//            Color.decode("#9d805d"),
//            Color.decode("#886847"),
    };

    // metal stick of bobber
    public static final Color[] FOURTH_COLORS = {
            Color.decode("#5c5d6c"),
            Color.decode("#656776"),

            Color.decode("#626367"),
            Color.decode("#404445"),

            Color.decode("#5a6e79"),
            Color.decode("#50626c"),
    };


    private Character character;
    private Rectangle windowArea;


    public FishingAI(Character character, Rectangle windowArea) {
        this.character = character;
        this.windowArea = windowArea;
    }

    @Override
    public void run() {
        character.pitch(STANDARD_PITCH);
        character.fpv();

        System.out.println("start fishing");
        for (int i = 0; i < FAIL_TRYINGS; i++) {
            System.out.println("try = " + i);
            fish();

            BufferedImage image = StaticFunc.cutImage(StaticFunc.calculateCutSquare(windowArea, SEARCH_SQUARE), true, "search");
            int[] bobberPoint = findColor(image, FIRST_COLORS, 7);
            if (bobberPoint != null) {
                System.out.println("first color");
                Rectangle bobberSquare = new Rectangle(bobberPoint[0] - 30, bobberPoint[1] - 20, 80, 50);
                BufferedImage bobberImage = StaticFunc.cutImage(StaticFunc.calculateCutSquare(windowArea,
                        StaticFunc.calculateCutSquare(SEARCH_SQUARE, bobberSquare)), true, "bobber");

                if (findColor(bobberImage, SECOND_COLORS, 7) != null) {
                    System.out.println("second color");
                    int[] bobberCoordinates = findColor(bobberImage, THIRD_COLORS, 6);
                    if (bobberCoordinates != null) {
                        System.out.println("third color");
                        int[] stickCoordinates = findColor(bobberImage, FOURTH_COLORS, 10);
                        if (stickCoordinates != null) {
                            System.out.println("fourth = " + Arrays.toString(bobberCoordinates));

                            Rectangle stickSquare = new Rectangle(bobberCoordinates[0] - 5, bobberCoordinates[1] - 10, 20, 20);
                            Rectangle trackSquare = StaticFunc.calculateCutSquare(windowArea,
                                    StaticFunc.calculateCutSquare(SEARCH_SQUARE,
                                            StaticFunc.calculateCutSquare(bobberSquare, stickSquare)));

                            StaticFunc.cutImage(trackSquare, true, "tracking");
                            trackingSquare(trackSquare, new Color(bobberCoordinates[2]));
                            break;
                        }
                    }
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void trackingSquare(Rectangle rectangle, Color color) {
        System.out.println("start tracking");
        long endTime = System.currentTimeMillis() / 1000 + FISHING_TIME_SEC;

        while ((System.currentTimeMillis() / 1000) <= endTime) {
            try {
                BufferedImage image = StaticFunc.cutImage(rectangle, false, null);
                int[] trackCoordinates = findColor(image, new Color[]{color}, 8);
                if (trackCoordinates == null) {
                    StaticFunc.cutImage(rectangle, true, "wtf");
                    int x = (int) (rectangle.getX() + (rectangle.getWidth() / 2));
                    int y = (int) (rectangle.getY() + (rectangle.getHeight() / 2));
                    loot(x, y);
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
        try {
            character.getControl().pressKey(FISH_BUTTON);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int[] findColor(BufferedImage image, Color[] colors, double similarity) {
//        int[][] pixels = StaticFunc.convertTo2DWithoutUsingGetRGB(image);
        int[][] pixels = StaticFunc.convertTo2DUsingGetRGB(image);

        return StaticFunc.findColor(pixels, colors, similarity);
    }

    public void loot(int x, int y) {
        character.getControl().mouse(x, y);
    }
}
