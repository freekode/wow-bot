package org.freekode.wowbot.controller;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.tools.ConfigKeys;

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
			//            robot.setAutoDelay(50);
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

	public void centerMouse() throws InterruptedException {
		int centerX = (int) (window.getX() + window.getWidth() / 2);
		int centerY = (int) (window.getY() + window.getHeight() / 2) - 11;
		mouse(centerX, centerY);
	}

	public void mouseForGather(int stepNum, Integer steps) throws InterruptedException {
		if (steps == null) {
			steps = 5;
		}

		int centerX = (int) (window.getX() + window.getWidth() / 2);
		int centerY = (int) (window.getY() + window.getHeight() / 2) - 11;

		int high = centerY;
		//        int high = (int) (centerY - centerY * 0.1);
		int low = (int) (centerY + centerY * 0.35);
		int stepPx = (low - high) / steps;

		centerY = high + stepNum * stepPx;

		mouse(centerX, centerY);
	}

	/**
	 * gather herb, ore or fish
	 *
	 * @throws InterruptedException
	 */
	public void gather() throws InterruptedException {
		Thread.sleep(300);
		robot.keyPress(KeyEvent.VK_SHIFT);
		Thread.sleep(300);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(300);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		Thread.sleep(500);
	}

	public void mouse(int x, int y) throws InterruptedException {
		robot.mouseMove(x, y);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
	}

	public void run(double distance) throws InterruptedException {
		int runMs = (int) (distance / 0.001 * ConfigKeys.RUN_DOUBLE_O_ONE);
		robot.keyPress(KeyEvent.VK_W);
		Thread.sleep(runMs);
		robot.keyRelease(KeyEvent.VK_W);
	}

	public void keyRotateRight(double rad) throws InterruptedException {
		long runMs = ((long) (rad / 0.1)) * ConfigKeys.KEY_YAW_DOUBLE_O_ONE;

		robot.keyPress(KeyEvent.VK_D);
		Thread.sleep(runMs);
		robot.keyRelease(KeyEvent.VK_D);
	}

	public void keyRotateLeft(double rad) throws InterruptedException {
		long runMs = ((long) (rad / 0.1)) * ConfigKeys.KEY_YAW_DOUBLE_O_ONE;

		robot.keyPress(KeyEvent.VK_A);
		Thread.sleep(runMs);
		robot.keyRelease(KeyEvent.VK_A);
	}

	/**
	 * change azimuth by mouse
	 *
	 * @param rad difference of angle
	 */
	public void mouseYaw(double rad) throws InterruptedException {
		int interval = ((int) (rad / 0.005)) * ConfigKeys.MOUSE_YAW_DOUBLE_O_ONE;

		centerMouse();
		Point mousePoint = MouseInfo.getPointerInfo().getLocation();

		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
		robot.mouseMove(mousePoint.x + interval, mousePoint.y);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
	}

	public void pitchInit() throws InterruptedException {
		mousePitch(-0.01);
		mousePitch(0.01);
	}

	public void mousePitch(double rad) throws InterruptedException {
		int interval = ((int) (rad / 0.01)) * ConfigKeys.MOUSE_PITCH_DOUBLE_O_TWO;

		centerMouse();
		Point mousePoint = MouseInfo.getPointerInfo().getLocation();

		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
		robot.mouseMove(mousePoint.x, mousePoint.y + interval);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
	}

	public void fpv() throws InterruptedException {
		robot.keyPress(KeyEvent.VK_END);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
		robot.keyRelease(KeyEvent.VK_END);

		robot.keyPress(KeyEvent.VK_HOME);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
		robot.keyRelease(KeyEvent.VK_HOME);

		robot.keyPress(KeyEvent.VK_HOME);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
		robot.keyRelease(KeyEvent.VK_HOME);

		robot.keyPress(KeyEvent.VK_HOME);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
		robot.keyRelease(KeyEvent.VK_HOME);

		robot.keyPress(KeyEvent.VK_HOME);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
		robot.keyRelease(KeyEvent.VK_HOME);

		Thread.sleep(3500);
	}

	public void third() throws InterruptedException {
		robot.keyPress(KeyEvent.VK_END);
		robot.keyRelease(KeyEvent.VK_END);

		robot.keyPress(KeyEvent.VK_END);
		robot.keyRelease(KeyEvent.VK_END);

		robot.keyPress(KeyEvent.VK_END);
		robot.keyRelease(KeyEvent.VK_END);

		Thread.sleep(3000);
	}

	public void fpvByMouse() throws InterruptedException {
		for (int i = 0; i < 15; i++) {
			robot.mouseWheel(-5);
			Thread.sleep(10);
		}
	}

	public void thirdByMouse() throws InterruptedException {
		for (int i = 0; i < 15; i++) {
			robot.mouseWheel(5);
			Thread.sleep(10);
		}
	}

	public void pressKey(int keyCode) throws InterruptedException {
		robot.keyPress(keyCode);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
		robot.keyRelease(keyCode);
		Thread.sleep(ConfigKeys.AUTO_DELAY_MS);
	}

	public Robot getRobot() {
		return robot;
	}
}
