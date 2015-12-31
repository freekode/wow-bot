package org.freekode.wowbot.beans.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.tools.ConfigKeys;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Driver {
    private static final Logger logger = LogManager.getLogger(Driver.class);
    private static Driver INSTANCE;
    /**
     * operated rectangle of window
     */
    private Rectangle window;

    /**
     * robot to use keyboard and mouse
     */
    private Robot robot;


    private Driver(Rectangle window) {
        this.window = window;

        try {
            robot = new Robot();
            robot.setAutoDelay(50);
        } catch (AWTException e) {
            logger.error("exception during creating of robot", e);
        }
    }

    public static Driver getInstance(Rectangle window) {
        if (INSTANCE == null) {
            INSTANCE = new Driver(window);
        }

        return INSTANCE;
    }

    public void centerMouse() {
        int centerX = (int) (window.getX() + window.getWidth() / 2);
        int centerY = (int) (window.getY() + window.getHeight() / 2) - 11;
        mouse(centerX, centerY);
    }

    public void mouseForGather(int pos) {
        int x = (int) (window.getX() + window.getWidth() / 2);
        int y = (int) (window.getY() + window.getHeight() / 2) - 11;

        switch (pos) {
            case 0:
                y -= (y / 2);
                break;
            case 1:
                y -= (y / 4);
                break;
            case 3:
                y += (y / 4);
                break;
            case 4:
                y += (y / 2);
                break;

        }
        mouse(x, y);
    }

    public void gather() throws InterruptedException {
        robot.keyPress(KeyEvent.VK_SHIFT);
        Thread.sleep(300);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(300);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        Thread.sleep(500);
    }

    public void mouse(int x, int y) {
        robot.mouseMove(x, y);
    }

    public void run(double distance) throws InterruptedException {
        int runMs = (int) (distance / 0.001 * ConfigKeys.RUN_DOUBLE_O_ONE);
        robot.keyPress(KeyEvent.VK_W);
        Thread.sleep(runMs);
        robot.keyRelease(KeyEvent.VK_W);
    }

    public void keyRotateRight(double rad) throws InterruptedException {
        long runMs = ((long) (rad / 0.01)) * ConfigKeys.KEY_YAW_DOUBLE_O_ONE;
        robot.keyPress(KeyEvent.VK_D);
        Thread.sleep(runMs);
        robot.keyRelease(KeyEvent.VK_D);
    }

    public void keyRotateLeft(double rad) throws InterruptedException {
        long runMs = ((long) (rad / 0.01)) * ConfigKeys.KEY_YAW_DOUBLE_O_ONE;
        robot.keyPress(KeyEvent.VK_A);
        Thread.sleep(runMs);
        robot.keyRelease(KeyEvent.VK_A);
    }

    /**
     * change azimuth by mouse
     *
     * @param rad difference of angle
     */
    public void mouseYaw(double rad) {
        int interval = ((int) (rad / 0.005)) * ConfigKeys.MOUSE_YAW_DOUBLE_O_ONE;

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
        int interval = ((int) (rad / 0.01)) * ConfigKeys.MOUSE_PITCH_DOUBLE_O_TWO;

        centerMouse();
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();

        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseMove(mousePoint.x, mousePoint.y + interval);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

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

    public void third() throws InterruptedException {
        robot.keyPress(KeyEvent.VK_END);
        robot.keyRelease(KeyEvent.VK_END);

        robot.keyPress(KeyEvent.VK_END);
        robot.keyRelease(KeyEvent.VK_END);

        robot.keyPress(KeyEvent.VK_END);
        robot.keyRelease(KeyEvent.VK_END);

        Thread.sleep(2000);
    }

    public void pressKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    public Robot getRobot() {
        return robot;
    }
}
