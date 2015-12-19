package org.freekode.wowbot;

import com.sun.jna.platform.win32.WinUser;
import org.freekode.wowbot.beans.Character;
import org.freekode.wowbot.beans.AddonReceiver;
import org.freekode.wowbot.beans.interfaces.Receiver;
import org.freekode.wowbot.tools.StaticFunc;

import java.awt.*;

public class Main {
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

        WinUser.WINDOWINFO windowCoordinates = StaticFunc.upWindow(WINDOW_CLASS, WINDOW_NAME);
        if (windowCoordinates == null) {
            throw new Exception("there is no window");
        }
        Rectangle windowRectangle = windowCoordinates.rcClient.toRectangle();

        // get addon api, now it is receiver
        Receiver receiver = new AddonReceiver((int) (windowRectangle.getX() + offsetX), (int) (windowRectangle.getY() + offsetY), 10, 4, 3);
    }
}
