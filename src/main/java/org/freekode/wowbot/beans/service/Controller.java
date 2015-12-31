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
        while (distance > ConfigKeys.DISTANCE_TOLERANCE) {
            if (receiver.isInCombat()) {
                fight();
            }

            logger.info("distance = " + distance);
            azimuth(StaticFunc.getAzimuth(getCoordinates(), point));

            // we need to run less distance because azimuth angle has very poor results of correction
            // so we run some meters, stop correct azimuth more precisely and run what left
            if (distance > 1) {
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
            double changeRad = currentAzimuth - rad;
            driver.mouseYaw(changeRad);
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
            logger.info("left distance = " + leftDistance);


            if (leftDistance < 0) {
                return;
            }

            if (leftDistance > 0.75) {
                driver.run(0.75);
            } else {
                driver.run(leftDistance);
            }
            Thread.sleep(50);
        }
    }

    public boolean gather() throws InterruptedException {
        pitch(ConfigKeys.GATHER_PITCH);

        Boolean found = false;
        for (int i = 0; i < 20; i++) {
            driver.mouseForGather();
            if (receiver.isHerb() || receiver.isOre()) {
                found = true;
                break;
            }

            driver.centerMouse();
            if (receiver.isHerb() || receiver.isOre()) {
                found = true;
                break;
            }

            driver.keyRotateLeft(0.3);
        }

        if (found) {
            driver.gather();
            Thread.sleep(3000);
        }

        pitch(ConfigKeys.STANDARD_PITCH);
        driver.centerMouse();

        return found;
    }

    /**
     * try to fight as hard as you can
     *
     * @throws InterruptedException
     */
    public void fight() throws InterruptedException {
        logger.info("fight!");
        while (receiver.isInCombat()) {
            driver.keyRotateLeft(0.3);

            driver.getRobot().keyPress(KeyEvent.VK_1);
            driver.getRobot().keyRelease(KeyEvent.VK_1);

            Thread.sleep(500);
        }

        logger.info("k.o.");
    }

    public Driver getDriver() {
        return driver;
    }

    public Receiver getReceiver() {
        return receiver;
    }
}
