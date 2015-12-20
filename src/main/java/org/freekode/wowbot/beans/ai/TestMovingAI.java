package org.freekode.wowbot.beans.ai;

public class TestMovingAI extends Intelligence<String> {
    @Override
    public Boolean processing() {
        return true;
    }


    public void setAzimuth(Double value) {
        getController().azimuth(value);
    }
}
