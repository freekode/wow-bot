package org.freekode.wowbot.modules;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.ai.GatherAI;
import org.freekode.wowbot.ai.Intelligence;
import org.freekode.wowbot.ai.MovingAI;
import org.freekode.wowbot.ai.RecordingAI;
import org.freekode.wowbot.entity.moving.CharacterRecordEntity;
import org.freekode.wowbot.ui.moving.MapUI;
import org.freekode.wowbot.ui.moving.RecordTableModel;
import org.freekode.wowbot.ui.renderers.DateRenderer;
import org.freekode.wowbot.ui.renderers.DoubleRenderer;
import org.freekode.wowbot.tools.StaticFunc;

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
import java.util.LinkedList;
import java.util.List;

public class MoveModule extends Module implements ActionListener {
    private static final Logger logger = LogManager.getLogger(MoveModule.class);
    private ModuleType currentType = ModuleType.RECORD;
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
        } else if (currentType == ModuleType.GATHER) {
            buildGatherUI();
        }

    }

    public void buildRecordUI() {
        ai = new RecordingAI();
        ai.addPropertyChangeListener(this);
    }

    public void buildMoveUI() {
        List<Vector3D> points = new ArrayList<>();
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();

        for (CharacterRecordEntity record : model.getData()) {
            points.add(record.getCoordinates());
        }

        int selectedIndex = recordsTable.getSelectedRow();
        if (selectedIndex > -1) {
            points = points.subList(selectedIndex, points.size());
        }

        ai = new MovingAI(points);
        ai.addPropertyChangeListener(this);
    }

    public void buildGatherUI() {
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();

        List<CharacterRecordEntity> records = new LinkedList<>(model.getData());
        int selectedIndex = recordsTable.getSelectedRow();
        if (selectedIndex > -1) {
            records = records.subList(selectedIndex, records.size());
        }

        ai = new GatherAI(records);
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

        JRadioButton gatherRadio = new JRadioButton("Gather");
        gatherRadio.addActionListener(this);
        gatherRadio.setActionCommand("gatherAI");
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(gatherRadio, c);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(recordRadio);
        buttonGroup.add(moveRadio);
        buttonGroup.add(gatherRadio);


        recordsTable = new JTable(new RecordTableModel());
        recordsTable.setDefaultRenderer(Date.class, new DateRenderer());
        recordsTable.setDefaultRenderer(Double.class, new DoubleRenderer());
        recordsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(recordsTable);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 6;
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
        c.insets = new Insets(0, 5, 5, 0);
        c.gridx = 4;
        c.gridy = 1;
        panel.add(reverseButton, c);

        JButton mapButton = new JButton("Map");
        mapButton.setActionCommand("showMap");
        mapButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 5, 5, 0);
        c.gridx = 5;
        c.gridy = 1;
        panel.add(mapButton, c);


        return panel;
    }

    @Override
    public void customProperty(PropertyChangeEvent e) {
        CharacterRecordEntity record = (CharacterRecordEntity) e.getNewValue();
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();

        if (currentType == ModuleType.RECORD) {
            Integer index = model.add(record);
            recordsTable.scrollRectToVisible(recordsTable.getCellRect(index, 0, true));
        } else if (currentType == ModuleType.MOVE || currentType == ModuleType.GATHER) {
            // update the model
            Integer index = model.update(record);
            if (index != null) {
                recordsTable.changeSelection(index, 0, false, false);
                recordsTable.scrollRectToVisible(recordsTable.getCellRect(index, 0, true));
            }
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
        } else if ("gatherAI".equals(e.getActionCommand())) {
            gatherAi();
        } else if ("showMap".equals(e.getActionCommand())) {
            showMap();
        }
    }

    public void save() {
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();
        String csv = StaticFunc.buildCsvFile(model.getData());

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
            List<CharacterRecordEntity> records = StaticFunc.parseCsvFile(fc.getSelectedFile());
            RecordTableModel model = (RecordTableModel) recordsTable.getModel();
            for (CharacterRecordEntity record : records) {
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

    public void gatherAi() {
        currentType = ModuleType.GATHER;
        buildAI();
    }

    public void showMap() {
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();
        List<CharacterRecordEntity> records = model.getData();

        MapUI optionsWindow = new MapUI();
        optionsWindow.init(records);
        optionsWindow.addPropertyChangeListener(this);
    }

    @Override
    public Component getUI() {
        return ui;
    }

    @Override
    public String getName() {
        return "Move";
    }

    public enum ModuleType {
        RECORD,
        MOVE,
        GATHER
    }
}
