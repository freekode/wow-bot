package org.freekode.wowbot.entity.fishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FishingOptionsEntity {

	private String fishKey = "=";

	private Integer failTryings = 5;

	private List<FishingKitEntity> kits = new ArrayList<>();

	public FishingOptionsEntity(String fishKey, Integer failTryings, List<FishingKitEntity> kits) {
		this.fishKey = fishKey;
		this.failTryings = failTryings;
		this.kits = kits;
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

				FishingKitEntity kit = new FishingKitEntity(kitMap);
				kits.add(kit);
			}
		}

		kits.add(FishingKitEntity.getStandard());
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
		return new FishingOptionsEntity(fishKey, failTryings, kits);
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

			kitsList.add(kit.toMap());
		}
		out.put("kits", kitsList);

		return out;
	}
}
