package org.freekode.wowbot.beans.ai;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.modules.moving.CharacterRecord;

import java.awt.*;
import java.util.Date;

public class RecordingAI extends Intelligence<CharacterRecord> implements HotkeyListener {
    public static final int HOT_KEY_IDENTIFIER = 100;
    private static final Logger logger = LogManager.getLogger(RecordingAI.class);

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
        Double x = getController().getReceiver().getX();
        Double y = getController().getReceiver().getY();

        CharacterRecord record = new CharacterRecord(new Date(), new Vector3D(x, y, 0));

        send(record);
    }
}
