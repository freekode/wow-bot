package org.freekode.wowbot.beans;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.freekode.wowbot.tools.StaticFunc;

import java.math.BigDecimal;
import java.util.ResourceBundle;

public class Character {
//    private static Character INSTANCE;
//    public static final double STANDARD_PITCH = -0.25;
//    public static final double PITCH_TOLERANCE = 0.02;
//    public static final double AZIMUTH_TOLERANCE = 0.02;
//    public static final double DISTANCE_TOLERANCE = 0.05;
//    private AddonReceiver api;
//    private ResourceBundle.Control control;
//
//
//    private Character(AddonReceiver api, ResourceBundle.Control control) {
//        this.api = api;
//        this.control = control;
//    }
//
//    public static Character getInstance(AddonReceiver api, ResourceBundle.Control control) {
//        if (INSTANCE == null) {
//            INSTANCE = new Character(api, control);
//        }
//
//        return INSTANCE;
//    }
//
//    public void init() throws InterruptedException {
//        control.pitchInit();
//        pitch(STANDARD_PITCH);
//    }
//
//    public double getPitch() {
//        return api.getPitch(true);
//    }
//
//    public double getAzimuth() {
//        return api.getAzimuth(true);
//    }
//
//    public Vector3D getCoordinates() {
//        return new Vector3D(api.getX(true), api.getY(true), 0);
//    }
//
//    public void pitch(double rad) {
//        while (true) {
//            double currentPitch = getPitch();
//            if (Math.abs(currentPitch - rad) > PITCH_TOLERANCE) {
//                double changeRad = currentPitch - rad;
//                control.mousePitch(changeRad);
//            } else {
//                break;
//            }
//        }
//    }
//
//    public void azimuth(double rad) {
//        while (true) {
//            double currentAzimuth = getAzimuth();
//            if (Math.abs(currentAzimuth - rad) > AZIMUTH_TOLERANCE) {
//                double changeRad = currentAzimuth - rad;
//
//                control.mouseYaw(changeRad);
//            } else {
//                break;
//            }
//        }
//    }
//
//    public void moveTo(Vector3D point) {
//        while (true) {
//            double distance = new BigDecimal(Vector3D.distance(getCoordinates(), point)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
//            if (distance <= DISTANCE_TOLERANCE) {
//                return;
//            }
//
//            azimuth(StaticFunc.getAzimuth(getCoordinates(), point));
//
//            if (distance > 1) {
//                distance -= 1;
//            }
//
//            control.run(distance);
//        }
//    }
//
//    public void fpv() throws InterruptedException {
//        control.fpv();
//    }
//
//    public boolean isOre() {
//        return api.isOre(true);
//    }
//
//    public boolean isInCombat() {
//        return api.isInCombat(true);
//    }
//
//    public Control getControl() {
//        return control;
//    }
//
//    @Override
//    public String toString() {
//        return "Character [" + getCoordinates() + "; pitch=" + getPitch() + "; azimuth=" + getAzimuth() + "]";
//    }
}
