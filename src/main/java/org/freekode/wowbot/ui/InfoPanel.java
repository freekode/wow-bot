package org.freekode.wowbot.ui;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    JLabel xLabel = new JLabel("0");
    JLabel yLabel = new JLabel("0");
    JLabel azimuthLabel = new JLabel("0");
    JLabel pitchLabel = new JLabel("0");


    public InfoPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 20);
        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel("X"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 1;
        c.gridy = 0;
        add(xLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 20);
        c.gridx = 0;
        c.gridy = 1;
        add(new JLabel("Y"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 1;
        c.gridy = 1;
        add(yLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 20);
        c.gridx = 0;
        c.gridy = 2;
        add(new JLabel("Azimuth"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 1;
        c.gridy = 2;
        add(azimuthLabel, c);


        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 20);
        c.gridx = 0;
        c.gridy = 3;
        add(new JLabel("Pitch"), c);

        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 1;
        c.gridy = 3;
        add(pitchLabel, c);
    }

    public void updateInfo(String x, String y, String azimuth, String pitch) {
        xLabel.setText(x);
        yLabel.setText(y);
        azimuthLabel.setText(azimuth);
        pitchLabel.setText(pitch);
    }
}

