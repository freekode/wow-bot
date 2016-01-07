package org.freekode.wowbot.entity.fishing;

import java.awt.*;
import java.util.*;
import java.util.List;

public class FishingOptionsEntity {
    private String fishKey;
    private Integer failTryings;
    private List<FishingKitEntity> kits;
    private List<Color> firstColors;
    private List<Color> secondColors;
    private List<Color> thirdColors;


    public FishingOptionsEntity() {
        fishKey = "=";
        failTryings = 5;
        kits = new ArrayList<>();

        firstColors = new ArrayList<>();
        secondColors = new ArrayList<>();
        thirdColors = new ArrayList<>();
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

    public List<FishingKitEntity> getKits() {
        return kits;
    }

    public void setKits(List<FishingKitEntity> kits) {
        this.kits = kits;
    }

    public void parse(Map<String, Object> config) {
        kits = new ArrayList<>();

        Object fishKeyObject = config.get("fishingKey");
        if (fishKeyObject != null) {
            fishKey = fishKeyObject.toString();
        }


        Object failTryingsObject = config.get("failTryings");
        if (failTryingsObject != null) {
            failTryings = Integer.valueOf(failTryingsObject.toString());
        }


        kits = new ArrayList<>();
        List<Map<String, Object>> kitsMap = (List<Map<String, Object>>) config.get("kits");
        for (Map<String, Object> kitMap : kitsMap) {
            FishingKitEntity kit = new FishingKitEntity();
            kit.parse(kitMap);
            kits.add(kit);
        }


//        firstColors = new LinkedList<>();
//        firstColors.add(Color.decode("#6b240e"));
//        firstColors.add(Color.decode("#4d160e"));
//        firstColors.add(Color.decode("#c62f12"));
//        firstColors.add(Color.decode("#94260b"));
//        firstColors.add(Color.decode("#49150a"));
//        firstColors.add(Color.decode("#341209"));
//
//
//        secondColors = new LinkedList<>();
//        secondColors.add(Color.decode("#353c59"));
//        secondColors.add(Color.decode("#2f3756"));
//        secondColors.add(Color.decode("#4d5363"));
//        secondColors.add(Color.decode("#626574"));
//        secondColors.add(Color.decode("#1e2d4a"));
//        secondColors.add(Color.decode("#17263d"));
//
//
//        thirdColors = new LinkedList<>();
//        thirdColors.add(Color.decode("#6a5344"));
//        thirdColors.add(Color.decode("#756051"));
//        thirdColors.add(Color.decode("#4d4030"));
//        thirdColors.add(Color.decode("#624d38"));
//        thirdColors.add(Color.decode("#504d3e"));
//        thirdColors.add(Color.decode("#42453a"));
    }

    public Map<String, Object> getMap() {
        Map<String, Object> out = new HashMap<>();

        out.put("fishKey", fishKey);
        out.put("failTryings", failTryings);

        List<Object> kitsList = new ArrayList<>();
        for (FishingKitEntity kit : kits) {
            kitsList.add(kit.getMap());
        }
        out.put("kits", kitsList);


        return out;
    }
}
