package org.freekode.wowbot.entity.fishing;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FishingKitEntity {
    private String name;
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

    @Override
    public String toString() {
        return name;
    }
}
