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
import java.io.*;

public class RecordingModule extends Module implements ActionListener {
    private static final Logger logger = LogManager.getLogger(RecordingModule.class);
    private DefaultListModel<String> recordsModel;
    private JList<String> recordsList;
    private Component ui;
    private Intelligence ai;
    private JFileChooser fc;

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
        fc = new JFileChooser();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        recordsModel = new DefaultListModel<>();

        recordsList = new JList<>(recordsModel);
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 3;
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


        JButton deleteButton = new JButton("Delete");
        saveButton.setActionCommand("delete");
        saveButton.addActionListener(this);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 5, 5, 0);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 0;
        panel.add(deleteButton, c);


        JButton clearButton = new JButton("Clear");
        saveButton.setActionCommand("clear");
        saveButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_END;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 5, 5, 0);
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 0;
        panel.add(clearButton, c);


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
        } else if ("delete".equals(e.getActionCommand())) {
            delete();
        } else if ("clear".equals(e.getActionCommand())) {
            clear();
        }
    }

    public void save() {
        String csv = buildCsvFile();

        int returnVal = fc.showSaveDialog(ui);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println(csv);
                writer.close();
            } catch (FileNotFoundException e) {
                logger.error("write to file exception", e);
            }
        }

        logger.info("saved");
    }

    public String buildCsvFile() {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < recordsModel.getSize(); i++) {
            String record = recordsModel.get(i);

            out.append(record.replaceAll(" ", "")).append("\n");
        }

        return out.toString();
    }

    public void delete() {
        recordsModel.removeElement(recordsList.getSelectedValue());
    }

    public void clear() {
        recordsList.removeAll();
        recordsModel.clear();
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
