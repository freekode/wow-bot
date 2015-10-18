package org.freekode.wowbot.modules;

import org.freekode.wowbot.beans.ai.FishingAI;
import org.freekode.wowbot.beans.ai.Intelligence;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class FishingModule extends Module {
    private JFormattedTextField fishButton;
    private JFormattedTextField failTryings;



    @Override
    public Component getUI() {
        JPanel panel = new JPanel(new SpringLayout());

        GridBagConstraints c = new GridBagConstraints();

        JLabel fishBtnLabel = new JLabel("Fish button");
        fishBtnLabel.setVerticalAlignment(SwingConstants.CENTER);
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(0, 0, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(fishBtnLabel, c);

        fishButton = new JFormattedTextField(NumberFormat.getNumberInstance());
        fishButton.setValue(61);
        fishButton.setColumns(4);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(fishButton, c);


        JLabel failLabel = new JLabel("Fail tryings");
        failLabel.setVerticalAlignment(SwingConstants.CENTER);
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(0, 0, 10, 10);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(failLabel, c);

        failTryings = new JFormattedTextField(NumberFormat.getNumberInstance());
        failTryings.setValue(5);
        failTryings.setColumns(4);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(failTryings, c);


        return panel;
    }

    @Override
    public Intelligence getAi() {
        int fishButtonValue = Integer.valueOf(fishButton.getText());
        int failTryingsValue = Integer.valueOf(failTryings.getText());
        return new FishingAI(fishButtonValue, failTryingsValue);
    }
}
