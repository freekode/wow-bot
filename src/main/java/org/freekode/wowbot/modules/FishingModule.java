package org.freekode.wowbot.modules;

import org.freekode.wowbot.beans.ai.FishingAI;
import org.freekode.wowbot.beans.ai.Intelligence;

import javax.swing.*;
import java.awt.*;

public class FishingModule extends Module {
    @Override
    public Component getUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JButton("Fish Button 1"));
        panel.add(new JButton("Fish Button 2"));

        return panel;
    }

    @Override
    public Intelligence getAi() {
        return new FishingAI();
    }
}
