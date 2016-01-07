package org.freekode.wowbot.entity.fishing;

import java.awt.*;
import java.util.List;

public class FishingKitEntity {
    private String name;
    private List<Color> firstColors;
    private List<Color> secondColors;
    private List<Color> thirdColors;


    public FishingKitEntity() {
    }

    public FishingKitEntity(String name) {
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

    @Override
    public String toString() {
        return name;
    }
}
