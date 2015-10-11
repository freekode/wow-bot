package org.freekode.wowbot;

import org.freekode.wowbot.modules.FishingModule;
import org.freekode.wowbot.modules.TestModule;
import org.freekode.wowbot.ui.MainUI;

public class Main {
    public static void main(String[] args) {
        MainUI ex = new MainUI();
        ex.addModule("TestAI", new TestModule());
        ex.addModule("Fishing", new FishingModule());
//        ex.addModule("Gathering", new GatheringModule());
        ex.start();
    }
}
