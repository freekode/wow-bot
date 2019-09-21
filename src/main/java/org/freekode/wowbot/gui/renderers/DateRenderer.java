package org.freekode.wowbot.gui.renderers;

import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableCellRenderer;

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
