package org.freekode.wowbot.modules;

import org.freekode.wowbot.beans.ai.IntelligenceOld;

import javax.swing.*;
import java.awt.*;

public class GatheringModule extends Module {
    @Override
    public Component getUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JButton("Gathering Button 1"));
        panel.add(new JButton("Gathering Button 2"));

        return panel;
    }

    @Override
    public IntelligenceOld getAi() {
        return null;
    }
}
