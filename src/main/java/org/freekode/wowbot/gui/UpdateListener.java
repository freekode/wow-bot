package org.freekode.wowbot.gui;

import java.util.EventListener;

/**
 * listener for data changes, using anywhere where it is acceptable
 */
public interface UpdateListener extends EventListener {
    /**
     * calling when we need it
     *
     * @param object  some object which we can send
     * @param command command what was
     */
    void updated(Object object, String command);
}
