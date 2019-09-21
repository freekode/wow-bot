package org.freekode.wowbot.gui.renderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

public class ColorListRenderer extends JLabel implements ListCellRenderer<Color> {

	public ColorListRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Color> list, Color value, int index, boolean isSelected, boolean cellHasFocus) {
		setBackground(value);
		setText(String.format("#%06X", (0xFFFFFF & value.getRGB())));
		setForeground(new Color((0xFFFFFF - value.getRGB())));

		//        setPreferredSize(new Dimension(20, 20));

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
