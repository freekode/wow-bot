package org.freekode.wowbot.modules.fishing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.ai.FishingAI;
import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.modules.Module;
import org.freekode.wowbot.tools.DateRenderer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

public class FishingModule extends Module {
    private static final Logger logger = LogManager.getLogger(FishingModule.class);
    private Component ui;
    private Intelligence ai;
    private JFormattedTextField fishButton;
    private JFormattedTextField failTryings;
    private Integer catches = 0;
    private Integer fails = 0;
    private JLabel catchesCountLabel;
    private JLabel failsCountLabel;
    private JTable recordsTable;



    public FishingModule() {
        ui = buildInterface();
        buildAI();
    }

    public Component buildInterface() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // row 0
        JLabel fishBtnLabel = new JLabel("Fish button");
        fishBtnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(fishBtnLabel, c);

        try {
            fishButton = new JFormattedTextField(new MaskFormatter("*"));
            fishButton.setValue("=");
            fishButton.setPreferredSize(new Dimension(40, 20));
            c.gridx = 1;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 0, 0);
            c.anchor = GridBagConstraints.LINE_START;
            panel.add(fishButton, c);
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
        panel.add(failTryingsLabel, c);

        failTryings = new JFormattedTextField(NumberFormat.getNumberInstance());
        failTryings.setPreferredSize(new Dimension(40, 20));
        failTryings.setValue(5);
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(failTryings, c);


        // row 2
        JLabel catchesLabel = new JLabel("Catches");
        catchesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(catchesLabel, c);

        catchesCountLabel = new JLabel(catches.toString());
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(catchesCountLabel, c);


        // row 3
        JLabel failsLabel = new JLabel("Fails");
        failsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 0, 5, 10);
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(failsLabel, c);

        failsCountLabel = new JLabel(fails.toString());
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(0, 0, 5, 0);
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(failsCountLabel, c);


        // row 4
        recordsTable = new JTable(new FishingTableModel());
        recordsTable.setDefaultRenderer(Date.class, new DateRenderer("yyyy-MM-dd HH:mm:ss"));
        JScrollPane scrollPane = new JScrollPane(recordsTable);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, c);


        return panel;
    }

    @Override
    public void buildAI() {
        int fishButtonValue = KeyStroke.getKeyStroke(fishButton.getText().charAt(0), 0).getKeyCode();
        int failTryingsValue = Integer.valueOf(failTryings.getText());

        ai = new FishingAI(fishButtonValue, failTryingsValue);
        ai.addPropertyChangeListener(this);
    }

    @Override
    public void property(PropertyChangeEvent e) {
        if((Boolean) e.getNewValue()) {
            catches++;
            catchesCountLabel.setText(catches.toString());

        } else {
            fails++;
            failsCountLabel.setText(fails.toString());
        }

        FishingTableModel model = (FishingTableModel) recordsTable.getModel();
        model.add(new FishingRecord(new Date(), (Boolean) e.getNewValue()));
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
        return "Fishing";
    }
}
