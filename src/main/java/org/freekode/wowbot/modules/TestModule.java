package org.freekode.wowbot.modules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.beans.ai.TestAI;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TestModule extends Module implements PropertyChangeListener {
    private static final Logger logger = LogManager.getLogger(TestModule.class);
    private Component ui;
    private Intelligence<Void> ai;


    public TestModule() {
        ui = buildInterface();
        ai = new TestAI();
        ai.addPropertyChangeListener(this);
    }

    public Component buildInterface() {
        JPanel panel = new JPanel(new GridBagLayout());

        JLabel testLabel = new JLabel("Test ready");
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
    public void propertyChange(PropertyChangeEvent e) {
        switch (e.getPropertyName()) {
            case "progress":
                logger.info("progress");
                break;
            case "state":
                switch ((SwingWorker.StateValue) e.getNewValue()) {
                    case DONE:
                        logger.info("done");
                        break;
                    case STARTED:
                        logger.info("done");
                        break;
                    case PENDING:
                        logger.info("done");
                        break;
                }
                break;
        }
    }
}
