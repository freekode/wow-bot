package org.freekode.wowbot.gui.dialogs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.entity.fishing.FishingKitEntity;
import org.freekode.wowbot.entity.fishing.FishingOptionsEntity;
import org.freekode.wowbot.gui.UpdateListener;
import org.freekode.wowbot.gui.components.ColorTablePanel;
import org.freekode.wowbot.gui.components.KitTablePanel;
import org.freekode.wowbot.gui.models.KitTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FishingOptionsDialog extends JDialog implements ActionListener {
    private static final Logger logger = LogManager.getLogger(FishingOptionsDialog.class);
    private List<UpdateListener> updateListeners = new ArrayList<>();
    private FishingOptionsEntity optionsEntity;
    private JFormattedTextField fishKey;
    private JFormattedTextField failTryings;
    private KitTablePanel kitTablePanel;
    private ColorTablePanel firstColorTablePanel;
    private ColorTablePanel secondColorTablePanel;
    private ColorTablePanel thirdColorTablePanel;


    public void init(FishingOptionsEntity optionsModel) {
        this.optionsEntity = optionsModel;

        setModal(true);
        setTitle("Fishing options");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocation(50, 100);

        build();

        setVisible(true);
    }

    public void build() {
        Container pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));


        JPanel mainSettings = getMainSettings();
        mainSettings.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pane.add(mainSettings);

        JPanel kitTable = getKitTable();
        kitTable.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(getKitTable());

        JPanel colorList = getColorList();
        colorList.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(getColorList());

        JPanel control = getControl();
        control.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pane.add(control);
    }

    /**
     * just simple configs inputs and labels
     */
    public JPanel getMainSettings() {
        JPanel fishButtonPanel = new JPanel();

        fishButtonPanel.add(new JLabel("Fish button", null, SwingConstants.RIGHT));

        try {
            fishKey = new JFormattedTextField(new MaskFormatter("*"));
            fishKey.setValue(optionsEntity.getFishKey());
            fishKey.setColumns(3);
            fishButtonPanel.add(fishKey);
        } catch (ParseException e) {
            logger.info("parse error", e);
        }


        JPanel failsPanel = new JPanel();

        failsPanel.add(new JLabel("Fail tryings", null, SwingConstants.RIGHT));

        failTryings = new JFormattedTextField(NumberFormat.getNumberInstance());
        failTryings.setColumns(3);
        failTryings.setValue(optionsEntity.getFailTryings());
        failsPanel.add(failTryings);


        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Main Settings"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(fishButtonPanel);
        panel.add(failsPanel);


        return panel;
    }

    /**
     * contains table with kits and enabling them
     */
    public JPanel getKitTable() {
        kitTablePanel = new KitTablePanel("Kit List", optionsEntity.getKits());
        kitTablePanel.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                KitTableModel model = (KitTableModel) kitTablePanel.getTable().getModel();
                int index = kitTablePanel.getTable().getSelectedRow();
                if (index > -1) {
                    FishingKitEntity kit = model.getData().get(index);
                    firstColorTablePanel.setSelectedColors(kit.getFirstColors());
                    secondColorTablePanel.setSelectedColors(kit.getSecondColors());
                    thirdColorTablePanel.setSelectedColors(kit.getThirdColors());
                }
            }
        });

        return kitTablePanel;
    }

    /**
     * colors of the kits with checkbox
     */
    public JPanel getColorList() {
        JPanel panel = new JPanel();


        // we need only unique colors in each set
        Set<Color> firstColorSet = new HashSet<>();
        Set<Color> secondColorSet = new HashSet<>();
        Set<Color> thirdColorSet = new HashSet<>();
        for (FishingKitEntity kit : optionsEntity.getKits()) {
            for (Color color : kit.getFirstColors()) {
                firstColorSet.add(color);
            }

            for (Color color : kit.getSecondColors()) {
                secondColorSet.add(color);
            }

            for (Color color : kit.getThirdColors()) {
                thirdColorSet.add(color);
            }
        }


        firstColorTablePanel = new ColorTablePanel("Red", new ArrayList<>(firstColorSet));
        firstColorTablePanel.getTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                KitTableModel model = (KitTableModel) kitTablePanel.getTable().getModel();
                int index = kitTablePanel.getTable().getSelectedRow();
                if (index > -1) {
                    FishingKitEntity kit = model.getData().get(index);
                    kit.setFirstColors(firstColorTablePanel.getSelectedColors());
                }
            }
        });
        panel.add(firstColorTablePanel);

        secondColorTablePanel = new ColorTablePanel("Blue", new ArrayList<>(secondColorSet));
        secondColorTablePanel.getTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                KitTableModel model = (KitTableModel) kitTablePanel.getTable().getModel();
                int index = kitTablePanel.getTable().getSelectedRow();
                if (index > -1) {
                    FishingKitEntity kit = model.getData().get(index);
                    kit.setSecondColors(secondColorTablePanel.getSelectedColors());
                }
            }
        });
        panel.add(secondColorTablePanel);

        thirdColorTablePanel = new ColorTablePanel("WhYe", new ArrayList<>(thirdColorSet));
        thirdColorTablePanel.getTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                KitTableModel model = (KitTableModel) kitTablePanel.getTable().getModel();
                int index = kitTablePanel.getTable().getSelectedRow();
                if (index > -1) {
                    FishingKitEntity kit = model.getData().get(index);
                    kit.setThirdColors(thirdColorTablePanel.getSelectedColors());
                }
            }
        });
        panel.add(thirdColorTablePanel);


        return panel;
    }

    /**
     * save and close without saving
     */
    public JPanel getControl() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("saveOptions");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(saveButton, c);


        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        closeButton.setActionCommand("close");
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        panel.add(closeButton, c);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("saveOptions".equals(e.getActionCommand())) {
            saveOptions();
            setVisible(false);
            dispose();
        } else if ("close".equals(e.getActionCommand())) {
            setVisible(false);
            dispose();
        }
    }

    public void saveOptions() {
        optionsEntity.setFailTryings(Integer.valueOf(failTryings.getText()));
        optionsEntity.setFishKey(fishKey.getText());

        KitTableModel model = (KitTableModel) kitTablePanel.getTable().getModel();
        optionsEntity.setKits(model.getData());

        fireUpdate(optionsEntity, "saveOptions");
    }

    public void saveKitColors(ColorTablePanel colorTablePanel) {
        System.out.println("save kit");
        KitTableModel model = (KitTableModel) kitTablePanel.getTable().getModel();
        int index = kitTablePanel.getTable().getSelectedRow();
        if (index > -1) {
            FishingKitEntity kit = model.getData().get(index);
            kit.setFirstColors(firstColorTablePanel.getSelectedColors());
            kit.setSecondColors(secondColorTablePanel.getSelectedColors());
            kit.setThirdColors(thirdColorTablePanel.getSelectedColors());
        }
    }

    public void addUpdateListener(UpdateListener l) {
        updateListeners.add(l);
    }

    public void fireUpdate(Object data, String command) {
        for (UpdateListener listener : updateListeners) {
            listener.updated(data, command);
        }
    }
}

