package org.freekode.wowbot.gui.renderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.freekode.wowbot.tools.StaticFunc;

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
