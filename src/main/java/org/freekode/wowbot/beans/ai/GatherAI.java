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


            if (point.getAction() == CharacterRecordModel.Action.MOVE) {
                logger.info("move = " + point);
                getController().moveTo(point.getCoordinates());
            } else if (point.getAction() == CharacterRecordModel.Action.GATHER) {
                getController().getDriver().fpv();
                logger.info("gather = " + point);
                Thread.sleep(1000);
                getController().gather();
                Thread.sleep(1000);
                getController().getDriver().third();
            }

            point.setState("reached");
            send(point);
        }

        return true;
    }
}
