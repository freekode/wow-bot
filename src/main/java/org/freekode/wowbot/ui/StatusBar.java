package org.freekode.wowbot.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class StatusBar extends JPanel {
    private JLabel label;


    public StatusBar() {
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        setPreferredSize(new Dimension(10, 18));

        add(label);
    }

    public void setText(String text) {
        label.setText(text);
    }
}
