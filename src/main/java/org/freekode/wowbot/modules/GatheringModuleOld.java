package org.freekode.wowbot.modules;

import org.freekode.wowbot.beans.ai.IntelligenceThread;

import javax.swing.*;
import java.awt.*;

public class GatheringModuleOld extends ModuleOld {
    @Override
    public Component getUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JButton("Gathering Button 1"));
        panel.add(new JButton("Gathering Button 2"));

        return panel;
    }

    @Override
    public IntelligenceThread getAi() {
        return null;
    }
}
