package org.freekode.wowbot.beans.ai;

import com.sun.jna.platform.win32.WinUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.Character;
import org.freekode.wowbot.beans.Control;
import org.freekode.wowbot.beans.AddonReceiver;
import org.freekode.wowbot.tools.StaticFunc;

import java.awt.*;

public abstract class Intelligence extends Thread {
    private static final Logger logger = LogManager.getLogger(Intelligence.class);
    private static final String WINDOW_CLASS = "GxWindowClass";
    private static final String WINDOW_NAME = "World of Warcraft";
    private static final Integer offsetX = 0;
    private static final Integer offsetY = 0;
    private Rectangle windowArea;
    private Character character;


    @Override
    public void run() {
        try {
            windowArea = findWindow();
            init(windowArea);
            processing();
        } catch (Exception e) {
            terminating();
            logger.warn(e);
        }
    }

    public Rectangle findWindow() throws Exception {
        WinUser.WINDOWINFO windowCoordinates = StaticFunc.upWindow(WINDOW_CLASS, WINDOW_NAME);

        if (windowCoordinates == null) {
            throw new Exception("there is no window");
        }

        return windowCoordinates.rcClient.toRectangle();
    }

    public void init(Rectangle windowRectangle) throws InterruptedException {
        AddonReceiver addonApi = new AddonReceiver((int) (windowRectangle.getX() + offsetX), (int) (windowRectangle.getY() + offsetY), 10, 4, 3);
        Control control = new Control(windowRectangle);

        character = Character.getInstance(addonApi, control);
    }

    public Rectangle getWindowArea() {
        return windowArea;
    }

    public Character getCharacter() {
        return character;
    }

    public abstract void processing() throws InterruptedException;

    public abstract void terminating();

    public void kill() {
        Thread.currentThread().interrupt();
    }
}
