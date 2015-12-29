package org.freekode.wowbot;

import org.freekode.wowbot.modules.*;
import org.freekode.wowbot.modules.fishing.FishingModule;
import org.freekode.wowbot.modules.moving.MovingModule;
import org.freekode.wowbot.modules.moving.RecordingModule;
import org.freekode.wowbot.ui.MainUI;

public class Main {
    public static void main(String[] args) throws Exception {
        MainUI mainUI = new MainUI();
        mainUI.registerHotKeys();
        mainUI.addModule(new FishingModule());
//        mainUI.addModule(new TestModule());
//        mainUI.addModule(new TestMovingModule());
//        mainUI.addModule(new RecordingModule());
//        mainUI.addModule(new MovingModule());
        mainUI.start();
    }
}
