package org.freekode.wowbot.beans.ai;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.modules.moving.CharacterRecordModel;

import java.util.List;

public class GatherAI extends Intelligence<CharacterRecordModel> {
    private static final Logger logger = LogManager.getLogger(GatherAI.class);
    private List<CharacterRecordModel> points;


    public GatherAI(List<CharacterRecordModel> points) {
        this.points = points;
    }

    @Override
    public Boolean processing() throws InterruptedException {
        for (CharacterRecordModel point : points) {
            point.setState("started");
            send(point);


            logger.info("point = " + point);
            if (point.getAction() == CharacterRecordModel.Action.MOVE) {
                getController().moveTo(point.getCoordinates());
            } else if (point.getAction() == CharacterRecordModel.Action.GATHER) {
                getController().gather();
            }

            point.setState("reached");
            send(point);
        }

        return true;
    }
}
