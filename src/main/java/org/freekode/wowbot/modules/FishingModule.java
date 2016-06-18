package org.freekode.wowbot.modules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.ai.FishingAI;
import org.freekode.wowbot.ai.Intelligence;
import org.freekode.wowbot.entity.fishing.FishingKitEntity;
import org.freekode.wowbot.entity.fishing.FishingOptionsEntity;
import org.freekode.wowbot.entity.fishing.FishingRecordEntity;
import org.freekode.wowbot.tools.ConfigKeys;
import org.freekode.wowbot.tools.StaticFunc;
import org.freekode.wowbot.ui.UpdateListener;
import org.freekode.wowbot.ui.fishing.FishingOptionsUI;
import org.freekode.wowbot.ui.fishing.FishingUI;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FishingModule extends Module {
    private static final Logger logger = LogManager.getLogger(FishingModule.class);
    private FishingOptionsEntity optionsEntity;
    private FishingUI ui;


    public FishingModule() {
        Map<String, Object> config = StaticFunc.loadYaml(ConfigKeys.YAML_CONFIG_FILENAME, "fishing");
        optionsEntity = new FishingOptionsEntity(config);

        ui = new FishingUI();
        ui.addUpdateListener(new UpdateListener() {
            @Override
            public void updated(Object object, String command) {
                if ("showOptions".equals(command)) {
                    showOptions();
                }
            }
        });
        buildAI();
    }

    @Override
    public Intelligence buildAI() {
        int fishButtonValue = KeyStroke.getKeyStroke(optionsEntity.getFishKey().charAt(0), 0).getKeyCode();
        int failTryingsValue = optionsEntity.getFailTryings();

        List<FishingKitEntity> enabledKits = new LinkedList<>();
        for (FishingKitEntity kit : optionsEntity.getKits()) {
            if (kit.getEnable()) {
                enabledKits.add(kit);
            }
        }

        return new FishingAI(fishButtonValue, failTryingsValue, enabledKits);
    }

    @Override
    public void customProperty(PropertyChangeEvent e) {
        FishingRecordEntity record = (FishingRecordEntity) e.getNewValue();
        ui.updateRecordsTable(record);
    }

    public void showOptions() {
        FishingOptionsUI optionsWindow = new FishingOptionsUI();
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
