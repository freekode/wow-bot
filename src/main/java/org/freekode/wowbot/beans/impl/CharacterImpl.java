package org.freekode.wowbot.beans.impl;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.freekode.wowbot.beans.interfaces.AddonApi;
import org.freekode.wowbot.beans.interfaces.Control;
import org.freekode.wowbot.tools.StaticFunc;
import org.freekode.wowbot.beans.interfaces.Character;

import java.math.BigDecimal;

public class CharacterImpl implements Character {
    public static final double STANDARD_PITCH = -0.25;
    public static final double PITCH_TOLERANCE = 0.02;
    public static final double AZIMUTH_TOLERANCE = 0.02;
    public static final double DISTANCE_TOLERANCE = 0.05;
    private AddonApi api;
    private Control control;


    public CharacterImpl(AddonApi api, Control control) {
        this.api = api;
        this.control = control;

        control.pitchInit();
        control.fpv();
        pitch(STANDARD_PITCH);
    }

    @Override
    public double getPitch() {
        return api.getPitch(true);
    }

    @Override
    public double getAzimuth() {
        return api.getAzimuth(true);
    }

    @Override
    public Vector3D getCoordinates() {
        return new Vector3D(api.getX(true), api.getY(true), 0);
    }

    @Override
    public void pitch(double rad) {
        while (true) {
            double currentPitch = getPitch();
            if (Math.abs(currentPitch - rad) > PITCH_TOLERANCE) {
                double changeRad = currentPitch - rad;
                control.mousePitch(changeRad);
            } else {
                break;
            }
        }
    }

    @Override
    public void azimuth(double rad) {
        while (true) {
            double currentAzimuth = getAzimuth();
            if (Math.abs(currentAzimuth - rad) > AZIMUTH_TOLERANCE) {
                double changeRad = currentAzimuth - rad;

                control.mouseYaw(changeRad);
            } else {
                break;
            }
        }
    }

    @Override
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

            control.run(distance);
        }
    }

    @Override
    public void fpv() {
        control.fpv();
    }

    @Override
    public boolean isOre() {
        return api.isOre(true);
    }

    @Override
    public boolean isInCombat() {
        return api.isInCombat(true);
    }

    @Override
    public Control getControl() {
        return control;
    }

    @Override
    public String toString() {
        return "Character [" + getCoordinates() + "; pitch=" + getPitch() + "; azimuth=" + getAzimuth() + "]";
    }
}
