package org.freekode.wowbot.ui;

import org.freekode.wowbot.beans.interfaces.Intelligence;

import javax.swing.*;
import java.awt.*;

public class GatheringModule extends Module {
    public GatheringModule() {
        super("Gathering");

    }

    @Override
    public Component getUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JButton("Gathering Button 1"));
        panel.add(new JButton("Gathering Button 2"));

        return panel;
    }

    @Override
    public Intelligence getAi() {
        return null;
    }
}
