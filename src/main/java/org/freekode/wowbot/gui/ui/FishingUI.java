package org.freekode.wowbot.gui.ui;

import org.freekode.wowbot.entity.fishing.FishingRecordEntity;
import org.freekode.wowbot.gui.UpdateListener;
import org.freekode.wowbot.gui.models.FishingTableModel;
import org.freekode.wowbot.gui.renderers.ColorCellRenderer;
import org.freekode.wowbot.gui.renderers.DateRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ui for fishing module
 */
public class FishingUI extends JPanel implements ActionListener {
    private List<UpdateListener> updateListeners = new ArrayList<>();
    private Integer bobberThrows = 0;
    private Integer catches = 0;
    private Integer fails = 0;
    private JLabel statusLabel;
    private JTable recordsTable;


    public FishingUI() {
        init();
    }

    public void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // row 0
        JButton optionsButton = new JButton("Options");
        optionsButton.addActionListener(this);
        optionsButton.setActionCommand("showOptions");
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.LINE_END;
        add(optionsButton, c);


        // row 1
        JLabel statusTitleLabel = new JLabel("Throws/Catches/Fails");
        statusTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.LINE_END;
        add(statusTitleLabel, c);

        statusLabel = new JLabel();
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        add(statusLabel, c);
        updateStatus(0, 0, 0);


        // row 3
        recordsTable = new JTable(new FishingTableModel());
        recordsTable.setDefaultRenderer(Date.class, new DateRenderer("yyyy-MM-dd HH:mm:ss"));
        recordsTable.setDefaultRenderer(Color.class, new ColorCellRenderer());
        recordsTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        recordsTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        recordsTable.getColumnModel().getColumn(2).setPreferredWidth(30);
        recordsTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(recordsTable), c);
    }

    private void updateStatus(Integer bobberThrows, Integer catches, Integer fails) {
        statusLabel.setText(bobberThrows.toString() + "/" + catches.toString() + "/" + fails.toString());
    }

    public void updateRecordsTable(FishingRecordEntity record) {
        FishingTableModel model = (FishingTableModel) recordsTable.getModel();

        bobberThrows++;
        if (record.getCaught() != null) {
            if (record.getCaught()) {
                catches++;
            } else {
                fails++;
            }
        }
        updateStatus(bobberThrows, catches, fails);


        Integer index = model.updateOrAdd(record);
        recordsTable.scrollRectToVisible(recordsTable.getCellRect(index, 0, true));
    }

    public void addUpdateListener(UpdateListener l) {
        updateListeners.add(l);
    }

    public void fireUpdate(Object data, String command) {
        for (UpdateListener listener : updateListeners) {
            listener.updated(data, command);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireUpdate(null, "showOptions");
    }
}
