package org.freekode.wowbot.beans.ai;

public class TestAI extends Intelligence<String> {
    @Override
    public Boolean processing() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            publish("k");
            setProgress(i);
            Thread.sleep(500);
        }

        return true;
    }
}
