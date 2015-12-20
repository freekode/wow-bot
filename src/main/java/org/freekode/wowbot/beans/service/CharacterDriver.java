package org.freekode.wowbot.beans.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.interfaces.Driver;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class CharacterDriver implements Driver {
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

    private static final Logger logger = LogManager.getLogger(CharacterDriver.class);

    /**
     * operated rectangle of window
     */
    private Rectangle window;

    /**
     * robot to use keyboard and mouse
     */
    private Robot robot;


    public CharacterDriver(Rectangle window) {
        this.window = window;

        try {
            robot = new Robot();
            robot.setAutoDelay(50);
        } catch (AWTException e) {
            logger.error("exception during creating of robot", e);
        }
    }

    @Override
    public void centerMouse() {
        int centerX = (int) (window.getX() + window.getWidth() / 2);
        int centerY = (int) (window.getY() + window.getHeight() / 2) - 11;
        mouse(centerX, centerY);
    }

    @Override
    public void mouse(int x, int y) {
        robot.mouseMove(x, y);
    }

    @Override
    public void run(double distance) {
        int runMs = (int) (distance / 0.1 * RUN_POINT_ONE);
        robot.keyPress(KeyEvent.VK_W);
        robot.delay(runMs);
        robot.keyRelease(KeyEvent.VK_W);
    }

    @Override
    public void keyRotateRight(double rad) throws InterruptedException {
        long runMs = ((long) (rad / 0.01)) * KEY_YAW_DOUBLE_O_ONE;
        robot.keyPress(KeyEvent.VK_D);
        Thread.sleep(runMs);
        robot.keyRelease(KeyEvent.VK_D);
    }

    @Override
    public void keyRotateLeft(double rad) throws InterruptedException {
        long runMs = ((long) (rad / 0.01)) * KEY_YAW_DOUBLE_O_ONE;
        robot.keyPress(KeyEvent.VK_A);
        Thread.sleep(runMs);
        robot.keyRelease(KeyEvent.VK_A);
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
    public void fpv() throws InterruptedException {
        robot.keyPress(KeyEvent.VK_END);
        robot.keyRelease(KeyEvent.VK_END);

        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        robot.keyPress(KeyEvent.VK_HOME);
        robot.keyRelease(KeyEvent.VK_HOME);

        Thread.sleep(2000);
    }

    @Override
    public void pressKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    @Override
    public Robot getRobot() {
        return robot;
    }
}
