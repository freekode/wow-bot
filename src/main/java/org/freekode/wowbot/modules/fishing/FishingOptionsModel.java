package org.freekode.wowbot.modules.fishing;

import java.awt.*;
import java.util.*;
import java.util.List;

public class FishingOptionsModel {
    private String fishKey;
    private Integer failTryings;
    private List<Color> firstColors;
    private List<Color> secondColors;
    private List<Color> thirdColors;


    public FishingOptionsModel(Map<String, Object> config) {
        Object fishKeyObject = config.get("fishingKey");
        if (fishKeyObject != null) {
            fishKey = fishKeyObject.toString();
        } else {
            fishKey = "=";
        }


        Object failTryingsObject = config.get("failTryings");
        if (failTryingsObject != null) {
            failTryings = Integer.valueOf(failTryingsObject.toString());
        } else {
            failTryings = 5;
        }


        firstColors = new LinkedList<>();
        Object firstColorsObject = config.get("firstColors");
        if (firstColorsObject != null) {
            for (String hexColor : (List<String>) firstColorsObject) {
                firstColors.add(Color.decode(hexColor));
            }
        } else {
            firstColors.add(Color.decode("#6b240e"));
            firstColors.add(Color.decode("#4d160e"));
            firstColors.add(Color.decode("#c62f12"));
            firstColors.add(Color.decode("#94260b"));
            firstColors.add(Color.decode("#49150a"));
            firstColors.add(Color.decode("#341209"));
        }


        secondColors = new LinkedList<>();
        Object secondColorObject = config.get("secondColors");
        if (secondColorObject != null) {
            for (String hexColor : (List<String>) secondColorObject) {
                secondColors.add(Color.decode(hexColor));
            }
        } else {
            secondColors.add(Color.decode("#353c59"));
            secondColors.add(Color.decode("#2f3756"));
            secondColors.add(Color.decode("#4d5363"));
            secondColors.add(Color.decode("#626574"));
            secondColors.add(Color.decode("#1e2d4a"));
            secondColors.add(Color.decode("#17263d"));
        }


        thirdColors = new LinkedList<>();
        Object thirdColorObject = config.get("thirdColors");
        if (thirdColorObject != null) {
            for (String hexColor : (List<String>) thirdColorObject) {
                thirdColors.add(Color.decode(hexColor));
            }
        } else {
            thirdColors.add(Color.decode("#6a5344"));
            thirdColors.add(Color.decode("#756051"));
            thirdColors.add(Color.decode("#4d4030"));
            thirdColors.add(Color.decode("#624d38"));
            thirdColors.add(Color.decode("#504d3e"));
            thirdColors.add(Color.decode("#42453a"));
        }
    }

    public String getFishKey() {
        return fishKey;
    }

    public void setFishKey(String fishKey) {
        this.fishKey = fishKey;
    }

    public Integer getFailTryings() {
        return failTryings;
    }

    public void setFailTryings(Integer failTryings) {
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

    public Map<String, Object> getMap() {
        Map<String, Object> out = new HashMap<>();

        out.put("fishKey", fishKey);
        out.put("failTryings", failTryings);

        List<String> firstColorHex = new ArrayList<>();
        for (Color color : firstColors) {
            firstColorHex.add(String.format("#%06X", (0xFFFFFF & color.getRGB())));
        }
        out.put("firstColors", firstColorHex);

        List<String> secondColorHex = new ArrayList<>();
        for (Color color : secondColors) {
            secondColorHex.add(String.format("#%06X", (0xFFFFFF & color.getRGB())));
        }
        out.put("secondColors", secondColorHex);

        List<String> thirdColorHex = new ArrayList<>();
        for (Color color : thirdColors) {
            thirdColorHex.add(String.format("#%06X", (0xFFFFFF & color.getRGB())));
        }
        out.put("thirdColors", thirdColorHex);


        return out;
    }
}
