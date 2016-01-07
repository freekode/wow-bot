package org.freekode.wowbot.modules.fishing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.entity.fishing.FishingKitEntity;
import org.freekode.wowbot.entity.fishing.FishingOptionsEntity;
import org.freekode.wowbot.tools.ColorCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

public class FishingOptionsUI extends JFrame implements ActionListener, ListSelectionListener {
    private static final Logger logger = LogManager.getLogger(FishingOptionsUI.class);
    private FishingOptionsEntity optionsModel;
    private JFormattedTextField fishKey;
    private JFormattedTextField failTryings;
    private JList<FishingKitEntity> kitList;
    private ColorTable firstColorTable;
    private ColorTable secondColorTable;
    private ColorTable thirdColorTable;


    public void init(FishingOptionsEntity optionsModel) {
        this.optionsModel = optionsModel;

        setTitle("Fishing options");
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(50, 100);

        buildInterface();
        pack();


        setVisible(true);
    }

    public void buildInterface() {
        Container pane = getContentPane();

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.LINE_START;
        pane.add(getFirstPart(), c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 10, 10, 10);
        pane.add(getKitList(), c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        pane.add(getColorCheckList(), c);

        c.gridx = 0;
        c.gridy = 3;
        pane.add(getKitControl(), c);

        c.gridx = 0;
        c.gridy = 4;
        pane.add(getControl(), c);
    }

    public JPanel getFirstPart() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        JLabel fishBtnLabel = new JLabel("Fish button");
        fishBtnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(fishBtnLabel, c);

        try {
            fishKey = new JFormattedTextField(new MaskFormatter("*"));
            fishKey.setValue(optionsModel.getFishKey());
            fishKey.setPreferredSize(new Dimension(40, 20));
            c.gridx = 1;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 5, 0);
            c.anchor = GridBagConstraints.LINE_START;
            panel.add(fishKey, c);
        } catch (ParseException e) {
            logger.info("parse error", e);
        }


        JLabel failTryingsLabel = new JLabel("Fail tryings");
        failTryingsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(failTryingsLabel, c);

        failTryings = new JFormattedTextField(NumberFormat.getNumberInstance());
        failTryings.setPreferredSize(new Dimension(40, 20));
        failTryings.setValue(optionsModel.getFailTryings());
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(failTryings, c);


