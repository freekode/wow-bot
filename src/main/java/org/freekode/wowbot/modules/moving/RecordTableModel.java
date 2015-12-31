package org.freekode.wowbot.modules.moving;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RecordTableModel extends AbstractTableModel {
    private static final Logger logger = LogManager.getLogger(RecordTableModel.class);
    private String[] columnNames = {"State", "Date", "X", "Y", "Action"};
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

    public Integer update(CharacterRecordModel record) {
        for (int i = 0; i < data.size(); i++) {
            CharacterRecordModel element = data.get(i);
            if (record.equals(element)) {
                element.setState(record.getState());
                fireTableRowsUpdated(i, i);
                return i;
            }
        }

        return null;
    }

    public List<CharacterRecordModel> getData() {
        return data;
    }
}
