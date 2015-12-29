package org.freekode.wowbot.beans.ai;

import org.freekode.wowbot.tools.ConfigKeys;
import org.freekode.wowbot.tools.StaticFunc;

public class TestMovingAI extends Intelligence<String> {
    @Override
    public Boolean processing() {
        return true;
    }

    public void setAzimuth(Double value) {
        getController().azimuth(value);
    }

    public void setPitch(Double value) {
        getController().pitch(value);
    }

    public void run(Double value) throws InterruptedException {
        StaticFunc.upWindow(ConfigKeys.WINDOW_CLASS, ConfigKeys.WINDOW_NAME);
        getController().run(value);
    }
}
