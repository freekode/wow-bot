package org.freekode.wowbot.modules;

import org.freekode.wowbot.ai.InfoAI;
import org.freekode.wowbot.ai.Intelligence;
import org.freekode.wowbot.entity.info.InfoUpdateEntity;
import org.freekode.wowbot.gui.components.InfoPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class InfoModule extends Module {
    private InfoPanel ui;


    public InfoModule() {
        ui = new InfoPanel();
    }

    @Override
    public Intelligence buildAI() {
        return new InfoAI();
    }

    @Override
    public Component getUI() {
        return ui;
    }

    @Override
    public String getName() {
        return "Information";
    }

    @Override
    public void customProperty(PropertyChangeEvent e) {
        InfoUpdateEntity update = (InfoUpdateEntity) e.getNewValue();
        ui.update(update);
    }
}
