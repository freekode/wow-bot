package org.freekode.wowbot.beans.service;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.tools.ConfigKeys;
import org.freekode.wowbot.tools.StaticFunc;

import java.awt.*;
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
        getDriver().pitchInit();
        pitch(ConfigKeys.STANDARD_PITCH);
    }

    /**
     * move to exact point
     * character automatically turned and run
     *
     * @param point distantion coordinates
     */
    public void moveTo(Vector3D point) throws InterruptedException {
        while (true) {
            // get the distance between character and destination
            double distance = new BigDecimal(Vector3D.distance(getCoordinates(), point)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            logger.info("distance = " + distance);
            if (distance <= ConfigKeys.DISTANCE_TOLERANCE) {
                return;
            }

            azimuth(StaticFunc.getAzimuth(getCoordinates(), point));

            // we need to run less distance because azimuth angle has very poor results of correction
            // so we run some meters, stop correct azimuth more precisely and run what left
            if (distance > 1) {
                distance -= 1;
            }

            run(distance);
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

        while (true) {
            double currentAzimuth = getReceiver().getAzimuth();
            if (Math.abs(currentAzimuth - rad) > ConfigKeys.AZIMUTH_TOLERANCE) {
                double changeRad = currentAzimuth - rad;

                getDriver().mouseYaw(changeRad);
            } else {
                break;
            }
        }
    }

    /**
     * set new pitch
     *
     * @param rad new pitch in radians
     */
    public void pitch(double rad) {
        while (true) {
            double currentPitch = getReceiver().getPitch();
            if (Math.abs(currentPitch - rad) > ConfigKeys.PITCH_TOLERANCE) {
                double changeRad = currentPitch - rad;
                getDriver().mousePitch(changeRad);
            } else {
                break;
            }
        }
    }

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
                getDriver().run(0.75);
            } else {
                getDriver().run(leftDistance);
            }
            Thread.sleep(50);
        }
    }

    public void fpv() throws InterruptedException {
        getDriver().fpv();
    }

    public Driver getDriver() {
        return driver;
    }

    public Receiver getReceiver() {
        return receiver;
    }
}
