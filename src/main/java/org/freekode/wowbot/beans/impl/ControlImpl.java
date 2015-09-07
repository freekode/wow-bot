package org.freekode.wowbot.beans.impl;

import org.freekode.wowbot.beans.interfaces.Control;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ControlImpl implements Control {
    /**
     * how many ms need to run approximately 0.1 distance
     */
    public static final int RUN_POINT_ONE = 357;

    /**
     * how many ms need to change yaw using keyboard
     */
    public static final int KEY_YAW_DOUBLE_O_ONE = 3;

    /**
     * how many px need to change yaw to 0.02 rad by mouse
     */
    public static final int MOUSE_YAW_DOUBLE_O_TWO = 5;

    /**
     * how many px need to change pitch to 0.02 rad by mouse
     */
    public static final int MOUSE_PITCH_DOUBLE_O_TWO = 5;
    private Rectangle rect;
    private Robot robot;


    public ControlImpl(Rectangle rect) {
        this.rect = rect;

        try {
            robot = new Robot();
            robot.setAutoDelay(50);
            robot.setAutoWaitForIdle(true);
        } catch (AWTException ignore) {
        }
    }

    @Override
    public void centerMouse() {
        int centerX = (int) (rect.getX() + rect.getWidth() / 2);
        int centerY = (int) (rect.getY() + rect.getHeight() / 2) - 11;
        robot.mouseMove(centerX, centerY);
    }

    @Override
    public void run(double distance) {
        int runMs = (int) (distance / 0.1 * RUN_POINT_ONE);
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_W);
            robot.delay(runMs);
            robot.keyRelease(KeyEvent.VK_W);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyRotateRight(double rad) {
        long runMs = ((long) (rad / 0.01)) * KEY_YAW_DOUBLE_O_ONE;
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_D);
            Thread.sleep(runMs);
            robot.keyRelease(KeyEvent.VK_D);
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyRotateLeft(double rad) {
        long runMs = ((long) (rad / 0.01)) * KEY_YAW_DOUBLE_O_ONE;
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_A);
            Thread.sleep(runMs);
            robot.keyRelease(KeyEvent.VK_A);
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseYaw(double rad) {
        int interval = ((int) (rad / 0.02)) * MOUSE_YAW_DOUBLE_O_TWO;

        centerMouse();
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();

        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseMove(mousePoint.x + interval, mousePoint.y);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    @Override
    public void pitchInit() {
        mousePitch(-0.01);
        mousePitch(0.01);
    }

    @Override
    public void mousePitch(double rad) {
        int interval = ((int) (rad / 0.01)) * MOUSE_PITCH_DOUBLE_O_TWO;

        centerMouse();
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();

        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseMove(mousePoint.x, mousePoint.y + interval);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    @Override
    public void fpv() {
        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        robot.delay(800);
    }

    @Override
    public void fish(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
        robot.delay(300);
    }
}
