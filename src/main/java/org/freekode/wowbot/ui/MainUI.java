package org.freekode.wowbot.ui;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class MainUI implements ActionListener, HotkeyListener, ItemListener {
    private static TestThread aiThread;
    private JFrame frame;
    private JPanel cards;
    private List<Module> modules = new ArrayList<>();


    public void start() {
        SwingUtilities.invokeLater(() -> {
            init();
        });
    }

    public void init() {
        frame = new JFrame();
        frame.setTitle("WoW Bot");
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(100, 100);

        buildInterface(frame);

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
        JComboBox<String> aiSelect = new JComboBox<>();
        for (Module module : modules) {
            aiSelect.addItem(module.getName());
            cards.add(module.getUI(), module.getName());
        }
        aiSelect.addItemListener(this);
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        pane.add(aiSelect, c);

        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 2;
        pane.add(cards, c);
    }

    public void registerHotKeys() {
        JIntellitype.getInstance();
        JIntellitype.getInstance().registerSwingHotKey(1, Event.CTRL_MASK, Event.F10);
        JIntellitype.getInstance().addHotKeyListener(this);
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) e.getItem());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("startThread".equals(e.getActionCommand())) {
            startThread();
        } else if ("stopThread".equals(e.getActionCommand())) {
            stopThread();
        }
    }

    @Override
    public void onHotKey(int i) {

    }

    public void startThread() {
        aiThread = new TestThread();
        aiThread.start();
    }

    public void stopThread() {
        aiThread.interrupt();
    }
}

