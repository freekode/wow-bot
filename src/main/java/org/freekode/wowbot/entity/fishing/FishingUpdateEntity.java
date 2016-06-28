package org.freekode.wowbot.entity.fishing;

import java.awt.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FishingUpdateEntity {
    private Date date;
    private String kitName;
    private Boolean caught;
    private Color first;
    private Color second;
    private Color third;


    public FishingUpdateEntity(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getCaught() {
        return caught;
    }

    public void setCaught(Boolean caught) {
        this.caught = caught;
    }

    public Color getFirst() {
        return first;
    }

    public void setFirst(Color first) {
        this.first = first;
    }

    public Color getSecond() {
        return second;
    }

    public void setSecond(Color second) {
        this.second = second;
    }

    public Color getThird() {
        return third;
    }

    public void setThird(Color third) {
        this.third = third;
    }

    public String getKitName() {
        return kitName;
    }

    public void setKitName(String kitName) {
        this.kitName = kitName;
    }

    public List<Object> toList() {
        List<Object> list = new LinkedList<>();
        list.add(date);
        list.add(kitName);
        list.add(caught);
        list.add(first);
        list.add(second);
        list.add(third);

        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FishingUpdateEntity that = (FishingUpdateEntity) o;

        return date.equals(that.date);

    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
