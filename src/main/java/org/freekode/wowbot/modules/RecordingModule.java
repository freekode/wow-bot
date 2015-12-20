package org.freekode.wowbot.modules;

import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.beans.ai.RecordingAI;

import javax.swing.*;
import java.awt.*;

public class RecordingModule extends Module {
    private DefaultListModel<String> recordsModel;
    private Intelligence ai;


    @Override
    public void createAiInstance() {
        ai = new RecordingAI(recordsModel);
    }

    @Override
    public Component getUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        recordsModel = new DefaultListModel<>();
        recordsModel.addElement("test");

        JList<String> recordsList = new JList<>(recordsModel);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(recordsList, c);


        return panel;
    }

    @Override
    public Intelligence getAI() {
        return ai;
    }

    @Override
    public String getName() {
        return "Recording";
    }
}
