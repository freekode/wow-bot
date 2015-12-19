package org.freekode.wowbot.beans.interfaces;

public interface Controller {
    void centerMouse();

    void mouse(int x, int y);

    void run(double distance);

    void keyRotateRight(double rad) throws InterruptedException;

    void keyRotateLeft(double rad) throws InterruptedException;

    void mouseYaw(double rad);

    void pitchInit();

    void mousePitch(double rad);

    void fpv() throws InterruptedException;

    void pressKey(int keyCode);
}
