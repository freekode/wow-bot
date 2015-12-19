package org.freekode.wowbot.beans.ai;

import com.sun.jna.platform.win32.WinUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.AddonReceiver;
import org.freekode.wowbot.beans.CharacterDriver;
import org.freekode.wowbot.beans.MainController;
import org.freekode.wowbot.beans.interfaces.Driver;
import org.freekode.wowbot.beans.interfaces.Receiver;
import org.freekode.wowbot.tools.StaticFunc;

import java.awt.*;

public abstract class IntelligenceThread extends Thread {
    private static final Logger logger = LogManager.getLogger(IntelligenceThread.class);
    private static final String WINDOW_CLASS = "GxWindowClass";
    private static final String WINDOW_NAME = "World of Warcraft";
    private static final Integer offsetX = 0;
    private static final Integer offsetY = 0;
    private Rectangle windowArea;
    private MainController controller;


    @Override
    public void run() {
        try {
            windowArea = findWindow();
            init();

            processing();
        } catch (Exception e) {
            terminating();
            logger.info("Intelligence exception", e);
        }
    }

    public Rectangle findWindow() throws Exception {
        WinUser.WINDOWINFO windowCoordinates = StaticFunc.upWindow(WINDOW_CLASS, WINDOW_NAME);

        if (windowCoordinates == null) {
            throw new Exception("there is no window");
        }

        return windowCoordinates.rcClient.toRectangle();
    }

    public void init() throws InterruptedException {
        Receiver receiver = new AddonReceiver((int) (windowArea.getX() + offsetX), (int) (windowArea.getY() + offsetY), 10, 4, 3);
        Driver driver = new CharacterDriver(windowArea);

        MainController controller = new MainController(driver, receiver);
    }

    public Rectangle getWindowArea() {
        return windowArea;
    }

    public MainController getController() {
        return controller;
    }

    public void kill() {
        Thread.currentThread().interrupt();
    }

    public abstract void processing() throws InterruptedException;

    public abstract void terminating();
}
