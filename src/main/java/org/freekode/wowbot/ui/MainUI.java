package org.freekode.wowbot.ui;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.beans.ai.Intelligence;
import org.freekode.wowbot.modules.Module;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

public class MainUI implements ActionListener, HotkeyListener, ItemListener {
    private static final Logger logger = LogManager.getLogger(MainUI.class);
    private Module currentModule;
    private Intelligence aiThread;
    private StatusBar statusBar;
    private JPanel cards;
    private HashMap<String, Module> modules = new HashMap<>();


    public void start() {
        SwingUtilities.invokeLater(this::init);
    }

    public void init() {
        JFrame frame = new JFrame();
        frame.setTitle("WoW Bot");
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(100, 100);

        buildInterface(frame);
        registerHotKeys();

        frame.pack();
        frame.setVisible(true);
    }

    public void buildInterface(JFrame frame) {
        Container pane = frame.getContentPane();

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton startButton = new JButton("Start process");
        startButton.setActionCommand("startThread");
        startButton.addActionListener(this);

        JButton pauseButton = new JButton("Pause process");
        pauseButton.setActionCommand("pauseThread");
        pauseButton.setVisible(false);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 10, 10, 5);
        c.gridx = 0;
        c.gridy = 0;
        pane.add(startButton, c);
        pane.add(pauseButton, c);

        JButton stopButton = new JButton("Stop process");
        stopButton.setActionCommand("stopThread");
        stopButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(10, 5, 10, 10);
        c.gridx = 1;
        c.gridy = 0;
        pane.add(stopButton, c);


        cards = new JPanel(new CardLayout());
//        cards.setBorder(new BevelBorder(BevelBorder.LOWERED));

        JComboBox<String> aiSelect = new JComboBox<>();
        aiSelect.addItemListener(this);
        for (Map.Entry<String, Module> entry : modules.entrySet()) {
            aiSelect.addItem(entry.getKey());
            cards.add(entry.getValue().getUI(), entry.getKey());
        }
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        pane.add(aiSelect, c);

        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        pane.add(cards, c);


        statusBar = new StatusBar();
        statusBar.setText("started");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.weighty = 0;
        pane.add(statusBar, c);
    }

    public void registerHotKeys() {
        JIntellitype.getInstance();
        JIntellitype.getInstance().registerSwingHotKey(1, Event.CTRL_MASK + Event.ALT_MASK, (int) 'G');
        JIntellitype.getInstance().registerSwingHotKey(2, Event.CTRL_MASK + Event.ALT_MASK, (int) 'K');
        JIntellitype.getInstance().addHotKeyListener(this);
    }

    public void addModule(String name, Module module) {
        modules.put(name, module);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, (String) e.getItem());
            currentModule = modules.get(e.getItem());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("startThread".equals(e.getActionCommand())) {
            startThread();
        } else if ("pauseThread".equals(e.getActionCommand())) {
            pauseThread();
        } else if ("stopThread".equals(e.getActionCommand())) {
            stopThread();
        }
    }

    @Override
    public void onHotKey(int i) {
        if (i == 1) {
            startThread();
        } else if (i == 2) {
            stopThread();
        }
    }

    public void startThread() {
        aiThread = currentModule.getAi();

        if (!aiThread.isAlive()) {
            statusBar.setText("thread = " + aiThread);
            aiThread.start();
        }
    }

    public void pauseThread() {
        if (!aiThread.isAlive()) {
            try {
                aiThread.wait();
            } catch (InterruptedException e) {
                logger.info(e);
            }
        }
    }

    public void stopThread() {
        aiThread.kill();
        statusBar.setText("thread alive = " + aiThread.isAlive());
    }
}

