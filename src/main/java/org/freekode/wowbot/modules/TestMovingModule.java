package org.freekode.wowbot.modules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.beans.ai.TestMovingAI;
import org.freekode.wowbot.beans.service.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

public class TestMovingModule extends Module implements ActionListener {
    private static final Logger logger = LogManager.getLogger(TestMovingModule.class);
    private TestMovingAI ai;
    private Component ui;

    private JTextField azimuthField;
    private JTextField pitchField;


    public TestMovingModule() {
        ui = buildInterface();
        buildAI();
    }

    public Component buildInterface() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        JLabel azimuthLabel = new JLabel("Azimuth");
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(azimuthLabel, c);


        azimuthField = new JTextField();
        azimuthField.setColumns(8);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 5, 5);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(azimuthField, c);


        JButton setAzimuthButton = new JButton("Set");
        setAzimuthButton.setActionCommand("setAzimuth");
        setAzimuthButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 2;
        c.gridy = 0;
        panel.add(setAzimuthButton, c);


        JLabel pitchLabel = new JLabel("Pitch");
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0, 5);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(pitchLabel, c);


        pitchField = new JTextField(Double.toString(Controller.STANDARD_PITCH));
        pitchField.setColumns(8);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 5);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(pitchField, c);


        JButton setPitchButton = new JButton("Set");
        setPitchButton.setActionCommand("setPitch");
        setPitchButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 2;
        c.gridy = 1;
        panel.add(setPitchButton, c);


        return panel;
    }

    @Override
    public void buildAI() {
        if (ai == null || ai.isDone() || ai.isCancelled()) {
            ai = new TestMovingAI();
            ai.addPropertyChangeListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("setAzimuth".equals(e.getActionCommand())) {
            setAzimuth();
        } else if ("setPitch".equals(e.getActionCommand())) {
            setPitch();
        }
    }

    public void setAzimuth() {
        Double newAzimuth = new Double(azimuthField.getText());
        logger.info("new azimuth = " + newAzimuth);

        ai.setAzimuth(newAzimuth);

        logger.info("current azimuth = " + ai.getController().getReceiver().getAzimuth());
    }

    public void setPitch() {
        Double newPitch = new Double(pitchField.getText());
        logger.info("new pitch = " + newPitch);

        ai.setPitch(newPitch);

        logger.info("current pitch = " + ai.getController().getReceiver().getPitch());
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
        return "Moving test";
    }
}
