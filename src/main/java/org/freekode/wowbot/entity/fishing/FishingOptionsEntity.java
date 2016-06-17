package org.freekode.wowbot.entity.fishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FishingOptionsEntity {
    private String fishKey = "=";
    private Integer failTryings = 5;
    private List<FishingKitEntity> kits = new ArrayList<>();


    public FishingOptionsEntity() {
        kits.add(FishingKitEntity.getStandard());
    }

    public FishingOptionsEntity(Map<String, Object> config) {
        if (config.containsKey("fishingKey")) {
            fishKey = config.get("fishingKey").toString();
        }
        if (config.containsKey("failTryings")) {
            failTryings = Integer.valueOf(config.get("failTryings").toString());
        }

        if (config.containsKey("kits")) {
            for (Map<String, Object> kitMap : (List<Map<String, Object>>) config.get("kits")) {
                if (FishingKitEntity.getStandard().getName().equals(kitMap.get("name").toString())) {
                    continue;
                }

                FishingKitEntity kit = new FishingKitEntity();
                kit.parse(kitMap);
                kits.add(kit);
            }
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

    public FishingOptionsEntity copy() {
        FishingOptionsEntity newEntity = new FishingOptionsEntity();

        newEntity.setFishKey(fishKey);
        newEntity.setFailTryings(failTryings);

        for (FishingKitEntity kit : this.kits) {
            newEntity.getKits().add(new FishingKitEntity(kit));
        }


        return newEntity;
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
