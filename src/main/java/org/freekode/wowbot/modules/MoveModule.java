package org.freekode.wowbot.modules;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.ai.GatherAI;
import org.freekode.wowbot.ai.Intelligence;
import org.freekode.wowbot.ai.MovingAI;
import org.freekode.wowbot.ai.RecordingAI;
import org.freekode.wowbot.entity.moving.CharacterUpdateEntity;
import org.freekode.wowbot.gui.UpdateListener;
import org.freekode.wowbot.gui.cards.MoveCardPanel;

public class MoveModule extends Module {

	private static final Logger logger = LogManager.getLogger(MoveModule.class);

	private ModuleType currentType = ModuleType.RECORD;

	private MoveCardPanel ui;

	public MoveModule() {
		ui = new MoveCardPanel();
		ui.addUpdateListener((object, command) -> {
			if ("savePoints".equals(command)) {
				Map<String, Object> map = (Map<String, Object>) object;
				File file = (File) map.get("file");
				List<CharacterUpdateEntity> points = (List<CharacterUpdateEntity>) map.get("data");

				savePoints(file, points);
			}
		});
	}

	@Override
	public Intelligence buildAI() {
		if (currentType == ModuleType.RECORD) {
			return buildRecordAI();
		} else if (currentType == ModuleType.MOVE) {
			return buildMoveAI();
		} else {
			return buildGatherAI();
		}
	}

	public Intelligence buildRecordAI() {
		return new RecordingAI();
	}

	public Intelligence buildMoveAI() {
		return new MovingAI(ui.getPointList());
	}

	public Intelligence buildGatherAI() {
		return new GatherAI(ui.getPointList());
	}

	public void savePoints(File file, List<CharacterUpdateEntity> list) {
		//        StaticFunc.saveYAML(file, null, list);
	}

	@Override
	public void customProperty(PropertyChangeEvent e) {
		CharacterUpdateEntity record = (CharacterUpdateEntity) e.getNewValue();
		ui.update(record);
	}

	@Override
	public Component getUI() {
		return ui;
	}

	@Override
	public String getName() {
		return "Move";
	}

	public enum ModuleType {
		RECORD,
		MOVE,
		GATHER
	}
}
