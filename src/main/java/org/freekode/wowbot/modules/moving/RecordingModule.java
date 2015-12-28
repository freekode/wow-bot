package org.freekode.wowbot.modules.moving;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.beans.ai.RecordingAI;
import org.freekode.wowbot.modules.Module;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public class RecordingModule extends Module implements ActionListener {
    private static final Logger logger = LogManager.getLogger(RecordingModule.class);
    private Intelligence ai;
    private Component ui;

    private JFileChooser fc;
    private JTable recordsTable;

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

        recordsTable = new JTable(new RecordTableModel());
        recordsTable.setDefaultRenderer(Date.class, new DateRenderer());
        recordsTable.setDefaultRenderer(Double.class, new DoubleRenderer());
        JScrollPane scrollPane = new JScrollPane(recordsTable);
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 4;
        panel.add(scrollPane, c);


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
        c.gridwidth = 1;
        panel.add(saveButton, c);


        JButton deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 5, 5, 0);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(deleteButton, c);


        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand("clear");
        clearButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 5, 5, 0);
        c.gridx = 2;
        c.gridy = 1;
        panel.add(clearButton, c);

        JButton reverseButton = new JButton("Rev");
        reverseButton.setActionCommand("reverse");
        reverseButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 5, 5, 5);
        c.gridx = 3;
        c.gridy = 1;
        panel.add(reverseButton, c);


        return panel;
    }

    @Override
    public void property(PropertyChangeEvent e) {
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();
        model.add((CharacterRecord) e.getNewValue());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("save".equals(e.getActionCommand())) {
            save();
        } else if ("delete".equals(e.getActionCommand())) {
            delete();
        } else if ("clear".equals(e.getActionCommand())) {
            clear();
        } else if ("reverse".equals(e.getActionCommand())) {
            reverse();
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
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();

        for (CharacterRecord record : model.getData()) {
            out.append(record.getDate().getTime()).append(";")
                    .append(record.getCoordinates().getX()).append(";")
                    .append(record.getCoordinates().getY()).append("\n");
        }

        return out.toString();
    }

    public void delete() {
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();
        int selectedIndex = recordsTable.getSelectedRow();
        if (selectedIndex != -1) {
            model.delete(selectedIndex);
        }
    }

    public void clear() {
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();
        model.clear();
    }

    public void reverse() {
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();
        model.reverse();
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
