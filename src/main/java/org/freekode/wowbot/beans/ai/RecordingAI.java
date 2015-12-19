package org.freekode.wowbot.beans.ai;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class RecordingAI extends Intelligence<Void> implements HotkeyListener {
    private static final Logger logger = LogManager.getLogger(RecordingAI.class);
    private DefaultListModel<String> listModelUi;


    public RecordingAI(DefaultListModel<String> listModelUi) {
        this.listModelUi = listModelUi;
    }

    @Override
    public Boolean processing() throws InterruptedException {
        logger.info("start recording");
//        JIntellitype.getInstance();
//        JIntellitype.getInstance().registerSwingHotKey(100, Event.CTRL_MASK + Event.ALT_MASK, (int) 'R');
//        JIntellitype.getInstance().addHotKeyListener(this);

//        Thread.currentThread().wait();
        return true;
    }

    @Override
    public void onHotKey(int i) {
        if (i == 100) {
            addRecord();
        }
    }

    public void addRecord() {
        String record = getController().getCoordinates().getX() + "; " + getController().getCoordinates().getY();
        listModelUi.addElement(record);
    }
}
