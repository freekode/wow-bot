package org.freekode.wowbot.modules.fishing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.tools.ColorListRenderer;
import org.freekode.wowbot.tools.StaticFunc;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FishingOptionsUI extends JFrame implements ActionListener {
    private static final Logger logger = LogManager.getLogger(FishingOptionsUI.class);
    private FishingOptionsModel optionsModel;
    private JFormattedTextField fishKey;
    private JFormattedTextField failTryings;
    private JList<Color> firstColorList;
    private JList<Color> secondColorList;
    private JList<Color> thirdColorList;


    public void init(FishingOptionsModel optionsModel) {
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


        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        pane.add(getFirstPart(), c);

        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        pane.add(getSecondPart(), c);

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 2;
        pane.add(getThirdPart(), c);
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

    public JPanel getSecondPart() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        panel.add(getFirstColorList(), c);

        c.gridx = 1;
        c.gridy = 0;
        panel.add(getSecondColorList(), c);

        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        panel.add(getThirdColorList(), c);


        return panel;
    }

    public JPanel getThirdPart() {
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

    public JPanel getFirstColorList() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(new JLabel("Red"), c);

        DefaultListModel<Color> listFirstModel = new DefaultListModel<>();
        for (Color elem : optionsModel.getFirstColors()) {
            listFirstModel.addElement(elem);
        }
        firstColorList = new JList<>(listFirstModel);
        firstColorList.setCellRenderer(new ColorListRenderer());
        firstColorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JScrollPane(firstColorList), c);

        JButton addFirstColorButton = new JButton("+");
        addFirstColorButton.addActionListener(this);
        addFirstColorButton.setActionCommand("addFirstColor");
        addFirstColorButton.setPreferredSize(new Dimension(20, 20));
        addFirstColorButton.setMargin(new Insets(0, 0, 0, 0));
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        panel.add(addFirstColorButton, c);

        JButton removeFirstColorButton = new JButton("-");
        removeFirstColorButton.addActionListener(this);
        removeFirstColorButton.setActionCommand("removeFirstColor");
        removeFirstColorButton.setPreferredSize(new Dimension(20, 20));
        removeFirstColorButton.setMargin(new Insets(0, 0, 0, 0));
        c.gridx = 1;
        c.gridy = 2;
        panel.add(removeFirstColorButton, c);


        return panel;
    }

    public JPanel getSecondColorList() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(new JLabel("Blue"), c);

        DefaultListModel<Color> listModel = new DefaultListModel<>();
        for (Color elem : optionsModel.getSecondColors()) {
            listModel.addElement(elem);
        }
        secondColorList = new JList<>(listModel);
        secondColorList.setCellRenderer(new ColorListRenderer());
        secondColorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JScrollPane(secondColorList), c);

        JButton addColorButton = new JButton("+");
        addColorButton.addActionListener(this);
        addColorButton.setActionCommand("addSecondColor");
        addColorButton.setPreferredSize(new Dimension(20, 20));
        addColorButton.setMargin(new Insets(0, 0, 0, 0));
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        panel.add(addColorButton, c);

        JButton removeColorButton = new JButton("-");
        removeColorButton.addActionListener(this);
        removeColorButton.setActionCommand("removeSecondColor");
        removeColorButton.setPreferredSize(new Dimension(20, 20));
        removeColorButton.setMargin(new Insets(0, 0, 0, 0));
        c.gridx = 1;
        c.gridy = 2;
        panel.add(removeColorButton, c);


        return panel;
    }

    public JPanel getThirdColorList() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(new JLabel("WhYe"), c);

        DefaultListModel<Color> listModel = new DefaultListModel<>();
        for (Color elem : optionsModel.getThirdColors()) {
            listModel.addElement(elem);
        }
        thirdColorList = new JList<>(listModel);
        thirdColorList.setCellRenderer(new ColorListRenderer());
        thirdColorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JScrollPane(thirdColorList), c);

        JButton addColorButton = new JButton("+");
        addColorButton.addActionListener(this);
        addColorButton.setActionCommand("addThirdColor");
        addColorButton.setPreferredSize(new Dimension(20, 20));
        addColorButton.setMargin(new Insets(0, 0, 0, 0));
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        panel.add(addColorButton, c);

        JButton removeColorButton = new JButton("-");
        removeColorButton.addActionListener(this);
        removeColorButton.setActionCommand("removeThirdColor");
        removeColorButton.setPreferredSize(new Dimension(20, 20));
        removeColorButton.setMargin(new Insets(0, 0, 0, 0));
        c.gridx = 1;
        c.gridy = 2;
        panel.add(removeColorButton, c);


        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("saveOptions".equals(e.getActionCommand())) {
            saveOptions();
            dispose();
        } else if ("close".equals(e.getActionCommand())) {
            dispose();
        } else if ("addFirstColor".equals(e.getActionCommand())) {
            addFirstColor();
        } else if ("removeFirstColor".equals(e.getActionCommand())) {
            removeFirstColor();
        } else if ("addSecondColor".equals(e.getActionCommand())) {
            addSecondColor();
        } else if ("removeSecondColor".equals(e.getActionCommand())) {
            removeSecondColor();
        } else if ("addThirdColor".equals(e.getActionCommand())) {
            addThirdColor();
        } else if ("removeThirdColor".equals(e.getActionCommand())) {
            removeThirdColor();
        }
    }

    public void addFirstColor() {
        Color newColor = JColorChooser.showDialog(this, "Add first color", null);
        if (newColor != null) {
            DefaultListModel<Color> model = (DefaultListModel<Color>) firstColorList.getModel();
            model.addElement(newColor);
        }
    }

    public void removeFirstColor() {
        int index = firstColorList.getSelectedIndex();
        if (index > -1) {
            DefaultListModel<Color> model = (DefaultListModel<Color>) firstColorList.getModel();
            model.remove(index);
        }
    }

    public void addSecondColor() {
        Color newColor = JColorChooser.showDialog(this, "Add first color", null);
        if (newColor != null) {
            DefaultListModel<Color> model = (DefaultListModel<Color>) secondColorList.getModel();
            model.addElement(newColor);
        }
    }

    public void removeSecondColor() {
        int index = secondColorList.getSelectedIndex();
        if (index > -1) {
            DefaultListModel<Color> model = (DefaultListModel<Color>) secondColorList.getModel();
            model.remove(index);
        }
    }

    public void addThirdColor() {
        Color newColor = JColorChooser.showDialog(this, "Add third color", null);
        if (newColor != null) {
            DefaultListModel<Color> model = (DefaultListModel<Color>) thirdColorList.getModel();
            model.addElement(newColor);
        }
    }

    public void removeThirdColor() {
        int index = thirdColorList.getSelectedIndex();
        if (index > -1) {
            DefaultListModel<Color> model = (DefaultListModel<Color>) thirdColorList.getModel();
            model.remove(index);
        }
    }

    public void saveOptions() {
        optionsModel.setFailTryings(Integer.valueOf(failTryings.getText()));
        optionsModel.setFishKey(fishKey.getText());

        DefaultListModel<Color> modelFirst = (DefaultListModel<Color>) firstColorList.getModel();
        List<Color> colors = new ArrayList<>();
        for (int i = 0; i < modelFirst.getSize(); i++) {
            colors.add(modelFirst.get(i));
        }
        optionsModel.setFirstColors(colors);

        DefaultListModel<Color> modelSecond = (DefaultListModel<Color>) secondColorList.getModel();
        colors = new ArrayList<>();
        for (int i = 0; i < modelSecond.getSize(); i++) {
            colors.add(modelSecond.get(i));
        }
        optionsModel.setSecondColors(colors);

        DefaultListModel<Color> modelThird = (DefaultListModel<Color>) thirdColorList.getModel();
        colors = new ArrayList<>();
        for (int i = 0; i < modelThird.getSize(); i++) {
            colors.add(modelThird.get(i));
        }
        optionsModel.setThirdColors(colors);


        StaticFunc.saveProperties("fishing", optionsModel.getMap());


        firePropertyChange("saveOptions", null, optionsModel);
    }
}

