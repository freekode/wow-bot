package org.freekode.wowbot.modules;

import org.freekode.wowbot.beans.ai.IntelligenceOld;
import org.freekode.wowbot.beans.ai.TestAI;

import javax.swing.*;
import java.awt.*;

public class TestModule extends Module {
    @Override
    public Component getUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JLabel("Test"));

        return panel;
    }

    @Override
    public IntelligenceOld getAi() {
        return new TestAI();
    }
}
