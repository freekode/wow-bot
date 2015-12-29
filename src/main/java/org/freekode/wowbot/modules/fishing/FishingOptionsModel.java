package org.freekode.wowbot.modules.fishing;

public class FishingOptionsModel {
    private String fishKey = "=";
    private String failTryings = "5";


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
}
