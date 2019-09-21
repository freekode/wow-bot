package org.freekode.wowbot.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import org.freekode.wowbot.gui.models.ColorTableModel;
import org.freekode.wowbot.gui.renderers.ColorCellRenderer;

/**
 * the table for choosing the colors
 */
public class ColorTablePanel extends JPanel {

	private JTable table;

	public ColorTablePanel(String title, List<Color> colors) {
		init(title, colors);
	}

	public void init(String title, List<Color> colors) {
		setBorder(BorderFactory.createTitledBorder(title));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		ColorTableModel model = new ColorTableModel();
		for (Color elem : colors) {
			model.add(false, elem);
		}
		table = new JTable(model);
		table.setDefaultRenderer(Color.class, new ColorCellRenderer());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumnModel().getColumn(0).setPreferredWidth(26);
		table.setTableHeader(null);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(160, 165));
		add(scrollPane);

		ActionColorListener colorListener = new ActionColorListener();
		JPanel controlPanel = new JPanel();
		controlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton addButton = new JButton("Add");
		addButton.addActionListener(colorListener);
		addButton.setActionCommand("add");
		controlPanel.add(addButton);

		JButton deleteButton = new JButton("Del");
		deleteButton.addActionListener(colorListener);
		deleteButton.setActionCommand("delete");
		controlPanel.add(deleteButton);

		add(controlPanel);
	}

	public List<Color> getSelectedColors() {
		ColorTableModel model = (ColorTableModel) table.getModel();
		return model.getSelected();
	}

	public void setSelectedColors(List<Color> colors) {
		ColorTableModel model = (ColorTableModel) table.getModel();
		model.setSelected(colors);
	}

	public JTable getTable() {
		return table;
	}

	public class ActionColorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if ("add".equals(e.getActionCommand())) {
				add();
			} else if ("delete".equals(e.getActionCommand())) {
				delete();
			}
		}

		public void add() {
			Color newColor = JColorChooser.showDialog(getParent(), "Add color", null);
			if (newColor != null) {
				ColorTableModel model = (ColorTableModel) ColorTablePanel.this.table.getModel();
				Integer index = model.add(true, newColor);
				ColorTablePanel.this.table.scrollRectToVisible(ColorTablePanel.this.table.getCellRect(index, 0, true));

			}
		}

		public void delete() {
			int index = ColorTablePanel.this.table.getSelectedRow();
			if (index > -1) {
				ColorTableModel model = (ColorTableModel) ColorTablePanel.this.table.getModel();
				model.delete(index);
			}
		}
	}
}
