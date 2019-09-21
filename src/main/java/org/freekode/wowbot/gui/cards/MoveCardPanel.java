package org.freekode.wowbot.gui.cards;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import org.freekode.wowbot.entity.moving.CharacterUpdateEntity;
import org.freekode.wowbot.gui.dialogs.MapDialog;
import org.freekode.wowbot.gui.models.MovingTableModel;
import org.freekode.wowbot.gui.renderers.DateRenderer;
import org.freekode.wowbot.gui.renderers.DoubleRenderer;
import org.freekode.wowbot.modules.MoveModule;
import org.freekode.wowbot.tools.StaticFunc;

/**
 * card panel for moving
 */
public class MoveCardPanel extends AbstractCardPanel implements ActionListener, PropertyChangeListener {

	private JFileChooser fc;

	private JTable table;

	private MoveModule.ModuleType currentType = MoveModule.ModuleType.RECORD;

	private ButtonGroup buttonGroup;

	public MoveCardPanel() {
		init();
	}

	public void init() {
		fc = new JFileChooser();

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JRadioButton recordRadio = new JRadioButton("Record");
		recordRadio.addActionListener(this);
		recordRadio.setActionCommand("recordAI");
		recordRadio.setSelected(true);
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0, 0, 5, 0);
		c.gridx = 0;
		c.gridy = 0;
		panel.add(recordRadio, c);

		JRadioButton moveRadio = new JRadioButton("Move");
		moveRadio.addActionListener(this);
		moveRadio.setActionCommand("moveAI");
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(moveRadio, c);

		JRadioButton gatherRadio = new JRadioButton("Gather");
		gatherRadio.addActionListener(this);
		gatherRadio.setActionCommand("gatherAI");
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 2;
		c.gridy = 0;
		panel.add(gatherRadio, c);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(recordRadio);
		buttonGroup.add(moveRadio);
		buttonGroup.add(gatherRadio);

		table = new JTable(new MovingTableModel());
		table.setDefaultRenderer(Date.class, new DateRenderer());
		table.setDefaultRenderer(Double.class, new DoubleRenderer());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 6;
		c.fill = GridBagConstraints.BOTH;
		panel.add(scrollPane, c);

		JButton saveButton = new JButton("Save");
		saveButton.setActionCommand("savePoints");
		saveButton.addActionListener(this);
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0, 5, 5, 0);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		panel.add(saveButton, c);

		JButton loadButton = new JButton("Load");
		loadButton.setActionCommand("load");
		loadButton.addActionListener(this);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.gridy = 1;
		panel.add(loadButton, c);

		JButton deleteButton = new JButton("Delete");
		deleteButton.setActionCommand("delete");
		deleteButton.addActionListener(this);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 2;
		c.gridy = 1;
		panel.add(deleteButton, c);

		JButton clearButton = new JButton("Clear");
		clearButton.setActionCommand("clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MovingTableModel model = (MovingTableModel) table.getModel();
				model.clear();
			}
		});
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 3;
		c.gridy = 1;
		panel.add(clearButton, c);

		JButton reverseButton = new JButton("Rev");
		reverseButton.setActionCommand("reverse");
		reverseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MovingTableModel model = (MovingTableModel) table.getModel();
				model.reverse();
			}
		});
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 5, 5, 0);
		c.gridx = 4;
		c.gridy = 1;
		panel.add(reverseButton, c);

		JButton mapButton = new JButton("Map");
		mapButton.setActionCommand("showMap");
		mapButton.addActionListener(this);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 5, 5, 0);
		c.gridx = 5;
		c.gridy = 1;
		panel.add(mapButton, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setAiType();

		if ("savePoints".equals(e.getActionCommand())) {
			savePoints();
		} else if ("delete".equals(e.getActionCommand())) {
			delete();
		} else if ("load".equals(e.getActionCommand())) {
			load();
		} else if ("showMap".equals(e.getActionCommand())) {
			showMap();
		}
	}

	public void setAiType() {
		if ("recordAI".equals(buttonGroup.getSelection().getActionCommand())) {
			currentType = MoveModule.ModuleType.RECORD;
		} else if ("moveAI".equals(buttonGroup.getSelection().getActionCommand())) {
			currentType = MoveModule.ModuleType.MOVE;
		} else if ("gatherAI".equals(buttonGroup.getSelection().getActionCommand())) {
			currentType = MoveModule.ModuleType.GATHER;
		}
	}

	public void savePoints() {
		if (fc.showSaveDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
			MovingTableModel model = (MovingTableModel) table.getModel();

			Map<String, Object> map = new HashMap<>();
			map.put("file", fc.getSelectedFile());
			map.put("data", model.getData());

			fireUpdate(map, "savePoints");
		}
	}

	public void load() {
		int returnVal = fc.showOpenDialog(getParent());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			java.util.List<CharacterUpdateEntity> records = StaticFunc.parseCsvFile(fc.getSelectedFile());
			MovingTableModel model = (MovingTableModel) table.getModel();
			for (CharacterUpdateEntity record : records) {
				model.add(record);
			}
		}
	}

	public void delete() {
		MovingTableModel model = (MovingTableModel) table.getModel();
		int selectedIndex = table.getSelectedRow();
		if (selectedIndex != -1) {
			model.delete(selectedIndex);
		}
	}

	public void showMap() {
		MovingTableModel model = (MovingTableModel) table.getModel();
		List<CharacterUpdateEntity> records = model.getData();

		MapDialog optionsWindow = new MapDialog();
		optionsWindow.init(records);
		optionsWindow.addPropertyChangeListener(this);
	}

	public void update(CharacterUpdateEntity record) {
		MovingTableModel model = (MovingTableModel) table.getModel();

		Integer index = null;
		if (currentType == MoveModule.ModuleType.RECORD) {
			index = model.add(record);
		} else if (currentType == MoveModule.ModuleType.MOVE || currentType == MoveModule.ModuleType.GATHER) {
			// update the model
			index = model.update(record);
			if (index != null) {
				table.changeSelection(index, 0, false, false);
			}
		}

		if (index != null) {
			table.scrollRectToVisible(table.getCellRect(index, 0, true));
		}
	}

	public List<CharacterUpdateEntity> getPointList() {
		MovingTableModel model = (MovingTableModel) table.getModel();

		List<CharacterUpdateEntity> points = new LinkedList<>(model.getData());
		int selectedIndex = table.getSelectedRow();
		if (selectedIndex > -1) {
			points = points.subList(selectedIndex, points.size());
		}

		return points;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}
}
