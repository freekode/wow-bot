package org.freekode.wowbot.ui.fishing;

import org.freekode.wowbot.ui.renderers.ColorCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * the table for choosing the colors
 */
public class ColorTable extends JPanel {
    private JTable table;


    public ColorTable(String title, java.util.List<Color> colors) {
        setBorder(BorderFactory.createTitledBorder(title));

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


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
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        add(scrollPane, c);


        ActionColorListener colorListener = new ActionColorListener();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(colorListener);
        addButton.setActionCommand("add");
//            addButton.setPreferredSize(new Dimension(20, 20));
        addButton.setMargin(new Insets(0, 5, 0, 5));
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(addButton, c);

        JButton deleteButton = new JButton("Del");
        deleteButton.addActionListener(colorListener);
        deleteButton.setActionCommand("delete");
//            deleteButton.setPreferredSize(new Dimension(20, 20));
        deleteButton.setMargin(new Insets(0, 5, 0, 5));
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 2;
        add(deleteButton, c);
    }

    public java.util.List<Color> getSelectedColors() {
        ColorTableModel model = (ColorTableModel) table.getModel();
        return model.getSelected();
    }

    public void setSelectedColors(java.util.List<Color> colors) {
        ColorTableModel model = (ColorTableModel) table.getModel();
        model.setSelected(colors);
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
                ColorTableModel model = (ColorTableModel) ColorTable.this.table.getModel();
                model.add(true, newColor);
            }
        }

        public void delete() {
            int index = ColorTable.this.table.getSelectedRow();
            if (index > -1) {
                ColorTableModel model = (ColorTableModel) ColorTable.this.table.getModel();
                model.delete(index);
            }
        }
    }
}
