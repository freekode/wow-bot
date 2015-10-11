package org.freekode.wowbot.ui;

import org.freekode.wowbot.beans.interfaces.Intelligence;

import javax.swing.*;
import java.awt.*;

public class FishingModule extends Module {
    public FishingModule() {
        super("Fishing");

    }

    @Override
    public Component getUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JButton("Fish Button 1"));
        panel.add(new JButton("Fish Button 2"));

        return panel;
    }

    @Override
    public Intelligence getAi() {
        return null;
    }
}
