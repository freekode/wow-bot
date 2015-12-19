package org.freekode.wowbot.beans.ai;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.List;

public class MovingAI extends IntelligenceThread {
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
            getController().moveTo(point);
        }
    }

    @Override
    public void terminating() {

    }
}
