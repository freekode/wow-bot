package org.freekode.wowbot.modules;

import org.freekode.wowbot.ai.InfoAI;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class InfoModule extends Module {
    private Component ui;
    private JLabel xLabel = new JLabel("0");
    private JLabel yLabel = new JLabel("0");
    private JLabel azimuthLabel = new JLabel("0");
    private JLabel pitchLabel = new JLabel("0");
    private JCheckBox isInCombatLabel = new JCheckBox();
    private JCheckBox isHerbLabel = new JCheckBox();
    private JCheckBox isOreLabel = new JCheckBox();
    private JCheckBox bagUpdateLabel = new JCheckBox();
    private JCheckBox hasTargetLabel = new JCheckBox();
    private JCheckBox inActionRangeLabel = new JCheckBox();


    public InfoModule() {
        ui = buildInterface();
        buildAI();
    }

    public Component buildInterface() {
        JPanel panel = new JPanel(new GridBagLayout());
//        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        GridBagConstraints c = new GridBagConstraints();


        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(0, 0, 0, 20);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(addFirstPart(), c);

        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(0, 0, 0, 20);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(addSecondPart(), c);

        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(0, 0, 0, 20);
        c.gridx = 2;
        c.gridy = 0;
        panel.add(addThirdPart(), c);


        return panel;
    }

    public JPanel addFirstPart() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 20);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("X"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(xLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 20);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Y"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(yLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 20);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Azimuth"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(azimuthLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0, 20);
        c.gridx = 0;
        c.gridy = 3;
        panel.add(new JLabel("Pitch"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(pitchLabel, c);


        return panel;
    }

    public JPanel addSecondPart() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Herb"), c);

        isHerbLabel.setEnabled(false);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(isHerbLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0, 10);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Ore"), c);

        isOreLabel.setEnabled(false);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(isOreLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0, 10);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Bag update"), c);

        bagUpdateLabel.setEnabled(false);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(bagUpdateLabel, c);


        return panel;
    }

    public JPanel addThirdPart() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("In combat"), c);

        isInCombatLabel.setEnabled(false);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(isInCombatLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0, 10);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Has target"), c);

        hasTargetLabel.setEnabled(false);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(hasTargetLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0, 10);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("In range"), c);

        inActionRangeLabel.setEnabled(false);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(inActionRangeLabel, c);


        return panel;
    }

    @Override
    public void buildAI() {
        if (ai == null || ai.isDone() || ai.isCancelled()) {
            ai = new InfoAI();
            ai.addPropertyChangeListener(this);
        }
    }

    @Override
    public Component getUI() {
        return ui;
    }

    @Override
    public String getName() {
        return "Information";
    }

    @Override
    public void property(PropertyChangeEvent e) {
        InfoAI.InfoUpdate update = (InfoAI.InfoUpdate) e.getNewValue();

        xLabel.setText(update.getX().toString());
        yLabel.setText(update.getY().toString());
        azimuthLabel.setText(update.getAzimuth().toString());
        pitchLabel.setText(update.getPitch().toString());

        isInCombatLabel.setSelected(update.getInCombat());
        isHerbLabel.setSelected(update.getHerb());
        isOreLabel.setSelected(update.getOre());
        bagUpdateLabel.setSelected(update.getBagUpdate());
        hasTargetLabel.setSelected(update.getHasTarget());
        inActionRangeLabel.setSelected(update.getInRange());
    }
}
