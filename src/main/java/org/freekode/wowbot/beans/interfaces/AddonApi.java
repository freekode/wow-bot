package org.freekode.wowbot.beans.interfaces;

public interface AddonApi {
    void updateColors(boolean wait);

    Double getX(boolean wait);

    Double getY(boolean wait);

    Double getPitch(boolean wait);

    Double getAzimuth(boolean wait);

    Boolean isOre(boolean wait);

    Boolean isHerb(boolean wait);

    Boolean isInCombat(boolean wait);
}
