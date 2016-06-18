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
    public Intelligence buildAI() {
        return new TestAI();
    }

    @Override
    public Component getUI() {
        return ui;
    }

    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public void customProperty(PropertyChangeEvent e) {
        testLabel.setText(e.getNewValue().toString());
    }
}
