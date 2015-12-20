package org.freekode.wowbot.modules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.beans.ai.RecordingAI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

public class RecordingModule extends Module implements ActionListener {
    private static final Logger logger = LogManager.getLogger(RecordingModule.class);
    private DefaultListModel<String> recordsModel;
    private Component ui;
    private Intelligence ai;

    public RecordingModule() {
        ui = buildUI();
        buildAI();
    }

    @Override
    public void buildAI() {
        ai = new RecordingAI();
        ai.addPropertyChangeListener(this);
    }

    public Component buildUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        recordsModel = new DefaultListModel<>();

        JList<String> recordsList = new JList<>(recordsModel);
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(recordsList, c);


        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 5, 5, 0);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 0;
        panel.add(saveButton, c);


        return panel;
    }

    @Override
    public void property(PropertyChangeEvent e) {
        recordsModel.addElement(e.getNewValue().toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("save".equals(e.getActionCommand())) {
            save();
        }
    }

    public void save() {
        logger.info("save");
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
        return "Recording";
    }
}
