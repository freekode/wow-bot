package org.freekode.wowbot.ai;

import java.awt.Rectangle;
import javax.swing.SwingWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.controller.Controller;
import org.freekode.wowbot.tools.ConfigKeys;
import org.freekode.wowbot.tools.StaticFunc;

public abstract class Intelligence<T> extends SwingWorker<Boolean, Void> {

	private static final Logger logger = LogManager.getLogger(Intelligence.class);

	/**
	 * when we setting true, we do not need running game
	 */
	private boolean testing = false;

	/**
	 * controller to control the player, and read information
	 */
	private Controller controller;

	public Intelligence() {
	}

	public Intelligence(boolean testing) {
		this.testing = testing;
	}

	@Override
	public Boolean doInBackground() {
		logger.info("start");
		try {
			if (!testing) {
				controller = init(StaticFunc.findWindow(ConfigKeys.WINDOW_CLASS, ConfigKeys.WINDOW_NAME));
			}

			return processing();
		} catch (Exception e) {
			logger.info("Intelligence exception: " + e.getMessage());
			return true;
		}
	}

	public Controller init(Rectangle rectangle) throws InterruptedException {
		return new Controller(rectangle);
	}

	public void send(T object) {
		firePropertyChange("custom", null, object);
	}

	public void send(T object, String command) {
		firePropertyChange(command, null, object);
	}

	public void kill() {
		if (!isDone() || !isCancelled()) {
			logger.info("kill");
			terminating();
			cancel(true);
		}
	}

	public Boolean processing() throws InterruptedException {
		return true;
	}

	public void terminating() {
	}

	public Controller getController() {
		return controller;
	}
}
