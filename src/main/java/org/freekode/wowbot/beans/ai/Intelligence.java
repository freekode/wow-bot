package org.freekode.wowbot.beans.ai;

import com.sun.jna.platform.win32.WinUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.service.Receiver;
import org.freekode.wowbot.beans.service.Driver;
import org.freekode.wowbot.beans.service.Controller;
import org.freekode.wowbot.tools.StaticFunc;

import javax.swing.*;
import java.awt.*;

public abstract class Intelligence<V> extends SwingWorker<Boolean, Void> {
    private static final Logger logger = LogManager.getLogger(Intelligence.class);
    private static final String WINDOW_CLASS = "GxWindowClass";
    private static final String WINDOW_NAME = "World of Warcraft";
    private static final Integer offsetX = 0;
    private static final Integer offsetY = 0;
    private Rectangle windowArea;
    private Controller controller;


    @Override
    public Boolean doInBackground() {
        logger.info("start");
        try {
            windowArea = findWindow();
            init();

            return processing();
        } catch (Exception e) {
            logger.info("Intelligence exception", e);
            return null;
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
        Receiver receiver = new Receiver((int) (windowArea.getX() + offsetX), (int) (windowArea.getY() + offsetY), 10, 4, 4);
        Driver driver = new Driver(windowArea);

        controller = new Controller(driver, receiver);
    }

    public void send(V object) {
        firePropertyChange("custom", null, object);
    }

    public void kill() {
        logger.info("kill");
        terminating();
        cancel(true);
    }

    public abstract Boolean processing() throws InterruptedException;

    public void terminating() {
    }

    public Rectangle getWindowArea() {
        return windowArea;
    }

    public Controller getController() {
        return controller;
    }
}
