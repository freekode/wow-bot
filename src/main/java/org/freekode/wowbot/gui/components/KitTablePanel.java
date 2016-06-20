package org.freekode.wowbot.gui.components;

import org.freekode.wowbot.entity.fishing.FishingKitEntity;
import org.freekode.wowbot.gui.models.KitTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * table for kits
 */
public class KitTablePanel extends JPanel implements ActionListener {
    private JTable table;


    public KitTablePanel(String title, List<FishingKitEntity> kits) {
        init(title, kits);
    }

    public void init(String title, List<FishingKitEntity> kits) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBorder(BorderFactory.createTitledBorder(title));


        KitTableModel model = new KitTableModel();
        for (FishingKitEntity elem : kits) {
            model.add(elem);
        }
        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                KitTableModel model = (KitTableModel) table.getModel();
                int index = table.getSelectedRow();
                if (index > -1) {
                    FishingKitEntity kit = model.getData().get(index);
//                    firstColorTablePanel.setSelectedColors(kit.getFirstColors());
//                    secondColorTablePanel.setSelectedColors(kit.getSecondColors());
//                    thirdColorTablePanel.setSelectedColors(kit.getThirdColors());
                }
            }
        });
//        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        table.getColumnModel().getColumn(0).setPreferredWidth(26);
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
//        table.setPreferredScrollableViewportSize(table.getPreferredSize());
//        table.setFillsViewportHeight(true);
        panel.add(table);


        JPanel controlPanel = new JPanel();
        controlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);
        addButton.setActionCommand("addKit");
        controlPanel.add(addButton);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(this);
        editButton.setActionCommand("editKit");
        controlPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("deleteKit");
        controlPanel.add(deleteButton);


        panel.add(controlPanel);
    }

    public JTable getTable() {
        return table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("addKit".equals(e.getActionCommand())) {
            addKit();
        } else if ("deleteKit".equals(e.getActionCommand())) {
            deleteKit();
        } else if ("editKit".equals(e.getActionCommand())) {
            editKit();
        }
    }

    public void addKit() {
        String name = JOptionPane.showInputDialog(this, "Input name", "Add kit", JOptionPane.PLAIN_MESSAGE);
        if (name != null) {
            KitTableModel model = (KitTableModel) table.getModel();
            FishingKitEntity newKit = new FishingKitEntity(name);
            Integer index = model.add(newKit);

//            kitTable.changeSelection(index, 0, false, false);
            table.setRowSelectionInterval(index - 1, index - 1);
            table.scrollRectToVisible(table.getCellRect(index, 0, true));
        }
    }

    public void editKit() {
        KitTableModel model = (KitTableModel) table.getModel();
        int index = table.getSelectedRow();
        if (index > -1) {
            FishingKitEntity kit = model.getData().get(index);
            String name = JOptionPane.showInputDialog(this, "Input name", kit.getName());
            if (name != null) {
                kit.setName(name);
                model.update(kit);
            }
        }
    }

    public void deleteKit() {
        int index = table.getSelectedRow();
        if (index > -1) {
            KitTableModel model = (KitTableModel) table.getModel();
            model.delete(index);
        }
    }
}
