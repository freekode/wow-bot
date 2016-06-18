package org.freekode.wowbot.modules;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.ai.Intelligence;
import org.freekode.wowbot.ai.TestMovingAI;
import org.freekode.wowbot.tools.ConfigKeys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestMovingModule extends Module implements ActionListener {
    private static final Logger logger = LogManager.getLogger(TestMovingModule.class);
    private Component ui;
    private TestMovingAI ai;

    private JTextField azimuthField;
    private JTextField pitchField;
    private JTextField runField;


    public TestMovingModule() {
        ui = buildInterface();
        buildAI();
    }

    @Override
    public Intelligence buildAI() {
        ai = new TestMovingAI();
        return ai;
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
        c.insets = new Insets(0, 0, 5, 5);
        c.gridx = 2;
        c.gridy = 0;
        panel.add(setAzimuthButton, c);

        JButton setAzimuthByKeyButton = new JButton("Set by key");
        setAzimuthByKeyButton.setActionCommand("setAzimuthByKey");
        setAzimuthByKeyButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 3;
        c.gridy = 0;
        panel.add(setAzimuthByKeyButton, c);


        JLabel pitchLabel = new JLabel("Pitch");
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 5);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(pitchLabel, c);

        pitchField = new JTextField(Double.toString(ConfigKeys.STANDARD_PITCH));
        pitchField.setColumns(8);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 5, 5);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(pitchField, c);

        JButton setPitchButton = new JButton("Set");
        setPitchButton.setActionCommand("setPitch");
        setPitchButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 2;
        c.gridy = 1;
        panel.add(setPitchButton, c);


        JLabel distanceLabel = new JLabel("Run");
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 5);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(distanceLabel, c);

        runField = new JTextField("0");
        runField.setColumns(8);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 5, 5);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(runField, c);

        JButton runButton = new JButton("Run");
        runButton.setActionCommand("run");
        runButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 2;
        c.gridy = 2;
        panel.add(runButton, c);

        JButton gatherHerbButton = new JButton("Gather Herb");
        gatherHerbButton.setActionCommand("gather");
        gatherHerbButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        panel.add(gatherHerbButton, c);


        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("setAzimuth".equals(e.getActionCommand())) {
            setAzimuth();
        } else if ("setAzimuthByKey".equals(e.getActionCommand())) {
            setAzimuthByKey();
        } else if ("setPitch".equals(e.getActionCommand())) {
            setPitch();
        } else if ("run".equals(e.getActionCommand())) {
            run();
        } else if ("gather".equals(e.getActionCommand())) {
            gatherHerb();
        } else {
            logger.info("unknown action command = " + e.getActionCommand());
        }
    }

    public void setAzimuth() {
        Double newAzimuth = new Double(azimuthField.getText());
        logger.info("new azimuth = " + newAzimuth);

        try {
            ai.setAzimuth(newAzimuth);
            logger.info("current azimuth = " + ai.getController().getReceiver().getAzimuth());
        } catch (InterruptedException e) {
            logger.info("azimuth test exception", e);
        }
    }

    public void setAzimuthByKey() {
        Double newAzimuth = new Double(azimuthField.getText());
        logger.info("new azimuth = " + newAzimuth);

        try {
            ai.setAzimuthByKey(newAzimuth);
            logger.info("current azimuth = " + ai.getController().getReceiver().getAzimuth());
        } catch (InterruptedException e) {
            logger.info("azimuth test exception", e);
        }
    }

    public void setPitch() {
        Double newPitch = new Double(pitchField.getText());
        logger.info("new pitch = " + newPitch);

        try {
            ai.setPitch(newPitch);
            logger.info("current pitch = " + ai.getController().getReceiver().getPitch());
        } catch (InterruptedException e) {
            logger.info("pitch test exception", e);
        }
    }

    public void run() {
        Double distance = new Double(runField.getText());
        Vector3D currentLocation = ai.getController().getCoordinates();
        logger.info("run = " + distance);

        try {
            ai.run(distance);

            Vector3D newLocation = ai.getController().getCoordinates();
            logger.info("real distance = " + Vector3D.distance(currentLocation, newLocation));
        } catch (InterruptedException e) {
            logger.info("run test exception", e);
        }
    }

    public void gatherHerb() {
        logger.info("gather herb");

        try {
            ai.gatherHerb();
        } catch (InterruptedException e) {
            logger.info("gather herb", e);
        }
    }

    @Override
    public Component getUI() {
        return ui;
    }

    @Override
    public String getName() {
        return "Moving test";
    }
}
