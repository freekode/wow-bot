package org.freekode.wowbot.modules.fishing;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CheckColorTableModel extends AbstractTableModel {
    private Class[] columnClasses = {Boolean.class, Color.class};
    private List<Record> data = new LinkedList<>();

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
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).toList().get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data.get(rowIndex).setState((Boolean) aValue);
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

    public Integer add(Boolean state, Color color) {
        Record record = new Record(state, color);
        if (!data.contains(record)) {
            data.add(record);
            fireTableRowsInserted(data.size(), data.size());
            return data.size();
        }

        return null;
    }

    public void delete(int index) {
        data.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public List<Color> getSelected() {
        List<Color> colors = new ArrayList<>();
        for (Record record : data) {
            if (record.getState()) {
                colors.add(record.getColor());
            }
        }

        return colors;
    }

    public void setSelected(List<Color> colors) {
        for (Record record : data) {
            if (colors.contains(record.getColor())) {
                record.setState(true);
            } else {
                record.setState(false);
            }
        }

        fireTableRowsUpdated(0, data.size());
    }

    public class Record {
        private Boolean state;
        private Color color;

        public Record(Boolean state, Color color) {
            this.state = state;
            this.color = color;
        }

        public Boolean getState() {
            return state;
        }

        public void setState(Boolean state) {
            this.state = state;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public List<Object> toList() {
            List<Object> list = new LinkedList<>();
            list.add(state);
            list.add(color);

            return list;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Record record = (Record) o;

            return color.equals(record.color);

        }

        @Override
        public int hashCode() {
            return color.hashCode();
        }
    }
}
