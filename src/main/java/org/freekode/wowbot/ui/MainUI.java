package org.freekode.wowbot.ui;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import com.melloware.jintellitype.JIntellitypeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.ai.Intelligence;
import org.freekode.wowbot.modules.InfoModule;
import org.freekode.wowbot.modules.Module;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

public class MainUI implements ActionListener, HotkeyListener, ItemListener {
    private static final Logger logger = LogManager.getLogger(MainUI.class);
    private InfoModule infoModule;
    private Module currentModule;
    private StatusBar statusBar;
    private JComboBox<String> aiSelect;
    private JPanel cards;
    private Map<String, Module> modules = new HashMap<>();


    public MainUI(boolean hotKey) {
        if (hotKey) {
            registerHotKeys();
        }
    }

    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    public void init() {
        JFrame frame = new JFrame();
        frame.setTitle("linux");
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(0, 0);

        buildInterface(frame);

//        frame.pack();
        frame.setVisible(true);

        statusBar.setText("initialized");
    }

    public void buildInterface(JFrame frame) {
        Container pane = frame.getContentPane();

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        infoModule = new InfoModule();
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        pane.add(infoModule.getUI(), c);


        JButton startButton = new JButton("Start process");
        startButton.setActionCommand("startThread");
        startButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 10, 10, 5);
        c.gridx = 0;
        c.gridy = 1;
        pane.add(startButton, c);

        JButton stopButton = new JButton("Stop process");
        stopButton.setActionCommand("stopThread");
        stopButton.addActionListener(this);
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(10, 5, 10, 10);
        c.gridx = 1;
        c.gridy = 1;
        pane.add(stopButton, c);


        cards = new JPanel(new CardLayout());
        cards.setBorder(BorderFactory.createEtchedBorder());

        aiSelect = new JComboBox<>();
        aiSelect.addItemListener(this);
        for (Map.Entry<String, Module> entry : modules.entrySet()) {
            aiSelect.addItem(entry.getKey());
            cards.add(entry.getValue().getUI(), entry.getKey());
        }
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        pane.add(aiSelect, c);

        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        pane.add(cards, c);


        statusBar = new StatusBar();
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.weighty = 0;
        pane.add(statusBar, c);
    }

    public void registerHotKeys() {
        logger.info("register hotkeys");

        try {
            JIntellitype.setLibraryLocation("JIntellitype.dll");
            JIntellitype.getInstance();
            JIntellitype.getInstance().registerSwingHotKey(1, Event.CTRL_MASK + Event.ALT_MASK, (int) 'G');
            JIntellitype.getInstance().registerSwingHotKey(2, Event.CTRL_MASK + Event.ALT_MASK, (int) 'K');
            JIntellitype.getInstance().addHotKeyListener(this);
        } catch (JIntellitypeException e) {
            logger.error("JIntellitype error", e);
        }
    }

    public void addModule(Module module) {
        modules.put(module.getName(), module);
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
        currentModule.buildAI();
        infoModule.buildAI();
        Intelligence ai = currentModule.getAI();
        Intelligence infoAi = infoModule.getAI();

        if (!infoAi.isDone()) {
            infoAi.execute();
        }

        if (!ai.isDone()) {
            ai.execute();

//            setEnableComponents(cards, false);
            aiSelect.setEnabled(false);
            statusBar.setText(currentModule.getName() + " - started");
        } else {
            statusBar.setText(currentModule.getName() + " - not started");
        }
    }

    public void stopThread() {
        statusBar.setText(currentModule.getName() + " - stop");

        Intelligence ai = currentModule.getAI();
        Intelligence infoAi = infoModule.getAI();

        infoAi.kill();
        ai.kill();

//        setEnableComponents(cards, true);
        aiSelect.setEnabled(true);
    }

    public void setEnableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);

            if (component instanceof Container) {
                component.setEnabled(enable);
                setEnableComponents((Container) component, enable);
            }
        }
    }
}