        return panel;
    }

    public JPanel getKitList() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        panel.add(new JLabel("Kit list"), c);

        DefaultListModel<FishingKitEntity> model = new DefaultListModel<>();
        for (FishingKitEntity elem : optionsModel.getKits()) {
            model.addElement(elem);
        }
        kitList = new JList<>(model);
        kitList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        kitList.addListSelectionListener(this);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JScrollPane(kitList), c);


        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);
        addButton.setActionCommand("addKit");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        panel.add(addButton, c);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(this);
        editButton.setActionCommand("editKit");
        c.gridx = 1;
        c.gridy = 2;
        panel.add(editButton, c);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("deleteKit");
        c.gridx = 2;
        c.gridy = 2;
        panel.add(deleteButton, c);


        return panel;
    }

    public JPanel getColorCheckList() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        Set<Color> firstColorSet = new HashSet<>();
        Set<Color> secondColorSet = new HashSet<>();
        Set<Color> thirdColorSet = new HashSet<>();
        for (FishingKitEntity kit : optionsModel.getKits()) {
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


        firstColorTable = new ColorTable("Red", new ArrayList<>(firstColorSet));
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        panel.add(firstColorTable, c);

        secondColorTable = new ColorTable("Blue", new ArrayList<>(secondColorSet));
        c.gridx = 1;
        c.gridy = 0;
        panel.add(secondColorTable, c);

        thirdColorTable = new ColorTable("WhYe", new ArrayList<>(thirdColorSet));
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        panel.add(thirdColorTable, c);


        return panel;
    }

    public JPanel getKitControl() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        JButton saveButton = new JButton("Save kit");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("saveKit");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(saveButton, c);

        return panel;
    }

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
            dispose();
        } else if ("close".equals(e.getActionCommand())) {
            dispose();
        } else if ("addKit".equals(e.getActionCommand())) {
            addKit();
        } else if ("deleteKit".equals(e.getActionCommand())) {
            deleteKit();
        } else if ("editKit".equals(e.getActionCommand())) {
            editKit();
        } else if ("saveKit".equals(e.getActionCommand())) {
            saveKit();
        }
    }

    public void saveOptions() {
        optionsModel.setFailTryings(Integer.valueOf(failTryings.getText()));
        optionsModel.setFishKey(fishKey.getText());

        DefaultListModel<FishingKitEntity> kitListModel = (DefaultListModel<FishingKitEntity>) kitList.getModel();
        List<FishingKitEntity> kitList = new ArrayList<>();
        for (int i = 0; i < kitListModel.size(); i++) {
            kitList.add(kitListModel.get(i));
        }
        optionsModel.setKits(kitList);


        firePropertyChange("saveOptions", null, optionsModel);
    }

    public void addKit() {
        String name = JOptionPane.showInputDialog(this, "Input name", "Add kit", JOptionPane.PLAIN_MESSAGE);
        if (name != null) {
            DefaultListModel<FishingKitEntity> model = (DefaultListModel<FishingKitEntity>) kitList.getModel();
            FishingKitEntity newKit = new FishingKitEntity(name);
            model.addElement(newKit);
            kitList.setSelectedValue(newKit, true);
        }
    }

    public void editKit() {
    }

    public void saveKit() {
        DefaultListModel<FishingKitEntity> model = (DefaultListModel<FishingKitEntity>) kitList.getModel();
        int index = kitList.getSelectedIndex();
        if (index > -1) {
            FishingKitEntity kit = model.get(index);
            kit.setFirstColors(firstColorTable.getSelectedColors());
            kit.setSecondColors(secondColorTable.getSelectedColors());
            kit.setThirdColors(thirdColorTable.getSelectedColors());
        }
    }

    public void deleteKit() {
        int index = kitList.getSelectedIndex();
        if (index > -1) {
            DefaultListModel<FishingKitEntity> model = (DefaultListModel<FishingKitEntity>) kitList.getModel();
            model.remove(index);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        DefaultListModel<FishingKitEntity> model = (DefaultListModel<FishingKitEntity>) kitList.getModel();
        int index = kitList.getSelectedIndex();
        if (index > -1) {
            FishingKitEntity kit = model.get(index);
            firstColorTable.setSelectedColors(kit.getFirstColors());
            secondColorTable.setSelectedColors(kit.getSecondColors());
            thirdColorTable.setSelectedColors(kit.getThirdColors());
        }
    }

    public class ColorTable extends JPanel {
        private JTable table;


        public ColorTable(String title, List<Color> colors) {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            add(new JLabel(title), c);

            CheckColorModel model = new CheckColorModel();
            for (Color elem : colors) {
                model.add(false, elem);
            }
            table = new JTable(model);
            table.setDefaultRenderer(Color.class, new ColorCellRenderer());
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getColumnModel().getColumn(0).setPreferredWidth(26);
            table.setTableHeader(null);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(80, 165));
            c.insets = new Insets(0, 0, 0, 0);
            c.gridx = 0;
            c.gridy = 1;
            add(scrollPane, c);


            ActionColorListener colorListener = new ActionColorListener(table);

            JButton addButton = new JButton("Add");
            addButton.addActionListener(colorListener);
            addButton.setActionCommand("add");
//            addButton.setPreferredSize(new Dimension(20, 20));
            addButton.setMargin(new Insets(0, 5, 0, 5));
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 1;
            add(addButton, c);

            JButton deleteButton = new JButton("Del");
            deleteButton.addActionListener(colorListener);
            deleteButton.setActionCommand("delete");
//            deleteButton.setPreferredSize(new Dimension(20, 20));
            deleteButton.setMargin(new Insets(0, 5, 0, 5));
            c.anchor = GridBagConstraints.LINE_END;
            c.gridx = 1;
            c.gridy = 2;
            add(deleteButton, c);
        }

        public List<Color> getSelectedColors() {
            CheckColorModel model = (CheckColorModel) table.getModel();
            return model.getSelected();
        }

        public void setSelectedColors(List<Color> colors) {
            CheckColorModel model = (CheckColorModel) table.getModel();
            model.setSelected(colors);
        }

        public class ActionColorListener implements ActionListener {
            private JTable table;


            public ActionColorListener(JTable table) {
                this.table = table;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if ("add".equals(e.getActionCommand())) {
                    add();
                } else if ("delete".equals(e.getActionCommand())) {
                    delete();
                }
            }

            public void add() {
                Color newColor = JColorChooser.showDialog(FishingOptionsUI.this, "Add color", null);
                if (newColor != null) {
                    CheckColorModel model = (CheckColorModel) table.getModel();
                    model.add(false, newColor);
                }
            }

            public void delete() {
                int index = table.getSelectedRow();
                if (index > -1) {
                    CheckColorModel model = (CheckColorModel) table.getModel();
                    model.delete(index);
                }
            }
        }
    }
}

