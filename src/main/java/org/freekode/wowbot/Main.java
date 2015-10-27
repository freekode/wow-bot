package org.freekode.wowbot;

import org.freekode.wowbot.modules.FishingModule;
import org.freekode.wowbot.modules.RecordingModule;
import org.freekode.wowbot.ui.MainUI;

public class Main {
    public static void main(String[] args) {
        MainUI mainUI = new MainUI();
        mainUI.registerHotKeys();
        mainUI.addModule("Fishing", new FishingModule());
        mainUI.addModule("Recording", new RecordingModule());
//        ex.addModule("TestAI", new TestModule());
//        ex.addModule("Gathering", new GatheringModule());
        mainUI.start();
    }
}
