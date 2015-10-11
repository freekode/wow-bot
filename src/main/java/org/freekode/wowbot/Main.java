package org.freekode.wowbot;

import org.freekode.wowbot.ui.FishingModule;
import org.freekode.wowbot.ui.GatheringModule;
import org.freekode.wowbot.ui.MainUI;

public class Main {
    public static void main(String[] args) {
        MainUI ex = new MainUI();
        ex.addModule(new FishingModule());
        ex.addModule(new GatheringModule());
        ex.start();
    }
}
