package org.freekode.wowbot.beans.interfaces;

public interface Control {
    void centerMouse();

    void run(double distance);

    void keyRotateRight(double rad);

    void keyRotateLeft(double rad);

    void mouseYaw(double rad);

    void pitchInit();

    void mousePitch(double rad);

    void fpv();

    void fish(int keyCode);
}
