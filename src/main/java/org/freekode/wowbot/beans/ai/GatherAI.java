package org.freekode.wowbot.beans.ai;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.entity.moving.CharacterRecordEntity;

import java.util.List;

public class GatherAI extends Intelligence<CharacterRecordEntity> {
    private static final Logger logger = LogManager.getLogger(GatherAI.class);
    private List<CharacterRecordEntity> points;


    public GatherAI(List<CharacterRecordEntity> points) {
        this.points = points;
    }

    @Override
    public Boolean processing() throws InterruptedException {
        for (CharacterRecordEntity point : points) {
            point.setState("started");
            send(point);


            logger.info("point = " + point);
            if (point.getAction() == CharacterRecordEntity.Action.MOVE) {
                getController().moveTo(point.getCoordinates());
            } else if (point.getAction() == CharacterRecordEntity.Action.GATHER) {
                getController().gatherSecond();
            }

            point.setState("reached");
            send(point);
        }

        return true;
    }
}
