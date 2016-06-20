package org.freekode.wowbot.gui.renderers;

import org.freekode.wowbot.tools.StaticFunc;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ColorCellRenderer extends JLabel implements TableCellRenderer {
    public ColorCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Color color = (Color) value;
        setBackground(color);

        if (color != null) {
            setToolTipText(StaticFunc.encodeColor(color));
        }

        return this;
    }
}
