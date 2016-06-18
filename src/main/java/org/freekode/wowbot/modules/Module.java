package org.freekode.wowbot.modules;

import org.freekode.wowbot.ai.Intelligence;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Module implements PropertyChangeListener {
    private List<ModuleListener> listeners = new ArrayList<>();
    protected Intelligence ai;


    /**
     * calling when we start our ai
     */
    public void startAI() {
        buildAI();

        if (!ai.isDone()) {
            ai.execute();
        }
    }

    /**
     * and stop ai
     */
    public void stopAI() {
        ai.kill();
    }

    /**
     * need to restart the thread after kill
     */
    public abstract void buildAI();

    /**
     * catch what ai send
     */
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        switch (e.getPropertyName()) {
            case "progress":
                progress(e);
                break;
            case "custom":
                customProperty(e);
                break;
            case "state":
                switch ((SwingWorker.StateValue) e.getNewValue()) {
                    case STARTED:
                        started(e);
                        break;
                    case DONE:
                        done(e);
                        break;
                    case PENDING:
                        pending(e);
                        break;
                }
                break;
        }
    }

    public void progress(PropertyChangeEvent e) {
        fireProgress(e);
    }

    public void customProperty(PropertyChangeEvent e) {
    }

    public void started(PropertyChangeEvent e) {
        fireStarted(e);
    }

    public void pending(PropertyChangeEvent e) {
        firePending(e);
    }

    public void done(PropertyChangeEvent e) {
        fireDone(e);
    }

    public void addUpdateListener(ModuleListener l) {
        listeners.add(l);
    }

    public void removeUpdateListener(ModuleListener l) {
        Iterator<ModuleListener> iterator = listeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(l)) {
                iterator.remove();
            }
        }
    }

    public void fireProgress(Object data) {
        for (ModuleListener listener : listeners) {
            listener.progress(data);
        }
    }

    public void fireStarted(Object data) {
        for (ModuleListener listener : listeners) {
            listener.started(data);
        }
    }

    public void fireDone(Object data) {
        for (ModuleListener listener : listeners) {
            listener.done(data);
        }
    }

    public void fireCustom(Object data, String command) {
        for (ModuleListener listener : listeners) {
            listener.custom(data, command);
        }
    }

    public void firePending(Object data) {
        for (ModuleListener listener : listeners) {
            listener.pending(data);
        }
    }

    /**
     * interface for module
     */
    public abstract Component getUI();

    /**
     * module name
     */
    public abstract String getName();
}
