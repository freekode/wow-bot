package org.freekode.wowbot;

import com.sun.jna.platform.win32.WinUser;
import org.freekode.wowbot.beans.impl.CharacterImpl;
import org.freekode.wowbot.beans.impl.ControlImpl;
import org.freekode.wowbot.beans.impl.MovingAI;
import org.freekode.wowbot.beans.impl.WoWAddonApi;
import org.freekode.wowbot.beans.interfaces.AddonApi;
import org.freekode.wowbot.beans.interfaces.Character;
import org.freekode.wowbot.beans.interfaces.Control;
import org.freekode.wowbot.beans.interfaces.Intelligence;
import org.freekode.wowbot.tools.StaticFunctions;

import java.util.ArrayList;

public class Main {
    public static final String WINDOW_CLASS = "GxWindowClass";
    public static final String WINDOW_NAME = "World of Warcraft";
    public static final Integer offsetX = 0;
    public static final Integer offsetY = 0;


    public static void main(String[] args) throws InterruptedException {
        WinUser.WINDOWINFO windowCoordinates = StaticFunctions.upWindow(WINDOW_CLASS, WINDOW_NAME);

        if (windowCoordinates == null) {
            System.out.println("there is no window");
            return;
        }


        AddonApi addonApi = new WoWAddonApi(windowCoordinates.rcClient.left + offsetX, windowCoordinates.rcClient.top + offsetY, 10, 4, 3);
        Control control = new ControlImpl(windowCoordinates.rcClient.toRectangle());

        Character character = new CharacterImpl(addonApi, control);

        Intelligence move = new MovingAI(new ArrayList<>(), character);
        move.run();
    }
}
