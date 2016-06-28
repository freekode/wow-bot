package org.freekode.wowbot.entity.info;

public class InfoUpdateEntity {
    private Double x;
    private Double y;
    private Double azimuth;
    private Double pitch;
    private Boolean inCombat;
    private Boolean ore;
    private Boolean herb;
    private Boolean bagUpdate;
    private Boolean hasTarget;
    private Boolean inRange;


    public InfoUpdateEntity(Double x, Double y, Double azimuth, Double pitch, Boolean inCombat, Boolean ore,
                            Boolean herb, Boolean bagUpdate, Boolean hasTarget, Boolean inRange) {
        this.x = x;
        this.y = y;
        this.azimuth = azimuth;
        this.pitch = pitch;
        this.inCombat = inCombat;
        this.ore = ore;
        this.herb = herb;
        this.bagUpdate = bagUpdate;
        this.hasTarget = hasTarget;
        this.inRange = inRange;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getAzimuth() {
        return azimuth;
    }

    public Double getPitch() {
        return pitch;
    }

    public Boolean getInCombat() {
        return inCombat;
    }

    public Boolean getOre() {
        return ore;
    }

    public Boolean getHerb() {
        return herb;
    }

    public Boolean getBagUpdate() {
        return bagUpdate;
    }

    public Boolean getHasTarget() {
        return hasTarget;
    }

    public Boolean getInRange() {
        return inRange;
    }
}
