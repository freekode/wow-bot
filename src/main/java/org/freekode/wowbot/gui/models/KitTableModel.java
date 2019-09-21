package org.freekode.wowbot.gui.models;

import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.freekode.wowbot.entity.fishing.FishingKitEntity;

public class KitTableModel extends AbstractTableModel {

	private String[] columnNames = {"Enable", "Name"};

	private Class[] columnClasses = {Boolean.class, String.class};

	private List<FishingKitEntity> data = new LinkedList<>();

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnClasses.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).toList().get(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data.get(rowIndex).setEnable((Boolean) aValue);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClasses[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return getColumnClass(columnIndex) == Boolean.class;
	}

	public Integer add(FishingKitEntity entity) {
		data.add(entity);
		fireTableRowsInserted(data.size(), data.size());
		return data.size();
	}

	public void delete(int index) {
		data.remove(index);
		fireTableRowsDeleted(index, index);
	}

	public Integer update(FishingKitEntity kit) {
		for (int i = 0; i < data.size(); i++) {
			FishingKitEntity element = data.get(i);
			if (kit.equals(element)) {
				fireTableRowsUpdated(i, i);
				return i;
			}
		}

		return null;
	}

	public List<FishingKitEntity> getData() {
		return data;
	}
}
