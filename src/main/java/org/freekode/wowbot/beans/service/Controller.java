package org.freekode.wowbot.beans.service;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.freekode.wowbot.tools.StaticFunc;

import java.math.BigDecimal;

public class Controller {
    public static final double STANDARD_PITCH = -0.25;
    public static final double PITCH_TOLERANCE = 0.02;
    public static final double AZIMUTH_TOLERANCE = 0.02;
    public static final double DISTANCE_TOLERANCE = 0.05;

    /**
     * to control the character
     */
    private Driver driver;

    /**
     * to receive the information
     */
    private Receiver receiver;


    public Controller(Driver driver, Receiver receiver) {
        this.driver = driver;
        this.receiver = receiver;
    }

    public Vector3D getCoordinates() {
        return new Vector3D(getReceiver().getX(), getReceiver().getY(), 0);
    }

    public void init() throws InterruptedException {
        getDriver().pitchInit();
        pitch(STANDARD_PITCH);
    }

    public void moveTo(Vector3D point) {
        while (true) {
            double distance = new BigDecimal(Vector3D.distance(getCoordinates(), point)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (distance <= DISTANCE_TOLERANCE) {
                return;
            }

            azimuth(StaticFunc.getAzimuth(getCoordinates(), point));

            if (distance > 1) {
                distance -= 1;
            }

            getDriver().run(distance);
        }
    }

    public void azimuth(double rad) {
        if (rad >= (Math.PI * 2)) {
            return;
        }

        while (true) {
            double currentAzimuth = getReceiver().getAzimuth();
            if (Math.abs(currentAzimuth - rad) > AZIMUTH_TOLERANCE) {
                double changeRad = currentAzimuth - rad;

                getDriver().mouseYaw(changeRad);
            } else {
                break;
            }
        }
    }

    public void pitch(double rad) {
        while (true) {
            double currentPitch = getReceiver().getPitch();
            if (Math.abs(currentPitch - rad) > PITCH_TOLERANCE) {
                double changeRad = currentPitch - rad;
                getDriver().mousePitch(changeRad);
            } else {
                break;
            }
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
