package org.freekode.wowbot.gui.renderers;

import javax.swing.table.DefaultTableCellRenderer;

public class DoubleRenderer extends DefaultTableCellRenderer {
    public DoubleRenderer() {
        super();
    }

    @Override
    protected void setValue(Object value) {
        setText(value.toString());
    }
}
