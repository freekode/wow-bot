package org.freekode.wowbot.modules;

import org.freekode.wowbot.beans.ai.IntelligenceThread;
import org.freekode.wowbot.beans.ai.RecordingAI;

import javax.swing.*;
import java.awt.*;

public class RecordingModuleOld extends ModuleOld {
    private DefaultListModel<String> recordsModel;


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
    public IntelligenceThread getAi() {
        return new RecordingAI(recordsModel);
    }
}
