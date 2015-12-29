package org.freekode.wowbot.modules.fishing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.tools.ColorRenderer;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;

public class FishingOptionsUI extends JFrame implements ActionListener {
    private static final Logger logger = LogManager.getLogger(FishingOptionsUI.class);
    private JFormattedTextField fishKey;
    private JFormattedTextField failTryings;
    private FishingOptionsModel optionsModel;


    public void init(FishingOptionsModel optionsModel) {
        this.optionsModel = optionsModel;

        setTitle("Fishing options");
        setSize(170, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(50, 100);

        buildInterface();

        setVisible(true);
    }

    public void buildInterface() {
        Container pane = getContentPane();

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        // row 0
        JLabel fishBtnLabel = new JLabel("Fish button");
        fishBtnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.LINE_END;
        pane.add(fishBtnLabel, c);

        try {
            fishKey = new JFormattedTextField(new MaskFormatter("*"));
            fishKey.setValue(optionsModel.getFishKey());
            fishKey.setPreferredSize(new Dimension(40, 20));
            c.gridx = 1;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 0, 0);
            c.anchor = GridBagConstraints.LINE_START;
            pane.add(fishKey, c);
        } catch (ParseException e) {
            logger.info("parse error", e);
        }


        // row 1
        JLabel failTryingsLabel = new JLabel("Fail tryings");
        failTryingsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.LINE_END;
        pane.add(failTryingsLabel, c);

        failTryings = new JFormattedTextField(NumberFormat.getNumberInstance());
        failTryings.setPreferredSize(new Dimension(40, 20));
        failTryings.setValue(Integer.valueOf(optionsModel.getFailTryings()));
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 10, 0);
        c.anchor = GridBagConstraints.LINE_START;
        pane.add(failTryings, c);


        JButton addFirstColorButton = new JButton("+");
        addFirstColorButton.addActionListener(this);
        addFirstColorButton.setActionCommand("addFirstColor");
        addFirstColorButton.setPreferredSize(new Dimension(20, 20));
        addFirstColorButton.setMargin(new Insets(0, 0, 0, 0));
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        pane.add(addFirstColorButton, c);

        JButton removeFirstColorButton = new JButton("-");
        removeFirstColorButton.addActionListener(this);
        removeFirstColorButton.setActionCommand("removeFirstColor");
        removeFirstColorButton.setPreferredSize(new Dimension(20, 20));
        removeFirstColorButton.setMargin(new Insets(0, 0, 0, 0));
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        pane.add(removeFirstColorButton, c);


        JTable recordsTable = new JTable(new FishingTableModel());
        recordsTable.setDefaultRenderer(Color.class, new ColorRenderer());
        recordsTable.setPreferredSize(new Dimension(40, 70));
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        pane.add(recordsTable, c);


        JButton optionsButton = new JButton("Save");
        optionsButton.addActionListener(this);
        optionsButton.setActionCommand("saveOptions");
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 1;
        pane.add(optionsButton, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("saveOptions".equals(e.getActionCommand())) {
            saveOptions();
            dispose();
        }
    }

    public void saveOptions() {
        optionsModel.setFailTryings(failTryings.getText());
        optionsModel.setFishKey(fishKey.getText());

        firePropertyChange("saveOptions", null, optionsModel);
    }
}

