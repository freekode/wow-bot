package org.freekode.wowbot.beans.ai;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.modules.fishing.FishingOptionsModel;
import org.freekode.wowbot.modules.fishing.FishingRecordModel;
import org.freekode.wowbot.tools.StaticFunc;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Date;

public class FishingAI extends Intelligence<FishingRecordModel> {
    private static final Logger logger = LogManager.getLogger(FishingAI.class);
    private static final int FISHING_TIME_SEC = 20;
    private static final int CHECK_IF_CAUGHT_SEC = 2;
    private Color firstFound;
    private Color secondFound;
    private Color thirdFound;

    // red colors
    public final Color[] FIRST_COLORS = {
            Color.decode("#6b240e"),
            Color.decode("#4d160e"),

            Color.decode("#c62f12"),
            Color.decode("#94260b"),

            Color.decode("#49150a"),
            Color.decode("#341209"),
    };
    // blue colors
    public final Color[] SECOND_COLORS = {
            Color.decode("#353c59"),
            Color.decode("#2f3756"),

            Color.decode("#4d5363"),
            Color.decode("#626574"),

            Color.decode("#1e2d4a"),
            Color.decode("#17263d"),
    };
    // white-yellow colors
    public final Color[] THIRD_COLORS = {
            Color.decode("#6a5344"),
            Color.decode("#756051"),

            Color.decode("#4d4030"),
            Color.decode("#624d38"),

            Color.decode("#504d3e"),
            Color.decode("#42453a"),
    };
    private final Rectangle SEARCH_SQUARE = new Rectangle(400, 110, 440, 390);
    private int fishKey;
    private int failTryings;


    public FishingAI(int fishKey, int failTryings) {
        this.fishKey = fishKey;
        this.failTryings = failTryings;

        logger.info("initialization; fish key = " + fishKey + "; fail tryings = " + failTryings);
    }

    @Override
    public Boolean processing() throws InterruptedException {
        getController().init();


        logger.info("start fishing");
        for (int i = 0; i < failTryings; i++) {
            logger.info("try = " + i);
            mouseOut();
            fish();

            Rectangle imageRect = StaticFunc.calculateCutSquare(getWindowArea(), SEARCH_SQUARE);
            BufferedImage image = StaticFunc.cutImage(imageRect);
            int[] bobberPoint = findColor(image, FIRST_COLORS, 7);
            if (bobberPoint == null) {
                continue;
            }
            firstFound = new Color(bobberPoint[2]);


            logger.info("first color found = " + new Color(bobberPoint[2]).toString());
            Rectangle bobberSquare = new Rectangle(bobberPoint[0] - 30, bobberPoint[1] - 20, 80, 50);
            Rectangle bobberRect = StaticFunc.calculateCutSquare(getWindowArea(),
                    StaticFunc.calculateCutSquare(SEARCH_SQUARE, bobberSquare));
            BufferedImage bobberImage = StaticFunc.cutImage(bobberRect);
            int[] bobberPart = findColor(bobberImage, SECOND_COLORS, 6);
            if (bobberPart == null) {
                clearColors();
                continue;
            }
            secondFound = new Color(bobberPart[2]);


            logger.info("second color found = " + new Color(bobberPart[2]).toString());
            int[] bobberCoordinates = findColor(bobberImage, THIRD_COLORS, 5);
            if (bobberCoordinates == null) {
                clearColors();
                continue;
            }
            thirdFound = new Color(bobberCoordinates[2]);


            logger.info("third color found = " + new Color(bobberCoordinates[2]).toString());
            Rectangle stickSquare = new Rectangle(bobberCoordinates[0] - 10, bobberCoordinates[1] - 5, 22, 22);
            Rectangle trackRect = StaticFunc.calculateCutSquare(getWindowArea(),
                    StaticFunc.calculateCutSquare(SEARCH_SQUARE,
                            StaticFunc.calculateCutSquare(bobberSquare, stickSquare)));

            StaticFunc.cutImage(trackRect);
            trackingSquare(trackRect, new Color(bobberCoordinates[2]));
            i = 0;

            Thread.sleep(500);
        }

        logger.info("sorry, can not find the bobber. stopping");

        return true;
    }

    public void clearColors() {
        firstFound = null;
        secondFound = null;
        thirdFound = null;
    }

    public void trackingSquare(Rectangle rectangle, Color color) throws InterruptedException {
        long endTime = System.currentTimeMillis() / 1000 + FISHING_TIME_SEC;

        while ((System.currentTimeMillis() / 1000) <= endTime) {
            BufferedImage image = StaticFunc.cutImage(rectangle);
            int[] trackCoordinates = findColor(image, new Color[]{color}, 8);
            if (trackCoordinates == null) {
                StaticFunc.cutImage(rectangle);
                int x = (int) (rectangle.getX() + (rectangle.getWidth() / 2));
                int y = (int) (rectangle.getY() + (rectangle.getHeight() / 2));

                logger.info("lets take it...");
                loot(x, y);
                checkIfCaught();
                break;
            }
            Thread.sleep(100);
        }
    }

    public void fish() throws InterruptedException {
        getController().getDriver().pressKey(fishKey);
        Thread.sleep(2000);
    }

    public int[] findColor(BufferedImage image, Color[] colors, double similarity) {
//        int[][] pixels = StaticFunc.convertTo2DWithoutUsingGetRGB(image);
        int[][] pixels = StaticFunc.convertTo2DUsingGetRGB(image);

        return StaticFunc.findColor(pixels, colors, similarity);
    }

    public void loot(int x, int y) throws InterruptedException {
        getController().getDriver().mouse(x, y);
        getController().getDriver().gather();
    }

    public void mouseOut() throws InterruptedException {
        int x = (int) (getWindowArea().getX() + SEARCH_SQUARE.getX() + SEARCH_SQUARE.getWidth() + 20);
        int y = (int) (getWindowArea().getY() + SEARCH_SQUARE.getY());
        getController().getDriver().mouse(x, y);
        Thread.sleep(100);
    }

    public void checkIfCaught() throws InterruptedException {
        long endTime = System.currentTimeMillis() / 1000 + CHECK_IF_CAUGHT_SEC;

        Boolean caught = false;
        while ((System.currentTimeMillis() / 1000) <= endTime) {
            if (getController().getReceiver().bagUpdated()) {
                caught = true;
                break;
            }
            Thread.sleep(100);
        }

        if (caught) {
            logger.info("got it, yay!");
        } else {
            logger.info("oh, no :(");
        }

        FishingRecordModel model = new FishingRecordModel(new Date(), caught, firstFound, secondFound, thirdFound);
        send(model);
        clearColors();
    }
}
