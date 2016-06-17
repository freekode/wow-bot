package org.freekode.wowbot.ai;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.entity.moving.CharacterRecordEntity;

import java.util.List;

public class MovingAI extends Intelligence<CharacterRecordEntity> {
    private static final Logger logger = LogManager.getLogger(MovingAI.class);
    private List<Vector3D> points;


    public MovingAI(List<Vector3D> points) {
        this.points = points;
    }

    @Override
    public Boolean processing() throws InterruptedException {
        for (Vector3D point : points) {
            CharacterRecordEntity record = new CharacterRecordEntity(point);
            record.setState("started");
            send(record);

            logger.info("move = " + point);
            getController().moveTo(point);

            record.setState("reached");
            send(record);
        }

        return true;
    }
}
