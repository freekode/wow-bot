package org.freekode.wowbot.modules;

import org.freekode.wowbot.beans.ai.InfoAI;
import org.freekode.wowbot.beans.ai.Intelligence;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class InfoModule extends Module {
    private Component ui;
    private Intelligence<InfoAI.InfoUpdate> ai;
    private JLabel xLabel = new JLabel("0");
    private JLabel yLabel = new JLabel("0");
    private JLabel azimuthLabel = new JLabel("0");
    private JLabel pitchLabel = new JLabel("0");
    private JLabel isInCombatLabel = new JLabel("false");
    private JLabel isHerbLabel = new JLabel("false");
    private JLabel isOreLabel = new JLabel("false");
    private JLabel bagUpdateLabel = new JLabel("false");


    public InfoModule() {
        ui = buildInterface();
        buildAI();
    }

    public Component buildInterface() {
        JPanel panel = new JPanel(new GridBagLayout());
//        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        GridBagConstraints c = new GridBagConstraints();


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0, 30);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(addLeftPart(), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(addRightPart(), c);


        return panel;
    }

    public JPanel addLeftPart() {
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

    public JPanel addRightPart() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 20);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("In combat"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(isInCombatLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 20);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Herb"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(isHerbLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 20);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Ore"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(isOreLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0, 20);
        c.gridx = 0;
        c.gridy = 3;
        panel.add(new JLabel("Bag update"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(bagUpdateLabel, c);


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
    public Intelligence getAI() {
        return ai;
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

        isInCombatLabel.setText(update.getInCombat().toString());
        isHerbLabel.setText(update.getHerb().toString());
        isOreLabel.setText(update.getOre().toString());
        bagUpdateLabel.setText(update.getBagUpdate().toString());
    }
}
