package org.freekode.wowbot.ai;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import com.melloware.jintellitype.JIntellitypeException;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freekode.wowbot.entity.moving.CharacterUpdateEntity;

import java.awt.*;
import java.util.Date;

public class RecordingAI extends Intelligence<CharacterUpdateEntity> implements HotkeyListener {
    private static final Logger logger = LogManager.getLogger(RecordingAI.class);
    private static final int HOT_KEY_IDENTIFIER_MOVE = 100;
    private static final int HOT_KEY_IDENTIFIER_GATHER = 101;
    private Vector3D prevPoint = Vector3D.ZERO;


    @Override
    public Boolean processing() throws InterruptedException {
        logger.info("start recording");
        try {
            JIntellitype.getInstance();
            JIntellitype.getInstance().registerSwingHotKey(HOT_KEY_IDENTIFIER_MOVE, Event.CTRL_MASK + Event.ALT_MASK, (int) '1');
            JIntellitype.getInstance().registerSwingHotKey(HOT_KEY_IDENTIFIER_GATHER, Event.CTRL_MASK + Event.ALT_MASK, (int) '2');
            JIntellitype.getInstance().addHotKeyListener(this);
        } catch (JIntellitypeException e) {
            logger.error("JIntellitype error", e);
        }

        while (true) {
            addMoveRecord();
            Thread.sleep(1000);
        }

//        return true;
    }

    @Override
    public void terminating() {
        logger.info("stop recording");
        try {
            JIntellitype.getInstance().unregisterHotKey(HOT_KEY_IDENTIFIER_MOVE);
            JIntellitype.getInstance().unregisterHotKey(HOT_KEY_IDENTIFIER_GATHER);
            JIntellitype.getInstance().removeHotKeyListener(this);
        } catch (JIntellitypeException e) {
            logger.error("JIntellitype error", e);
        }
    }

    @Override
    public void onHotKey(int i) {
        if (i == HOT_KEY_IDENTIFIER_MOVE) {
            addMoveRecord();
        } else if (i == HOT_KEY_IDENTIFIER_GATHER) {
            addGatherRecord();
        }
    }

    public void addMoveRecord() {
        Double x = getController().getReceiver().getX();
        Double y = getController().getReceiver().getY();
        Vector3D newPoint = new Vector3D(x, y, 0);

        if (newPoint.equals(prevPoint)) {
            return;
        }

        prevPoint = newPoint;
        CharacterUpdateEntity record = new CharacterUpdateEntity(
                new Date(), newPoint, CharacterUpdateEntity.Action.MOVE);

        send(record);
    }

    public void addGatherRecord() {
        Double x = getController().getReceiver().getX();
        Double y = getController().getReceiver().getY();

        CharacterUpdateEntity record = new CharacterUpdateEntity(
                new Date(), new Vector3D(x, y, 0), CharacterUpdateEntity.Action.GATHER);

        send(record);
    }
}
