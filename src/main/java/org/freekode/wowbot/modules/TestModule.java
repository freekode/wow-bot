package org.freekode.wowbot.modules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.ai.Intelligence;
import org.freekode.wowbot.ai.TestAI;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class TestModule extends Module {
    private static final Logger logger = LogManager.getLogger(TestModule.class);
    private JLabel testLabel;
    private Intelligence<String> ai;
    private Component ui;


    public TestModule() {
        ui = buildInterface();
        buildAI();
    }

    public Component buildInterface() {
        JPanel panel = new JPanel(new GridBagLayout());

        testLabel = new JLabel("Test ready");
        panel.add(testLabel);

        return panel;
    }

    @Override
    public void buildAI() {
        if (ai == null || ai.isDone() || ai.isCancelled()) {
            ai = new TestAI();
            ai.addPropertyChangeListener(this);
        }
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
        return "Test";
    }

    @Override
    public void property(PropertyChangeEvent e) {
        testLabel.setText(e.getNewValue().toString());
    }
}
