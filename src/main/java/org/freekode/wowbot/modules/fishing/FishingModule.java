package org.freekode.wowbot.modules.fishing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.ai.FishingAI;
import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.modules.Module;
import org.freekode.wowbot.tools.ColorRenderer;
import org.freekode.wowbot.tools.DateRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Date;
import java.util.List;

public class FishingModule extends Module implements ActionListener {
    private static final Logger logger = LogManager.getLogger(FishingModule.class);
    private FishingOptionsEntity optionsModel;
    private Component ui;
    private Intelligence ai;
    private Integer catches = 0;
    private Integer fails = 0;
    private JLabel catchesCountLabel;
    private JLabel failsCountLabel;
    private JTable recordsTable;


    public FishingModule() {
        optionsModel = new FishingOptionsEntity();
        Map<String, Object> config = StaticFunc.loadProperties("fishing");
        optionsModel = new FishingOptionsModel(config);

        ui = buildInterface();
        buildAI();
    }

    public Component buildInterface() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // row 0
        JButton optionsButton = new JButton("Options");
        optionsButton.addActionListener(this);
        optionsButton.setActionCommand("showOptions");
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(optionsButton, c);


        // row 1
        JLabel catchesLabel = new JLabel("Catches");
        catchesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(catchesLabel, c);

        catchesCountLabel = new JLabel(catches.toString());
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(catchesCountLabel, c);


        // row 2
        JLabel failsLabel = new JLabel("Fails");
        failsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 5, 10);
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(failsLabel, c);

        failsCountLabel = new JLabel(fails.toString());
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 5, 0);
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(failsCountLabel, c);


        // row 3
        recordsTable = new JTable(new FishingTableEntity());
        recordsTable.setDefaultRenderer(Date.class, new DateRenderer("yyyy-MM-dd HH:mm:ss"));
        recordsTable.setDefaultRenderer(Color.class, new ColorCellRenderer());
        JScrollPane scrollPane = new JScrollPane(recordsTable);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, c);


        return panel;
    }

    @Override
    public void buildAI() {
        int fishButtonValue = KeyStroke.getKeyStroke(optionsModel.getFishKey().charAt(0), 0).getKeyCode();
        int failTryingsValue = optionsModel.getFailTryings();
        List<Color> firstColors = optionsModel.getFirstColors();
        List<Color> secondColors = optionsModel.getSecondColors();
        List<Color> thirdColors = optionsModel.getThirdColors();

        ai = new FishingAI(fishButtonValue, failTryingsValue, firstColors, secondColors, thirdColors);
        ai.addPropertyChangeListener(this);
    }

    @Override
    public void property(PropertyChangeEvent e) {
        FishingRecordEntity record = (FishingRecordEntity) e.getNewValue();

        if (record.getCaught() != null) {
            if (record.getCaught()) {
                catches++;
                catchesCountLabel.setText(catches.toString());
            } else {
                fails++;
                failsCountLabel.setText(fails.toString());
            }
        }

        FishingTableModel model = (FishingTableModel) recordsTable.getModel();
        Integer index = model.updateOrAdd(record);
        recordsTable.scrollRectToVisible(recordsTable.getCellRect(index, 0, true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("showOptions".equals(e.getActionCommand())) {
            showOptions();
        }
    }

    public void showOptions() {
        FishingOptionsUI optionsWindow = new FishingOptionsUI();
        optionsWindow.init(optionsModel);
        optionsWindow.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        super.propertyChange(e);

        if ("saveOptions".equals(e.getPropertyName())) {
            saveOptions((FishingOptionsEntity) e.getNewValue());
        }
    }

    public void saveOptions(FishingOptionsEntity options) {
        optionsModel = options;
        buildAI();

        logger.info("options saved");
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
