package org.freekode.wowbot.gui;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

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
