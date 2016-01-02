package org.freekode.wowbot.modules.fishing;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class FishingOptionsModel {
    private String fishKey;
    private String failTryings;
    private List<Color> firstColors;
    private List<Color> secondColors;
    private List<Color> thirdColors;

    public FishingOptionsModel() {
        fishKey = "=";
        failTryings = "5";

        firstColors = new LinkedList<>();
        firstColors.add(Color.decode("#6b240e"));
        firstColors.add(Color.decode("#4d160e"));
        firstColors.add(Color.decode("#c62f12"));
        firstColors.add(Color.decode("#94260b"));
        firstColors.add(Color.decode("#49150a"));
        firstColors.add(Color.decode("#341209"));

        secondColors = new LinkedList<>();
        secondColors.add(Color.decode("#353c59"));
        secondColors.add(Color.decode("#2f3756"));
        secondColors.add(Color.decode("#4d5363"));
        secondColors.add(Color.decode("#626574"));
        secondColors.add(Color.decode("#1e2d4a"));
        secondColors.add(Color.decode("#17263d"));

        thirdColors = new LinkedList<>();
        thirdColors.add(Color.decode("#6a5344"));
        thirdColors.add(Color.decode("#756051"));
        thirdColors.add(Color.decode("#4d4030"));
        thirdColors.add(Color.decode("#624d38"));
        thirdColors.add(Color.decode("#504d3e"));
        thirdColors.add(Color.decode("#42453a"));
    }

    public String getFishKey() {
        return fishKey;
    }

    public void setFishKey(String fishKey) {
        this.fishKey = fishKey;
    }

    public String getFailTryings() {
        return failTryings;
    }

    public void setFailTryings(String failTryings) {
        this.failTryings = failTryings;
    }

    public List<Color> getFirstColors() {
        return firstColors;
    }

    public void setFirstColors(List<Color> firstColors) {
        this.firstColors = firstColors;
    }

    public List<Color> getSecondColors() {
        return secondColors;
    }

    public void setSecondColors(List<Color> secondColors) {
        this.secondColors = secondColors;
    }

    public List<Color> getThirdColors() {
        return thirdColors;
    }

    public void setThirdColors(List<Color> thirdColors) {
        this.thirdColors = thirdColors;
    }
}
