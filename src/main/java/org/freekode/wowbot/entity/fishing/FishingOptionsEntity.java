package org.freekode.wowbot.entity.fishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FishingOptionsEntity {
    private String fishKey;
    private Integer failTryings;
    private List<FishingKitEntity> kits;


    public FishingOptionsEntity() {
        fishKey = "=";
        failTryings = 5;
        kits = new ArrayList<>();

        kits.add(FishingKitEntity.getStandard());
    }

    public FishingOptionsEntity(FishingOptionsEntity copy) {
        fishKey = copy.getFishKey();
        failTryings = copy.failTryings;

        kits = new ArrayList<>();
        for (FishingKitEntity kit : copy.getKits()) {
            kits.add(new FishingKitEntity(kit));
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

    public List<FishingKitEntity> getKits() {
        return kits;
    }

    public void setKits(List<FishingKitEntity> kits) {
        this.kits = kits;
    }

    public void parse(Map<String, Object> config) {
        Object fishKeyObject = config.get("fishingKey");
        if (fishKeyObject != null) {
            fishKey = fishKeyObject.toString();
        }


        Object failTryingsObject = config.get("failTryings");
        if (failTryingsObject != null) {
            failTryings = Integer.valueOf(failTryingsObject.toString());
        }


        List<Map<String, Object>> kitsMap = (List<Map<String, Object>>) config.get("kits");
        if (kitsMap != null) {
            for (Map<String, Object> kitMap : kitsMap) {
                if (FishingKitEntity.getStandard().getName().equals(kitMap.get("name").toString())) {
                    continue;
                }

                FishingKitEntity kit = new FishingKitEntity();
                kit.parse(kitMap);
                kits.add(kit);
            }
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
            if (FishingKitEntity.getStandard().getName().equals(kit.getName())) {
                continue;
            }

            kitsList.add(kit.getMap());
        }
        out.put("kits", kitsList);


        return out;
    }
}
