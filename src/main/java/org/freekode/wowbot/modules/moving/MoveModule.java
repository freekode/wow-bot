package org.freekode.wowbot.modules.moving;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.beans.ai.MovingAI;
import org.freekode.wowbot.beans.ai.RecordingAI;
import org.freekode.wowbot.modules.Module;
import org.freekode.wowbot.tools.DateRenderer;
import org.freekode.wowbot.tools.DoubleRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoveModule extends Module implements ActionListener {
    private static final Logger logger = LogManager.getLogger(MoveModule.class);
    private ModuleType currentType = ModuleType.RECORD;
    private Intelligence ai;
    private Component ui;
    private JFileChooser fc;
    private JTable recordsTable;


    public MoveModule() {
        ui = buildUI();
        buildAI();
    }

    @Override
    public void buildAI() {
        if (currentType == ModuleType.RECORD) {
            buildRecordUI();
        } else if (currentType == ModuleType.MOVE) {
            buildMoveUI();
        }

    }

    public void buildRecordUI() {
        ai = new RecordingAI();
        ai.addPropertyChangeListener(this);
    }

    public void buildMoveUI() {
        List<Vector3D> points = new ArrayList<>();
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();
        for (CharacterRecordModel record : model.getData()) {
            points.add(record.getCoordinates());
        }

        ai = new MovingAI(points);
        ai.addPropertyChangeListener(this);
    }

    public Component buildUI() {
        fc = new JFileChooser();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        JRadioButton recordRadio = new JRadioButton("Record");
        recordRadio.addActionListener(this);
        recordRadio.setActionCommand("recordAI");
        recordRadio.setSelected(true);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(recordRadio, c);

        JRadioButton moveRadio = new JRadioButton("Move");
        moveRadio.addActionListener(this);
        moveRadio.setActionCommand("moveAI");
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(moveRadio, c);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(recordRadio);
        buttonGroup.add(moveRadio);


        recordsTable = new JTable(new RecordTableModel());
        recordsTable.setDefaultRenderer(Date.class, new DateRenderer());
        recordsTable.setDefaultRenderer(Double.class, new DoubleRenderer());
        JScrollPane scrollPane = new JScrollPane(recordsTable);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 5;
        c.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, c);


        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 5, 5, 0);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        panel.add(saveButton, c);


        JButton loadButton = new JButton("Load");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(loadButton, c);


        JButton deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(deleteButton, c);


        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand("clear");
        clearButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 3;
        c.gridy = 1;
        panel.add(clearButton, c);

        JButton reverseButton = new JButton("Rev");
        reverseButton.setActionCommand("reverse");
        reverseButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 5, 5, 5);
        c.gridx = 4;
        c.gridy = 1;
        panel.add(reverseButton, c);


        return panel;
    }

    @Override
    public void property(PropertyChangeEvent e) {
        CharacterRecordModel record = (CharacterRecordModel) e.getNewValue();
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();

        if (currentType == ModuleType.RECORD) {
            model.add(record);
        } else if (currentType == ModuleType.MOVE) {
            // update the model
            model.update(record);
        }
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
        } else if ("load".equals(e.getActionCommand())) {
            load();
        } else if ("recordAI".equals(e.getActionCommand())) {
            recordAi();
        } else if ("moveAI".equals(e.getActionCommand())) {
            moveAi();
        }
    }

    public void save() {
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();
        String csv = CsvTools.buildCsvFile(model.getData());

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

    public void load() {
        int returnVal = fc.showOpenDialog(ui);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            List<CharacterRecordModel> records = CsvTools.parseCsvFile(fc.getSelectedFile());
            RecordTableModel model = (RecordTableModel) recordsTable.getModel();
            for (CharacterRecordModel record : records) {
                model.add(record);
            }
            buildAI();
        }
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

    public void recordAi() {
        currentType = ModuleType.RECORD;
        buildAI();
    }

    public void moveAi() {
        currentType = ModuleType.MOVE;
        buildAI();
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
        return "Move";
    }

    public enum ModuleType {
        RECORD,
        MOVE
    }
}
