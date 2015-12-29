package org.freekode.wowbot.modules.moving;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.beans.ai.MovingAI;
import org.freekode.wowbot.modules.Module;
import org.freekode.wowbot.tools.DateRenderer;
import org.freekode.wowbot.tools.DoubleRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovingModule extends Module implements ActionListener {
    private static final Logger logger = LogManager.getLogger(MovingModule.class);
    private MovingAI ai;
    private Component ui;
    private JFileChooser fc;
    private JTable recordsTable;


    public MovingModule() {
        ui = buildInterface();
        buildAI();
    }

    public Component buildInterface() {
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
        c.gridwidth = 3;
        panel.add(scrollPane, c);


        JButton saveButton = new JButton("Load");
        saveButton.setActionCommand("load");
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
    public void buildAI() {
//        if (ai == null || ai.isDone() || ai.isCancelled()) {

        List<Vector3D> points = new ArrayList<>();
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();
        for (CharacterRecordModel record : model.getData()) {
            points.add(record.getCoordinates());
        }

        ai = new MovingAI(points);
        ai.addPropertyChangeListener(this);
//        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("load".equals(e.getActionCommand())) {
            load();
        }
    }

    public void load() {
        int returnVal = fc.showOpenDialog(ui);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            parseCsvFile(fc.getSelectedFile());
            buildAI();
        }
    }

    public void parseCsvFile(File file) {
        Pattern pattern = Pattern.compile("([\\d\\.]*);([\\d\\.]*);([\\d\\.]*)");
        RecordTableModel model = (RecordTableModel) recordsTable.getModel();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {

                    Date date = new Date(new Long(matcher.group(1)));
                    Double x = new Double(matcher.group(2));
                    Double y = new Double(matcher.group(3));

                    model.add(new CharacterRecordModel(date, new Vector3D(x, y, 0)));
                }
            }
        } catch (IOException e) {
            logger.info("read file exception", e);
        }
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
}
