package org.freekode.wowbot.beans.impl;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.freekode.wowbot.beans.interfaces.Character;
import org.freekode.wowbot.beans.interfaces.Intelligence;

import java.util.List;

public class MovingAI extends Intelligence {
    private List<Vector3D> points;
    private Character character;


    public MovingAI(List<Vector3D> points, Character character) {
        this.points = points;
        this.character = character;
    }

    @Override
    public void processing() {
        for (Vector3D point : points) {
            System.out.println("move = " + point);
            character.moveTo(point);
        }
    }
}
