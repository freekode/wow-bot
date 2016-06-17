package org.freekode.wowbot.modules.fishing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.ai.FishingAI;
import org.freekode.wowbot.ai.Intelligence;
import org.freekode.wowbot.entity.fishing.FishingKitEntity;
import org.freekode.wowbot.entity.fishing.FishingOptionsEntity;
import org.freekode.wowbot.entity.fishing.FishingRecordEntity;
import org.freekode.wowbot.modules.Module;
import org.freekode.wowbot.ui.renderers.ColorCellRenderer;
import org.freekode.wowbot.tools.ConfigKeys;
import org.freekode.wowbot.ui.renderers.DateRenderer;
import org.freekode.wowbot.tools.StaticFunc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FishingModule extends Module implements ActionListener {
    private static final Logger logger = LogManager.getLogger(FishingModule.class);
    private FishingOptionsEntity optionsModel;
    private Component ui;
    private Intelligence ai;
    private Integer bobberThrows = 0;
    private Integer catches = 0;
    private Integer fails = 0;
    private JLabel statusLabel;
    private JTable recordsTable;


    public FishingModule() {
        Map<String, Object> config = StaticFunc.loadYaml(ConfigKeys.YAML_CONFIG_FILENAME, "fishing");
        optionsModel = new FishingOptionsEntity();
        optionsModel.parse(config);

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
        JLabel statusTitleLabel = new JLabel("Throws/Catches/Fails");
        statusTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(statusTitleLabel, c);

        statusLabel = new JLabel();
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(statusLabel, c);
        updateStatus();


        // row 3
        recordsTable = new JTable(new FishingTableModel());
        recordsTable.setDefaultRenderer(Date.class, new DateRenderer("yyyy-MM-dd HH:mm:ss"));
        recordsTable.setDefaultRenderer(Color.class, new ColorCellRenderer());
        recordsTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        recordsTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        recordsTable.getColumnModel().getColumn(2).setPreferredWidth(30);
        recordsTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(recordsTable), c);


        return panel;
    }

    @Override
    public void buildAI() {
        int fishButtonValue = KeyStroke.getKeyStroke(optionsModel.getFishKey().charAt(0), 0).getKeyCode();
        int failTryingsValue = optionsModel.getFailTryings();

        List<FishingKitEntity> enabledKits = new LinkedList<>();
        for (FishingKitEntity kit : optionsModel.getKits()) {
            if (kit.getEnable()) {
                enabledKits.add(kit);
            }
        }

        ai = new FishingAI(fishButtonValue, failTryingsValue, enabledKits);
        ai.addPropertyChangeListener(this);
    }

    @Override
    public void property(PropertyChangeEvent e) {
        FishingTableModel model = (FishingTableModel) recordsTable.getModel();
        FishingRecordEntity record = (FishingRecordEntity) e.getNewValue();

        bobberThrows = model.getRowCount();
        if (record.getCaught() != null) {
            if (record.getCaught()) {
                catches++;
            } else {
                fails++;
            }
        }
        updateStatus();


        Integer index = model.updateOrAdd(record);
        recordsTable.scrollRectToVisible(recordsTable.getCellRect(index, 0, true));
    }

    public void updateStatus() {
        statusLabel.setText(bobberThrows.toString() + "/" + catches.toString() + "/" + fails.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("showOptions".equals(e.getActionCommand())) {
            showOptions();
        }
    }

    public void showOptions() {
        FishingOptionsUI optionsWindow = new FishingOptionsUI();
        optionsWindow.init(new FishingOptionsEntity(optionsModel));
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
//        StaticFunc.saveProperties("fishing", optionsModel.getMap());
        StaticFunc.saveYaml(ConfigKeys.YAML_CONFIG_FILENAME, "fishing", options.getMap());
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
