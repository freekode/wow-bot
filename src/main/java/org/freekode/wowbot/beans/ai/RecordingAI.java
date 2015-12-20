package org.freekode.wowbot.beans.ai;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class RecordingAI extends Intelligence<String> implements HotkeyListener {
    private static final Logger logger = LogManager.getLogger(RecordingAI.class);
    public static final int HOT_KEY_IDENTIFIER = 100;


    @Override
    public Boolean processing() throws InterruptedException {
        logger.info("start recording");
        JIntellitype.getInstance();
        JIntellitype.getInstance().registerSwingHotKey(HOT_KEY_IDENTIFIER, Event.CTRL_MASK + Event.ALT_MASK, (int) 'R');
        JIntellitype.getInstance().addHotKeyListener(this);

        return true;
    }

    @Override
    public void terminating() {
        logger.info("stop recording");
        JIntellitype.getInstance().unregisterHotKey(HOT_KEY_IDENTIFIER);
        JIntellitype.getInstance().removeHotKeyListener(this);
    }

    @Override
    public void onHotKey(int i) {
        if (i == HOT_KEY_IDENTIFIER) {
            addRecord();
        }
    }

    public void addRecord() {
        String record = getController().getCoordinates().getX() + "; " + getController().getCoordinates().getY();
        send(record);
    }
}
