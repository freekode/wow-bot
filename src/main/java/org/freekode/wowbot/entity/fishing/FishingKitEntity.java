package org.freekode.wowbot.entity.fishing;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FishingKitEntity {
    private String name;
    private Boolean enable;
    private List<Color> firstColors;
    private List<Color> secondColors;
    private List<Color> thirdColors;


    public FishingKitEntity() {
        firstColors = new ArrayList<>();
        secondColors = new ArrayList<>();
        thirdColors = new ArrayList<>();
    }

    public FishingKitEntity(String name) {
        this();

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Map<String, Object> getMap() {
        Map<String, Object> kitMap = new HashMap<>();
        kitMap.put("name", name);

        List<String> firstColorsHex = new ArrayList<>();
        for (Color color : firstColors) {
            firstColorsHex.add(String.format("#%06X", (0xFFFFFF & color.getRGB())));
        }
        kitMap.put("firstColors", firstColorsHex);

        List<String> secondColorsHex = new ArrayList<>();
        for (Color color : secondColors) {
            secondColorsHex.add(String.format("#%06X", (0xFFFFFF & color.getRGB())));
        }
        kitMap.put("secondColors", secondColorsHex);

        List<String> thirdColorsHex = new ArrayList<>();
        for (Color color : thirdColors) {
            thirdColorsHex.add(String.format("#%06X", (0xFFFFFF & color.getRGB())));
        }
        kitMap.put("thirdColors", thirdColorsHex);

        return kitMap;
    }

    public void parse(Map<String, Object> config) {
        name = config.get("name").toString();

        List<String> firstColorHex = (List<String>) config.get("firstColors");
        for (String colorHex : firstColorHex) {
            firstColors.add(Color.decode(colorHex));
        }

        List<String> secondColorHex = (List<String>) config.get("secondColors");
        for (String colorHex : secondColorHex) {
            secondColors.add(Color.decode(colorHex));
        }

        List<String> thirdColorHex = (List<String>) config.get("thirdColors");
        for (String colorHex : thirdColorHex) {
            thirdColors.add(Color.decode(colorHex));
        }
    }

    public static FishingKitEntity getStandard() {
        FishingKitEntity mainKit = new FishingKitEntity("Standard");
        mainKit.getFirstColors().add(Color.decode("#6b240e"));
        mainKit.getFirstColors().add(Color.decode("#4d160e"));
        mainKit.getFirstColors().add(Color.decode("#c62f12"));
        mainKit.getFirstColors().add(Color.decode("#94260b"));
        mainKit.getFirstColors().add(Color.decode("#49150a"));
        mainKit.getFirstColors().add(Color.decode("#341209"));

        mainKit.getSecondColors().add(Color.decode("#353c59"));
        mainKit.getSecondColors().add(Color.decode("#2f3756"));
        mainKit.getSecondColors().add(Color.decode("#4d5363"));
        mainKit.getSecondColors().add(Color.decode("#626574"));
        mainKit.getSecondColors().add(Color.decode("#1e2d4a"));
        mainKit.getSecondColors().add(Color.decode("#17263d"));

        mainKit.getThirdColors().add(Color.decode("#6a5344"));
        mainKit.getThirdColors().add(Color.decode("#756051"));
        mainKit.getThirdColors().add(Color.decode("#4d4030"));
        mainKit.getThirdColors().add(Color.decode("#624d38"));
        mainKit.getThirdColors().add(Color.decode("#504d3e"));
        mainKit.getThirdColors().add(Color.decode("#42453a"));

        return mainKit;
    }

    @Override
    public String toString() {
        return name;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
