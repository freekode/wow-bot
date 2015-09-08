package org.freekode.wowbot.beans.interfaces;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface Character {
    double getPitch();

    double getAzimuth();

    Vector3D getCoordinates();

    void pitch(double rad);

    void azimuth(double rad);

    void moveTo(Vector3D point);

    void fpv();

    boolean isOre();

    boolean isInCombat();

    Control getControl();
}
