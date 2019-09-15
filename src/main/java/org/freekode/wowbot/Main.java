package org.freekode.wowbot;

import org.freekode.wowbot.modules.FishingModule;
import org.freekode.wowbot.gui.MainUI;
import org.freekode.wowbot.modules.MoveModule;

public class Main {
    private static boolean hotKey;

    public static void main(String[] args) throws Exception {
        parseArgs(args);

        MainUI mainUI = new MainUI(hotKey);
//        mainUI.addModule(new FishingModule());
//        mainUI.addModule(new TestModule());
//        mainUI.addModule(new TestMovingModule());
        mainUI.addModule(new MoveModule());
        mainUI.start();
    }

    public static void parseArgs(String[] args) {
        for (String arg : args) {
            String replaced = arg.replace("-", "");
            if (replaced.equals("k")) {
                hotKey = true;
            }
        }
    }
}
