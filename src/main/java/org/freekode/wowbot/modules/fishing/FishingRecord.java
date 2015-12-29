package org.freekode.wowbot.modules.fishing;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FishingRecord {
    private Date date;
    private Boolean caught;

    public FishingRecord(Date date, Boolean caught) {
        this.date = date;
        this.caught = caught;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getCaught() {
        return caught;
    }

    public List<Object> toList() {
        List<Object> list = new LinkedList<>();
        list.add(date);
        list.add(caught);

        return list;
    }
}
