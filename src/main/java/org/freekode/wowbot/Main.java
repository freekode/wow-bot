package org.freekode.wowbot;

import org.freekode.wowbot.modules.FishingModule;
import org.freekode.wowbot.modules.TestModule;
import org.freekode.wowbot.modules.TestMovingModule;
import org.freekode.wowbot.ui.MainUI;

public class Main {
    public static void main(String[] args) throws Exception {
        MainUI mainUI = new MainUI();
        mainUI.registerHotKeys();
        mainUI.addModule(new FishingModule());
        mainUI.addModule(new TestModule());
        mainUI.addModule(new TestMovingModule());
        mainUI.start();
    }
}
