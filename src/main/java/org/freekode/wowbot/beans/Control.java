package org.freekode.wowbot.beans;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Control {
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


    public Control(Rectangle rect) {
        this.rect = rect;

        try {
            robot = new Robot();
            robot.setAutoDelay(50);
        } catch (AWTException ignore) {
        }
    }

    public void centerMouse() {
        int centerX = (int) (rect.getX() + rect.getWidth() / 2);
        int centerY = (int) (rect.getY() + rect.getHeight() / 2) - 11;
        mouse(centerX, centerY);
    }

    public void mouse(int x, int y) {
        robot.mouseMove(x, y);
    }

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

    public void mouseYaw(double rad) {
        int interval = ((int) (rad / 0.02)) * MOUSE_YAW_DOUBLE_O_TWO;

        centerMouse();
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();

        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseMove(mousePoint.x + interval, mousePoint.y);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    public void pitchInit() {
        mousePitch(-0.01);
        mousePitch(0.01);
    }

    public void mousePitch(double rad) {
        int interval = ((int) (rad / 0.01)) * MOUSE_PITCH_DOUBLE_O_TWO;

        centerMouse();
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();

        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseMove(mousePoint.x, mousePoint.y + interval);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    public void fpv() {
        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pressKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    public Robot getRobot() {
        return robot;
    }
}
