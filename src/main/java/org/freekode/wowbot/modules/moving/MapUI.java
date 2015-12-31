package org.freekode.wowbot.modules.moving;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class MapUI extends JFrame implements ActionListener {
    private static final Logger logger = LogManager.getLogger(MapUI.class);
    private List<CharacterRecordModel> records;


    public void init(List<CharacterRecordModel> records) {
        this.records = records;

        setTitle("Map");
        setSize(1018, 705);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(50, 100);

        buildInterface();

        setVisible(true);
    }

    public void buildInterface() {
        Container pane = getContentPane();

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel panel = new DrawPanel();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        pane.add(panel, c);
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
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            try {
                Image backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("map/elwynn_forest.jpg"));
                g2d.drawImage(backgroundImage, 0, 0, this);
            } catch (Exception e) {
                logger.info("background exception", e);
            }

            for (CharacterRecordModel record : records) {
                Vector3D point = record.getCoordinates();

                if (record.getAction() == CharacterRecordModel.Action.MOVE) {
                    g2d.setPaint(new Color(0, 255, 255));
                } else if (record.getAction() == CharacterRecordModel.Action.MOVE) {
                    g2d.setPaint(new Color(255, 255, 0));
                }

                double x = point.getX() / 100.0 * 1002.0;
                double y = point.getY() / 100.0 * 668.0;

                Ellipse2D.Double circle = new Ellipse2D.Double(x - 2.5, y - 2.5, 5, 5);
                g2d.fill(circle);
            }
        }
    }
}

