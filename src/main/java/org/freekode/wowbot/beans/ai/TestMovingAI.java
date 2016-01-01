package org.freekode.wowbot.beans.ai;

import org.freekode.wowbot.tools.ConfigKeys;
import org.freekode.wowbot.tools.StaticFunc;

public class TestMovingAI extends Intelligence<String> {
    @Override
    public Boolean processing() {
        return true;
    }

    public void setAzimuth(Double value) throws InterruptedException {
        StaticFunc.upWindow(ConfigKeys.WINDOW_CLASS, ConfigKeys.WINDOW_NAME);
        getController().azimuth(value);
    }

    public void setAzimuthByKey(Double value) throws InterruptedException {
        StaticFunc.upWindow(ConfigKeys.WINDOW_CLASS, ConfigKeys.WINDOW_NAME);
        getController().azimuthByKey(value);
    }

    public void setPitch(Double value) {
        StaticFunc.upWindow(ConfigKeys.WINDOW_CLASS, ConfigKeys.WINDOW_NAME);
        getController().pitch(value);
    }

    public void run(Double value) throws InterruptedException {
        StaticFunc.upWindow(ConfigKeys.WINDOW_CLASS, ConfigKeys.WINDOW_NAME);
        getController().run(value);
    }

    public void gatherHerb() throws InterruptedException {
        StaticFunc.upWindow(ConfigKeys.WINDOW_CLASS, ConfigKeys.WINDOW_NAME);
        getController().gatherSecond();
    }
}
