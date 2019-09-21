package org.freekode.wowbot.ai;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.entity.moving.CharacterUpdateEntity;

public class GatherAI extends Intelligence<CharacterUpdateEntity> {

	private static final Logger logger = LogManager.getLogger(GatherAI.class);

	private List<CharacterUpdateEntity> points;

	public GatherAI(List<CharacterUpdateEntity> points) {
		this.points = points;
	}

	@Override
	public Boolean processing() throws InterruptedException {
		for (CharacterUpdateEntity point : points) {
			point.setState("started");
			send(point);

			logger.info("point = " + point);
			if (point.getAction() == CharacterUpdateEntity.Action.MOVE) {
				getController().moveTo(point.getCoordinates());
			} else if (point.getAction() == CharacterUpdateEntity.Action.GATHER) {
				getController().gatherSecond();
			}

			point.setState("reached");
			send(point);
		}

		return true;
	}
}
