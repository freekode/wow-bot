package org.freekode.wowbot.beans.ai;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestAI extends Intelligence<Void> {
    private static final Logger logger = LogManager.getLogger(TestAI.class);


    @Override
    public Boolean processing() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            setProgress(i);
//            logger.info("test passed");
            Thread.sleep(500);
        }

        return true;
    }
}
