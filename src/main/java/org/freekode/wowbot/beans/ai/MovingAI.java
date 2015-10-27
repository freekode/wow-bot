package org.freekode.wowbot.beans.ai;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.freekode.wowbot.beans.Character;

import java.util.List;

public class MovingAI extends Intelligence {
    private List<Vector3D> points;


    public MovingAI() {
    }

    public MovingAI(List<Vector3D> points) {
        this.points = points;
    }

    @Override
    public void processing() {
        for (Vector3D point : points) {
            System.out.println("move = " + point);
            getCharacter().moveTo(point);
        }
    }

    @Override
    public void terminating() {

    }
}
