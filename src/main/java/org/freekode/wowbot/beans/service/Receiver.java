package org.freekode.wowbot.beans.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Receiver {
    public static final int UPDATE_INTERVAL = 100;
    private static final Logger logger = LogManager.getLogger(Receiver.class);
    public static long lastUpdate = 0;

    private Integer startX;
    private Integer startY;
    private Integer sidePx;
    private Integer columns;
    private Integer rows;

    private Color[][] colors;

    /**
     * wait next colors update, or get instant info
     */
    private boolean wait;


    public Receiver(Integer startX, Integer startY, Integer sidePx, Integer columns, Integer rows) {
        this.startX = startX;
        this.startY = startY;
        this.sidePx = sidePx;
        this.columns = columns;
        this.rows = rows;

        colors = new Color[rows][columns];
        updateColors(true);
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public void updateColors(boolean wait) {
        try {
            while (true) {
                long currentUpdate = System.currentTimeMillis();
                if (currentUpdate > (lastUpdate + UPDATE_INTERVAL)) {
                    lastUpdate = currentUpdate;
                    break;
                } else {
                    if (wait) {
                        Thread.sleep(UPDATE_INTERVAL);
                    } else {
                        return;
                    }
                }
            }

            Robot robot = new Robot();
            Rectangle rect = new Rectangle(startX, startY, sidePx * columns, sidePx * rows);
            BufferedImage image = robot.createScreenCapture(rect);


            Integer pointX = sidePx / 2;
            Integer pointY = sidePx / 2;

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    colors[r][c] = new Color(image.getRGB(pointX, pointY));
                    pointX += sidePx;
                }
                pointY += sidePx;
                pointX = sidePx / 2;
            }
        } catch (AWTException | InterruptedException e) {
            logger.error("during updating the colors was an exception ", e);
        }
    }

    public Double getX() {
        updateColors(wait);

        Color xColor = colors[0][0];
        StringBuilder fullString = new StringBuilder();
        fullString.append(new BigDecimal(xColor.getRed() / 255d).setScale(2, RoundingMode.HALF_UP).toString().replaceAll("0\\.", ""));
        fullString.append(new BigDecimal(xColor.getGreen() / 255d).setScale(2, RoundingMode.HALF_UP).toString().replaceAll("0\\.", ""));
        fullString.append(new BigDecimal(xColor.getBlue() / 255d).setScale(2, RoundingMode.HALF_UP).toString().replaceAll("0\\.", ""));

        return new Integer(fullString.toString()) / 10000d;
    }

    public Double getY() {
        updateColors(wait);
        Color yColor = colors[0][1];

        StringBuilder fullString = new StringBuilder();
        fullString.append(new BigDecimal(yColor.getRed() / 255d).setScale(2, RoundingMode.HALF_UP).toString().replaceAll("0\\.", ""));
        fullString.append(new BigDecimal(yColor.getGreen() / 255d).setScale(2, RoundingMode.HALF_UP).toString().replaceAll("0\\.", ""));
        fullString.append(new BigDecimal(yColor.getBlue() / 255d).setScale(2, RoundingMode.HALF_UP).toString().replaceAll("0\\.", ""));

        return new Integer(fullString.toString()) / 10000d;
    }

    public Double getPitch() {
        updateColors(wait);

        Color pitchColor = colors[0][2];
        StringBuilder fullString = new StringBuilder();
        fullString.append(new BigDecimal(pitchColor.getGreen() / 255d).setScale(2, RoundingMode.HALF_UP).toString().replaceAll("0\\.", ""));
        fullString.append(new BigDecimal(pitchColor.getBlue() / 255d).setScale(2, RoundingMode.HALF_UP).toString().replaceAll("0\\.", ""));
        Double value = new Integer(fullString.toString()) / 1000d;
        if (pitchColor.getRed() / 255 == 1) {
            value *= -1;
        }

        return value;
    }

    public Double getAzimuth() {
        updateColors(wait);

        Color azimuthColor = colors[0][3];
        StringBuilder fullString = new StringBuilder();
        fullString.append(new BigDecimal(azimuthColor.getRed() / 255d).setScale(2, RoundingMode.HALF_UP).toString().replaceAll("0\\.", ""));
        fullString.append(new BigDecimal(azimuthColor.getGreen() / 255d).setScale(2, RoundingMode.HALF_UP).toString().replaceAll("0\\.", ""));
        fullString.append(new BigDecimal(azimuthColor.getBlue() / 255d).setScale(2, RoundingMode.HALF_UP).toString().replaceAll("0\\.", ""));

        return new Integer(fullString.toString()) / 100000d;
    }

    public Boolean isInCombat() {
        updateColors(wait);

        Color color = colors[1][0];
        return color.equals(Color.WHITE);
    }

    public Boolean isHerb() {
        updateColors(wait);

        Color color = colors[2][0];
        return color.equals(Color.WHITE);
    }

    public Boolean isOre() {
        updateColors(wait);

        Color oreColor = colors[2][1];
        return oreColor.equals(Color.WHITE);
    }

    public Boolean bagUpdated() {
        updateColors(wait);

        Color color = colors[3][0];
        return color.equals(Color.WHITE);
    }

    public String toString() {
        return "Receiver[x=" + getX() + "; y=" + getY() + "; azimuth=" + getAzimuth() + "; pitch=" + getPitch() +
                "; isOre=" + isOre() + "; isHerb=" + isHerb() + "; isInCombat=" + isInCombat() + "]";
    }
}
