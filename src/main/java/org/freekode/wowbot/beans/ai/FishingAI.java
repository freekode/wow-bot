package org.freekode.wowbot.beans.ai;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.entity.fishing.FishingRecordEntity;
import org.freekode.wowbot.tools.StaticFunc;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FishingAI extends Intelligence<FishingRecordEntity> {
    private static final Logger logger = LogManager.getLogger(FishingAI.class);
    private static final int FISHING_TIME_SEC = 20;
    private static final int CHECK_IF_CAUGHT_SEC = 2;
    private final Rectangle SEARCH_SQUARE = new Rectangle(400, 110, 440, 390);
    private FishingRecordEntity record;
    // red colors
    private List<Color> firstColors;
    // blue colors
    private List<Color> secondColors;
    // white-yellow colors
    private List<Color> thirdColors;
    private int fishKey;
    private int failTryings;


    public FishingAI(int fishKey, int failTryings, List<Color> firstColors, List<Color> secondColors, List<Color> thirdColors) {
        this.fishKey = fishKey;
        this.failTryings = failTryings;
        this.firstColors = firstColors;
        this.secondColors = secondColors;
        this.thirdColors = thirdColors;

        logger.info("fish key = " + fishKey + "; fail tryings = " + failTryings +
                "; [" + firstColors.size() + ", " + secondColors.size() + ", " + thirdColors.size() + "]");
    }

    @Override
    public Boolean processing() throws InterruptedException {
        getController().init();
        startFishing();

        return true;
    }

    public void startFishing() throws InterruptedException {
        logger.info("start fishing");
        for (int i = 0; i < failTryings; i++) {
            logger.info("try = " + i);
            mouseOut();
            fish();

            record = new FishingRecordEntity(new Date());
            send(record);

            Rectangle imageRect = StaticFunc.calculateCutSquare(getWindowArea(), SEARCH_SQUARE);
            BufferedImage image = StaticFunc.cutImage(imageRect);
            int[] bobberPoint = findColor(image, firstColors, 7);
            if (bobberPoint == null) {
                continue;
            }
            record.setFirst(new Color(bobberPoint[2]));
            send(record);


            logger.info("first color found = " + new Color(bobberPoint[2]).toString());
            Rectangle bobberSquare = new Rectangle(bobberPoint[0] - 30, bobberPoint[1] - 20, 80, 50);
            Rectangle bobberRect = StaticFunc.calculateCutSquare(getWindowArea(),
                    StaticFunc.calculateCutSquare(SEARCH_SQUARE, bobberSquare));
            BufferedImage bobberImage = StaticFunc.cutImage(bobberRect);
            int[] bobberPart = findColor(bobberImage, secondColors, 6);
            if (bobberPart == null) {
                continue;
            }
            record.setSecond(new Color(bobberPart[2]));
            send(record);


            logger.info("second color found = " + new Color(bobberPart[2]).toString());
            int[] bobberCoordinates = findColor(bobberImage, thirdColors, 5);
            if (bobberCoordinates == null) {
                continue;
            }
            record.setThird(new Color(bobberCoordinates[2]));
            send(record);


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
    }

    public void trackingSquare(Rectangle rectangle, Color color) throws InterruptedException {
        long endTime = System.currentTimeMillis() / 1000 + FISHING_TIME_SEC;

        while ((System.currentTimeMillis() / 1000) <= endTime) {
            BufferedImage image = StaticFunc.cutImage(rectangle);
            int[] trackCoordinates = findColor(image, Collections.singletonList(color), 8);
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

    public int[] findColor(BufferedImage image, List<Color> colors, double similarity) {
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

        record.setCaught(caught);
        send(record);
    }
}
