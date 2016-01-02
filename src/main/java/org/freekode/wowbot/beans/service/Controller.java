package org.freekode.wowbot.beans.service;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.tools.ConfigKeys;
import org.freekode.wowbot.tools.StaticFunc;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

public class Controller {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    /**
     * to control the character
     */
    private Driver driver;

    /**
     * to receive the information
     */
    private Receiver receiver;


    public Controller(Rectangle window) {
        driver = Driver.getInstance(window);
        receiver = Receiver.getInstance(window);
    }

    public Vector3D getCoordinates() {
        return new Vector3D(getReceiver().getX(), getReceiver().getY(), 0);
    }

    public void init() throws InterruptedException {
        driver.pitchInit();
        pitch(ConfigKeys.STANDARD_PITCH);
    }

    /**
     * move to exact point
     * character automatically turned and run
     *
     * @param point distantion coordinates
     */
    public void moveTo(Vector3D point) throws InterruptedException {
        // get the distance between character and destination
        double distance = new BigDecimal(Vector3D.distance(getCoordinates(), point)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        logger.info("distance = " + distance);
        while (distance > ConfigKeys.DISTANCE_TOLERANCE) {
//            if (receiver.isInCombat()) {
//                fight();
//            }

//            azimuth(StaticFunc.getAzimuth(getCoordinates(), point));
            azimuthByKey(StaticFunc.getAzimuth(getCoordinates(), point));

            // we need to run less distance because azimuth angle has very poor results of correction
            // so we run some meters, stop correct azimuth more precisely and run what left
            if (distance > 1 + ConfigKeys.DISTANCE_TOLERANCE) {
                distance -= 1;
            }

            run(distance);
            distance = new BigDecimal(Vector3D.distance(getCoordinates(), point)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    /**
     * set new azimuth
     *
     * @param rad new azimuth in radians
     */
    public void azimuth(double rad) throws InterruptedException {
        if (rad >= (Math.PI * 2)) {
            return;
        }

        double currentAzimuth = getReceiver().getAzimuth();
        while ((Math.abs(currentAzimuth - rad) > ConfigKeys.AZIMUTH_TOLERANCE)) {
            cleanTarget();

            double changeRad = currentAzimuth - rad;
            driver.mouseYaw(changeRad);
            Thread.sleep(100);

            currentAzimuth = getReceiver().getAzimuth();
        }
    }

    public void azimuthByKey(double rad) throws InterruptedException {
        if (rad >= (Math.PI * 2)) {
            return;
        }

        double currentAzimuth = getReceiver().getAzimuth();
        while ((Math.abs(currentAzimuth - rad) > ConfigKeys.AZIMUTH_KEY_TOLERANCE)) {
            double changeRad = currentAzimuth - rad;

            if (changeRad < 0) {
                changeRad = changeRad * -1;

                if (changeRad > Math.PI) {
                    driver.keyRotateRight(changeRad - Math.PI);
                } else {
                    driver.keyRotateLeft(changeRad);
                }
            } else {
                if (changeRad > Math.PI) {
                    driver.keyRotateLeft(changeRad - Math.PI);
                } else {
                    driver.keyRotateRight(changeRad);
                }
            }

            Thread.sleep(100);
            currentAzimuth = getReceiver().getAzimuth();
        }
    }

    /**
     * set new pitch
     *
     * @param rad new pitch in radians
     */
    public void pitch(double rad) {
        double currentPitch = getReceiver().getPitch();
        while (Math.abs(currentPitch - rad) > ConfigKeys.PITCH_TOLERANCE) {
            double changeRad = currentPitch - rad;
            driver.mousePitch(changeRad);

            currentPitch = getReceiver().getPitch();
        }
    }

    /**
     * run exact distance
     *
     * @param distance distance
     * @throws InterruptedException
     */
    public void run(double distance) throws InterruptedException {
        double leftDistance = distance;
        Vector3D currentLocation = getCoordinates();

        while (leftDistance > ConfigKeys.DISTANCE_TOLERANCE) {
            double alreadyRun = Vector3D.distance(currentLocation, getCoordinates());
            leftDistance = distance - alreadyRun;

            if (leftDistance < 0) {
                return;
            }

            if (leftDistance > 1 + ConfigKeys.DISTANCE_TOLERANCE) {
                driver.run(1);
            } else {
                driver.run(leftDistance);
            }
            Thread.sleep(50);
        }
    }

    public boolean gather() throws InterruptedException {
        // first person view, to see clearly
        driver.fpv();
        if (!receiver.isHerb() || !receiver.isOre()) {
            pitch(ConfigKeys.GATHER_PITCH);
        }

        // rotate the character and "scan" by mouse where is herb
        Integer found = null;
        int steps = 10;
        outer:
        for (int i = 0; i < 50; i++) {
            // remove focus if we have
            cleanTarget();

            // if someone attack us, all gathering will interrupted
            // so fight, and gather again with beginning
            if (receiver.isInCombat()) {
                fight();
                i = 0;
            }

            // scan where is herb, there is a bug
            // sometimes mouse not stopping where he found the herb
            // it just make one step again, it is problem of later detection
            for (int j = 0; j < steps; j++) {
                driver.mouseForGather(j, steps);

                Thread.sleep(50);
                if (receiver.isHerb() || receiver.isOre()) {
                    found = j;
                    found++;

                    if (found > steps) {
                        found = steps;
                    }

                    logger.info("found j = " + found);
                    break outer;
                }
            }

            // rotate the character
            driver.keyRotateLeft(0.5);
        }

        // if found gather
        if (found != null) {
            driver.mouseForGather(found, steps);
            driver.gather();
            Thread.sleep(3000);
        }

        pitch(ConfigKeys.STANDARD_PITCH);
        driver.third();
        driver.centerMouse();

        return found != null;
    }

    public boolean gatherSecond() throws InterruptedException {
        // first person view, to see clearly
        driver.centerMouse();
//        driver.fpvByMouse();
//        if (!receiver.isHerb() || !receiver.isOre()) {
//            pitch(ConfigKeys.GATHER_PITCH);
//        }

        // rotate the character and "scan" by mouse where is herb
        Integer found = null;
        int steps = 4;
        outer:
        for (int i = 0; i < steps; i++) {
            // if someone attack us, all gathering will interrupted
            // so fight, and gather again with beginning
            if (receiver.isInCombat()) {
                fight();
                i = 0;
            }

            driver.mouseForGather(i, steps);

            for (int j = 0; j < 30; j++) {
                Thread.sleep(40);
                if (receiver.isHerb() || receiver.isOre()) {
                    found = i;

                    break outer;
                }
                driver.keyRotateLeft(1);
            }
        }

        // if found gather
        if (found != null) {
            driver.mouseForGather(found, steps);
            driver.gather();
            Thread.sleep(3000);
        }

        pitch(ConfigKeys.STANDARD_PITCH);
//        driver.third();
        driver.centerMouse();

        return found != null;
    }

    /**
     * try to fight as hard as you can
     *
     * @throws InterruptedException
     */
    public void fight() throws InterruptedException {
        logger.info("fight!");

        while (receiver.isInCombat()) {
            if (!receiver.isInActionRange()) {
                cleanTarget();
            }

            driver.getRobot().keyPress(KeyEvent.VK_1);
            driver.getRobot().keyRelease(KeyEvent.VK_1);

            driver.keyRotateLeft(0.3);

            Thread.sleep(700);
        }

        cleanTarget();
        logger.info("k.o.");
    }

    public void cleanTarget() {
        if (receiver.hasTarget()) {
            driver.getRobot().keyPress(KeyEvent.VK_ESCAPE);
            driver.getRobot().keyRelease(KeyEvent.VK_ESCAPE);
        }
    }

    public Driver getDriver() {
        return driver;
    }

    public Receiver getReceiver() {
        return receiver;
    }
}
