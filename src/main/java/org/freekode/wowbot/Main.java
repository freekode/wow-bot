package org.freekode.wowbot;

import org.freekode.wowbot.modules.TestModule;
import org.freekode.wowbot.ui.MainUI;

public class Main {
    public static void main(String[] args) throws Exception {
        MainUI mainUI = new MainUI();
//        mainUI.registerHotKeys();
//        mainUI.addModule("Fishing", new FishingModule());
        mainUI.addModule(new TestModule());
//        mainUI.addModule("Recording", new RecordingModule());
        mainUI.start();
    }
}
