package org.freekode.wowbot.ai;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.entity.moving.CharacterUpdateEntity;

public class MovingAI extends Intelligence<CharacterUpdateEntity> {

	private static final Logger logger = LogManager.getLogger(MovingAI.class);

	private List<CharacterUpdateEntity> points;

	public MovingAI(List<CharacterUpdateEntity> points) {
		this.points = points;
	}

	@Override
	public Boolean processing() throws InterruptedException {
		for (CharacterUpdateEntity point : points) {
			point.setState("started");
			send(point);

			logger.info("move = " + point);
			getController().moveTo(point.getCoordinates());

			point.setState("reached");
			send(point);
		}

		return true;
	}
}
