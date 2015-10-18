package org.freekode.wowbot.beans.ai;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestAI extends Intelligence {
    private static final Logger logger = LogManager.getLogger(TestAI.class);


    @Override
    public void processing() throws InterruptedException {
        while (true) {
            logger.info("test passed");
            Thread.sleep(500);
        }
    }
}