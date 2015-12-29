package org.freekode.wowbot.modules.moving;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CharacterRecordModel {
    private Date date;
    private Vector3D coordinates;

    public CharacterRecordModel(Date date, Vector3D coordinates) {
        this.date = date;
        this.coordinates = coordinates;
    }

    public Date getDate() {
        return date;
    }

    public Vector3D getCoordinates() {
        return coordinates;
    }

    public List<Object> toList() {
        List<Object> list = new LinkedList<>();
        list.add(date);
        list.add(coordinates.getX());
        list.add(coordinates.getY());

        return list;
    }
}
