package org.freekode.wowbot.modules.fishing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.ai.FishingAI;
import org.freekode.wowbot.ai.Intelligence;
import org.freekode.wowbot.entity.fishing.FishingKitEntity;
import org.freekode.wowbot.entity.fishing.FishingOptionsEntity;
import org.freekode.wowbot.entity.fishing.FishingRecordEntity;
import org.freekode.wowbot.modules.Module;
import org.freekode.wowbot.tools.ConfigKeys;
import org.freekode.wowbot.tools.StaticFunc;
import org.freekode.wowbot.ui.fishing.FishingOptionsUI;
import org.freekode.wowbot.ui.fishing.FishingUI;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FishingModule extends Module implements PropertyChangeListener {
    private static final Logger logger = LogManager.getLogger(FishingModule.class);
    private FishingOptionsEntity optionsEntity;
    private FishingUI ui;
    private Intelligence ai;


    public FishingModule() {
        Map<String, Object> config = StaticFunc.loadYaml(ConfigKeys.YAML_CONFIG_FILENAME, "fishing");
        optionsEntity = new FishingOptionsEntity(config);

        ui = new FishingUI();
        ui.addPropertyChangeListener(this);
        buildAI();
    }

    @Override
    public void buildAI() {
        int fishButtonValue = KeyStroke.getKeyStroke(optionsEntity.getFishKey().charAt(0), 0).getKeyCode();
        int failTryingsValue = optionsEntity.getFailTryings();

        List<FishingKitEntity> enabledKits = new LinkedList<>();
        for (FishingKitEntity kit : optionsEntity.getKits()) {
            if (kit.getEnable()) {
                enabledKits.add(kit);
            }
        }

        ai = new FishingAI(fishButtonValue, failTryingsValue, enabledKits);
        ai.addPropertyChangeListener(this);
    }

    @Override
    public void property(PropertyChangeEvent e) {
        FishingRecordEntity record = (FishingRecordEntity) e.getNewValue();
        ui.updateRecordsTable(record);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        super.propertyChange(e);

        if ("showOptions".equals(e.getPropertyName())) {
            showOptions();
        }

        if ("saveOptions".equals(e.getPropertyName())) {
            saveOptions((FishingOptionsEntity) e.getNewValue());
        }
    }

    public void showOptions() {
        FishingOptionsUI optionsWindow = new FishingOptionsUI();
        optionsWindow.init(optionsEntity.copy());
        optionsWindow.addPropertyChangeListener(this);
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
    public Intelligence getAI() {
        return ai;
    }

    @Override
    public String getName() {
        return "Fishing";
    }
}
