package org.freekode.wowbot.modules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.ai.FishingAI;
import org.freekode.wowbot.ai.Intelligence;
import org.freekode.wowbot.entity.fishing.FishingOptionsEntity;
import org.freekode.wowbot.entity.fishing.FishingRecordEntity;
import org.freekode.wowbot.tools.ConfigKeys;
import org.freekode.wowbot.tools.StaticFunc;
import org.freekode.wowbot.gui.UpdateListener;
import org.freekode.wowbot.gui.dialogs.FishingOptionsDialog;
import org.freekode.wowbot.gui.cards.FishingCardPanel;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.Map;

public class FishingModule extends Module {
    private static final Logger logger = LogManager.getLogger(FishingModule.class);
    private FishingOptionsEntity optionsEntity;
    private FishingCardPanel ui;


    public FishingModule() {
        Map<String, Object> config = StaticFunc.loadYaml(ConfigKeys.YAML_CONFIG_FILENAME, "fishing");
        optionsEntity = new FishingOptionsEntity(config);

        ui = new FishingCardPanel();
        ui.addUpdateListener(new UpdateListener() {
            @Override
            public void updated(Object object, String command) {
                if ("showOptions".equals(command)) {
                    showOptions();
                }
            }
        });
    }

    @Override
    public Intelligence buildAI() {
        return new FishingAI(optionsEntity);
    }

    @Override
    public void customProperty(PropertyChangeEvent e) {
        FishingRecordEntity record = (FishingRecordEntity) e.getNewValue();
        ui.updateRecordsTable(record);
    }

    public void showOptions() {
        FishingOptionsDialog optionsWindow = new FishingOptionsDialog();
        optionsWindow.addUpdateListener(new UpdateListener() {
            @Override
            public void updated(Object object, String command) {
                if ("saveOptions".equals(command)) {
                    saveOptions((FishingOptionsEntity) object);
                }
            }
        });

        optionsWindow.init(optionsEntity.copy());
    }

    public void saveOptions(FishingOptionsEntity options) {
        StaticFunc.saveYaml(ConfigKeys.YAML_CONFIG_FILENAME, "fishing", options.getMap());
        optionsEntity = options;
        buildAI();

        logger.info("options saved");
    }

    @Override
    public Component getUI() {
        return ui;
    }

    @Override
    public String getName() {
        return "Fishing";
    }
}
