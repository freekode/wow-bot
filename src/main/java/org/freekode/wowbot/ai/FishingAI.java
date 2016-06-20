package org.freekode.wowbot.ai;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.entity.fishing.FishingKitEntity;
import org.freekode.wowbot.entity.fishing.FishingOptionsEntity;
import org.freekode.wowbot.entity.fishing.FishingRecordEntity;
import org.freekode.wowbot.tools.StaticFunc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FishingAI extends Intelligence<FishingRecordEntity> {
    private static final Logger logger = LogManager.getLogger(FishingAI.class);
    private static final int FISHING_TIME_SEC = 20;
    private static final int CHECK_IF_CAUGHT_SEC = 2;
    private static final double FIRST_COLOR_TOLERANCE = 7;
    private static final double SECOND_COLOR_TOLERANCE = 6;
    private static final double THIRD_COLOR_TOLERANCE = 5;
    private final int TRACKING_INTERVAL = 100;
    private final int SEARCH_SQUARE_X1_OFFSET = 400;
    private final int SEARCH_SQUARE_Y1_OFFSET = 100;
    private final int SEARCH_SQUARE_X2_OFFSET = 400;
    private final int SEARCH_SQUARE_Y2_OFFSET = 220;
    private Rectangle calculatedSearchSquare ;
    private FishingRecordEntity record;
    private List<FishingKitEntity> enabledKits;
    private int fishKey;
    private int failTryings;


    public FishingAI(FishingOptionsEntity options) {
        this.fishKey = KeyStroke.getKeyStroke(options.getFishKey().charAt(0), 0).getKeyCode();
        this.failTryings = options.getFailTryings();

        this.enabledKits = new ArrayList<>();
        for (FishingKitEntity kit : options.getKits()) {
            if (kit.getEnable()) {
                enabledKits.add(kit);
            }
        }

        logger.info("fish key = " + fishKey + "; fail tryings = " + failTryings + "; enabledKits = " + enabledKits.size());
    }

    @Override
    public Boolean processing() throws InterruptedException {
        getController().init();
        getController().pitch(-0.35);
        startFishing();

        return true;
    }

    public void startFishing() throws InterruptedException {
        logger.info("start fishing");

        // calculate search square, to work with different window sizes
        int searchSquareWidth = (int) (getWindowArea().getWidth() - SEARCH_SQUARE_X1_OFFSET - SEARCH_SQUARE_X2_OFFSET);
        int searchSquareHeight = (int) (getWindowArea().getHeight() - SEARCH_SQUARE_Y1_OFFSET - SEARCH_SQUARE_Y2_OFFSET);
        calculatedSearchSquare = new Rectangle(SEARCH_SQUARE_X1_OFFSET, SEARCH_SQUARE_Y2_OFFSET, searchSquareWidth, searchSquareHeight);
        logger.info("searching rectangle = " + calculatedSearchSquare);

        // fishing rectangle where is our bobber can be located in the window
        Rectangle imageRect = StaticFunc.calculateCutSquare(getWindowArea(), calculatedSearchSquare);

        for (int i = 0; i < failTryings; i++) {
            logger.info("try = " + i);
            mouseOut();
            fish();

            record = new FishingRecordEntity(new Date());
            send(record);

            BufferedImage image = StaticFunc.cutImage(imageRect);

            // lets find first color, and kit from which that red color
            int[] bobberPoint = null;
            FishingKitEntity currentKit = null;
            for (FishingKitEntity kit : enabledKits) {
                bobberPoint = findColor(image, kit.getFirstColors(), FIRST_COLOR_TOLERANCE);
                if (bobberPoint != null) {
                    currentKit = kit;
                    break;
                }
            }
            if (bobberPoint == null) {
                continue;
            }
            Color redColor = new Color(bobberPoint[2]);
            logger.info("first color found = " + redColor.toString());
            record.setFirst(redColor);
            record.setKitName(currentKit.getName());
            send(record);


            // lets find second color, and we must find it only within small rectangle
            Rectangle bobberSquare = new Rectangle(bobberPoint[0] - 30, bobberPoint[1] - 20, 80, 50);
            Rectangle bobberRect = StaticFunc.calculateCutSquare(getWindowArea(),
                    StaticFunc.calculateCutSquare(calculatedSearchSquare, bobberSquare));
            BufferedImage bobberImage = StaticFunc.cutImage(bobberRect);
            int[] bobberPart = findColor(bobberImage, currentKit.getSecondColors(), SECOND_COLOR_TOLERANCE);
            if (bobberPart == null) {
                continue;
            }
            Color blueColor = new Color(bobberPart[2]);
            logger.info("second color found = " + blueColor.toString());
            record.setSecond(blueColor);
            send(record);


            // last check that it is really the bobber, lets find the main part of the bobber
            int[] bobberCoordinates = findColor(bobberImage, currentKit.getThirdColors(), THIRD_COLOR_TOLERANCE);
            if (bobberCoordinates == null) {
                continue;
            }
            Color whiteYellowColor = new Color(bobberCoordinates[2]);
            logger.info("third color found = " + whiteYellowColor.toString());
            record.setThird(whiteYellowColor);
            send(record);


            // everything found, track for changes
            Rectangle stickSquare = new Rectangle(bobberCoordinates[0] - 10, bobberCoordinates[1] - 5, 22, 22);
            Rectangle trackRect = StaticFunc.calculateCutSquare(getWindowArea(),
                    StaticFunc.calculateCutSquare(calculatedSearchSquare,
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
            Thread.sleep(TRACKING_INTERVAL);
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
        int x = (int) (getWindowArea().getX() + calculatedSearchSquare.getX() + calculatedSearchSquare.getWidth() + 20);
        int y = (int) (getWindowArea().getY() + calculatedSearchSquare.getY());
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
