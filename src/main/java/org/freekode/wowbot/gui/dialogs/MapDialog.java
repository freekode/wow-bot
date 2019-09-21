package org.freekode.wowbot.gui.dialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Ellipse2D;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.entity.moving.CharacterUpdateEntity;

public class MapDialog extends JDialog implements ActionListener {

	private static final Logger logger = LogManager.getLogger(MapDialog.class);

	private List<CharacterUpdateEntity> records;

	public void init(List<CharacterUpdateEntity> records) {
		this.records = records;

		setModal(true);
		setTitle("Map");
		setSize(1018, 705);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation(50, 100);

		buildInterface();
		setVisible(true);
	}

	public void buildInterface() {
		Container pane = getContentPane();

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel drawPanel = new DrawPanel();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		pane.add(drawPanel, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("saveOptions".equals(e.getActionCommand())) {
			saveOptions();
			dispose();
		}
	}

	public void saveOptions() {
	}

	public class DrawPanel extends JPanel {

		private Image backgroundImage;

		private double scale = 1;

		private int dragX = 0;

		private int dragY = 0;

		public DrawPanel() {
			new MovingAdapter(this);
			setDoubleBuffered(true);

			try {
				backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("map/elwynn_forest.jpg"));
			} catch (Exception e) {
				logger.info("background exception", e);
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;
			g2d.scale(scale, scale);

			if (backgroundImage != null) {
				int x = (int) (dragX * (1 / scale));
				int y = (int) (dragY * (1 / scale));
				g2d.drawImage(backgroundImage, x, y, this);
			}

			for (CharacterUpdateEntity record : records) {
				Vector3D point = record.getCoordinates();

				if (record.getAction() == CharacterUpdateEntity.Action.MOVE) {
					g2d.setPaint(new Color(255, 0, 0));
				} else if (record.getAction() == CharacterUpdateEntity.Action.GATHER) {
					g2d.setPaint(new Color(0, 255, 0));
				}

				double width = 5 * (1 / scale);
				double height = 5 * (1 / scale);
				double x = (point.getX() / 100.0 * 1002.0) - (width / 2) + (dragX * (1 / scale));
				double y = (point.getY() / 100.0 * 668.0) - (height / 2) + (dragY * (1 / scale));

				Ellipse2D.Double circle = new Ellipse2D.Double(x, y, width, height);
				g2d.fill(circle);

			}
		}

		public class MovingAdapter extends MouseAdapter {

			private int x;

			private int y;

			public MovingAdapter(Component component) {
				component.addMouseWheelListener(this);
				component.addMouseMotionListener(this);
				component.addMouseListener(this);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				int dx = e.getX() - x;
				int dy = e.getY() - y;

				dragX += dx;
				dragY += dy;

				x += dx;
				y += dy;

				repaint();
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
					unitScroll(e);
				}
			}

			public void unitScroll(MouseWheelEvent e) {
				if (e.getWheelRotation() < 0) {
					scale += 0.1;
				} else {
					scale -= 0.1;
				}

				if (scale < 0.1) {
					scale = 0.1;
				}

				repaint();
			}
		}
	}
}

