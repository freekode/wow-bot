package org.freekode.wowbot.modules.fishing;

import java.awt.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FishingRecordModel {
    private Date date;
    private Boolean caught;
    private Color first;
    private Color second;
    private Color third;

    public FishingRecordModel(Date date, Boolean caught, Color first, Color second, Color third) {
        this.date = date;
        this.caught = caught;
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getCaught() {
        return caught;
    }

    public Color getFirst() {
        return first;
    }

    public Color getSecond() {
        return second;
    }

    public Color getThird() {
        return third;
    }

    public List<Object> toList() {
        List<Object> list = new LinkedList<>();
        list.add(date);
        list.add(caught);
        list.add(first);
        list.add(second);
        list.add(third);

        return list;
    }
}
