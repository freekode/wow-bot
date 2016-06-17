package org.freekode.wowbot.ui.renderers;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.SimpleDateFormat;

public class DateRenderer extends DefaultTableCellRenderer {
    private SimpleDateFormat formatter;

    public DateRenderer() {
        super();
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    }

    public DateRenderer(String format) {
        super();
        formatter = new SimpleDateFormat(format);
    }

    @Override
    protected void setValue(Object value) {
        setText(formatter.format(value));
    }
}
