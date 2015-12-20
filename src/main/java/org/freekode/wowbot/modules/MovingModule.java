package org.freekode.wowbot.modules;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.beans.ai.MovingAI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovingModule extends Module implements ActionListener {
    private static final Logger logger = LogManager.getLogger(MovingModule.class);
    private MovingAI ai;
    private Component ui;
    private JList<String> recordsList;
    private JFileChooser fc;
    private List<Vector3D> points = new ArrayList<>();


    public MovingModule() {
        ui = buildInterface();
        buildAI();
    }

    public Component buildInterface() {
        fc = new JFileChooser();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        DefaultListModel<String> recordsModel = new DefaultListModel<>();
        recordsList = new JList<>(recordsModel);
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 3;
        panel.add(recordsList, c);


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
        Pattern pattern = Pattern.compile("([\\d\\.]*);([\\d\\.]*)");
        DefaultListModel<String> model = (DefaultListModel<String>) recordsList.getModel();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    points.add(new Vector3D(new Double(matcher.group(1)), new Double(matcher.group(2)), 0));
                    model.addElement(matcher.group(1) + "; " + matcher.group(2));
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
