package org.freekode.wowbot;

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

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String WINDOW_CLASS = "GxWindowClass";
    private static final String WINDOW_NAME = "World of Warcraft";
    private static final Integer offsetX = 0;
    private static final Integer offsetY = 0;
    private Rectangle windowArea;
    private Character character;


    public static void main(String[] args) throws Exception {
//        MainUI mainUI = new MainUI();
//        mainUI.registerHotKeys();
//        mainUI.addModule("Fishing", new FishingModule());
//        mainUI.addModule("Recording", new RecordingModule());
//        ex.addModule("TestAI", new TestModule());
//        ex.addModule("Gathering", new GatheringModule());
//        mainUI.start();


        // testing new arch
        WinUser.WINDOWINFO windowCoordinates = StaticFunc.upWindow(WINDOW_CLASS, WINDOW_NAME);
        if (windowCoordinates == null) {
            throw new Exception("there is no window");
        }
        Rectangle windowRectangle = windowCoordinates.rcClient.toRectangle();

        // get addon api, now it is receiver
        Receiver receiver = new AddonReceiver((int) (windowRectangle.getX() + offsetX), (int) (windowRectangle.getY() + offsetY), 10, 4, 3);
        logger.info(receiver.toString());

        // get driver to control the character
        Driver driver = new CharacterDriver(windowRectangle);

        // use it
        MainController controller = new MainController(driver, receiver);
//        controller.pitch(MainController.STANDARD_PITCH);
    }
}
