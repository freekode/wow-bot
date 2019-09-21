package org.freekode.wowbot.gui.models;

import java.awt.Color;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.freekode.wowbot.entity.fishing.FishingUpdateEntity;

public class FishingTableModel extends AbstractTableModel {

	private String[] columnNames = {"Date", "Kit name", "Caught", "1", "2", "3"};

	private Class[] columnClasses = {Date.class, String.class, Boolean.class, Color.class, Color.class, Color.class};

	private List<FishingUpdateEntity> data = new LinkedList<>();

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
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
	public Class<?> getColumnClass(int columnIndex) {
		return columnClasses[columnIndex];
	}

	public Integer updateOrAdd(FishingUpdateEntity newRecord) {
		Integer updateIndex = update(newRecord);
		if (updateIndex != null) {
			return updateIndex;
		}

		data.add(newRecord);
		fireTableRowsInserted(data.size(), data.size());
		return data.size();
	}

	public Integer update(FishingUpdateEntity newRecord) {
		for (int i = 0; i < data.size(); i++) {
			FishingUpdateEntity element = data.get(i);
			if (newRecord.equals(element)) {
				element.setFirst(newRecord.getFirst());
				element.setSecond(newRecord.getSecond());
				element.setThird(newRecord.getThird());
				element.setCaught(newRecord.getCaught());
				fireTableRowsUpdated(i, i);
				return i;
			}
		}

		return null;
	}

	public void clear() {
		int size = data.size();
		data.clear();

		fireTableRowsDeleted(0, size);
	}

	public List<FishingUpdateEntity> getData() {
		return data;
	}
}
