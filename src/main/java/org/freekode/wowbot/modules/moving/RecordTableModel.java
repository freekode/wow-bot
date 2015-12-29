package org.freekode.wowbot.modules.moving;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RecordTableModel extends AbstractTableModel {
    private String[] columnNames = {"Date", "X", "Y"};
    private List<CharacterRecordModel> data = new LinkedList<>();


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
        return getValueAt(0, columnIndex).getClass();
    }

    public void add(CharacterRecordModel record) {
        data.add(record);
        fireTableRowsInserted(data.size(), data.size());
    }

    public void clear() {
        int size = data.size();
        data.clear();

        fireTableRowsDeleted(0, size);
    }

    public void delete(int i) {
        data.remove(i);
        fireTableRowsDeleted(i, i);
    }

    public void reverse() {
        Collections.reverse(data);
        fireTableRowsDeleted(0, data.size());
    }

    public List<CharacterRecordModel> getData() {
        return data;
    }
}
