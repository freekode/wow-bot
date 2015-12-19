package org.freekode.wowbot.modules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.beans.ai.TestAI;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TestModule extends Module {
    private static final Logger logger = LogManager.getLogger(TestModule.class);
    private JLabel testLabel;
    private Component ui;
    private Intelligence<String> ai;


    public TestModule() {
        ui = buildInterface();

        ai = new TestAI();
        ai.addPropertyChangeListener(this);
    }

    public Component buildInterface() {
        JPanel panel = new JPanel(new GridBagLayout());

        testLabel = new JLabel("Test ready");
        panel.add(testLabel);

        return panel;
    }

    @Override
    public Component getUI() {
        return ui;
    }

    @Override
    public Intelligence getAi() {
        return ai;
    }

    @Override
    public void property(PropertyChangeEvent e) {
        testLabel.setText(e.getNewValue().toString());
    }
}
