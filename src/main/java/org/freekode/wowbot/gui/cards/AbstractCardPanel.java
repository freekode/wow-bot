package org.freekode.wowbot.gui.cards;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.freekode.wowbot.gui.UpdateListener;

/**
 * abstract panel for cards, needed just for common methods
 * and supporting update listeners
 */
public abstract class AbstractCardPanel extends JPanel {

	private List<UpdateListener> updateListeners = new ArrayList<>();

	public void addUpdateListener(UpdateListener l) {
		updateListeners.add(l);
	}

	public void fireUpdate(Object data, String command) {
		for (UpdateListener listener : updateListeners) {
			listener.updated(data, command);
		}
	}
}
