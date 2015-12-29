package org.freekode.wowbot.tools;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ColorRenderer extends JLabel implements TableCellRenderer {
    public ColorRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Color color = (Color) value;
        setBackground(color);

        return this;
    }
}
