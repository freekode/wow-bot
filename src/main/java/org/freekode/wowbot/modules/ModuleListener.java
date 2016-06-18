package org.freekode.wowbot.modules;

/**
 * listener specially for ai
 */
public interface ModuleListener {
    void progress(Object object);

    void started(Object object);

    void done(Object object);

    void custom(Object object, String command);

    void pending(Object object);
}
