package org.freekode.wowbot.modules.moving;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.SimpleDateFormat;

public class DateRenderer extends DefaultTableCellRenderer {
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    public DateRenderer() {
        super();
    }

    @Override
    protected void setValue(Object value) {
        setText(formatter.format(value));
    }
}
