package org.freekode.wowbot.tools;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ColorListRenderer extends JLabel implements ListCellRenderer<Color> {
    public ColorListRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Color> list, Color value, int index, boolean isSelected, boolean cellHasFocus) {
        setBackground(value);
//        setText(String.format("#%06X", (0xFFFFFF & value.getRGB())));

        setPreferredSize(new Dimension(20, 20));

        if (isSelected) {
            Border selectedBorder = BorderFactory.createMatteBorder(2, 2, 2, 2, list.getSelectionBackground());
            setBorder(selectedBorder);
        } else {
            Border unselectedBorder = BorderFactory.createMatteBorder(2, 2, 2, 2, list.getBackground());
            setBorder(unselectedBorder);
        }

        return this;
    }
}
