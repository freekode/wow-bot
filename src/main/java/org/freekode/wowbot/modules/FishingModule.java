package org.freekode.wowbot.modules;

import org.freekode.wowbot.beans.ai.FishingAI;
import org.freekode.wowbot.beans.ai.Intelligence;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;

public class FishingModule extends Module {
    private Component ui;
    private Intelligence ai;
    private JFormattedTextField fishButton;
    private JFormattedTextField failTryings;

    public FishingModule() {
        ui = buildInterface();
        buildAI();
    }

    public Component buildInterface() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel fishBtnLabel = new JLabel("Fish button");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        panel.add(fishBtnLabel, c);

        JLabel failLabel = new JLabel("Fail tryings");
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 10);
        panel.add(failLabel, c);


        try {
            fishButton = new JFormattedTextField(new MaskFormatter("*"));
            fishButton.setValue("=");
            fishButton.setColumns(4);
            c.gridx = 1;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 0, 0);
            panel.add(fishButton, c);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        failTryings = new JFormattedTextField(NumberFormat.getNumberInstance());
        failTryings.setValue(5);
        failTryings.setColumns(4);
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        panel.add(failTryings, c);


        return panel;
    }

    @Override
    public void buildAI() {
        int fishButtonValue = KeyStroke.getKeyStroke(fishButton.getText().charAt(0), 0).getKeyCode();
        int failTryingsValue = Integer.valueOf(failTryings.getText());
        ai = new FishingAI(fishButtonValue, failTryingsValue);
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
