package org.freekode.wowbot.modules;

import org.freekode.wowbot.ai.Intelligence;
import org.freekode.wowbot.ui.UpdateListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Module implements PropertyChangeListener {
    private List<UpdateListener> listeners = new ArrayList<>();
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
            default:
                property(e);
        }
    }

    public void progress(PropertyChangeEvent e) {
        fireUpdate(e, "progress");
    }

    public void property(PropertyChangeEvent e) {
    }

    public void started(PropertyChangeEvent e) {
        fireUpdate(e, "started");
    }

    public void pending(PropertyChangeEvent e) {
        fireUpdate(e, "pending");
    }

    public void done(PropertyChangeEvent e) {
        fireUpdate(e, "done");
    }

    public void addUpdateListener(UpdateListener l) {
        listeners.add(l);
    }

    public void removeUpdateListener(UpdateListener l) {
        Iterator<UpdateListener> iterator = listeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(l)) {
                iterator.remove();
            }
        }
    }

    public void fireUpdate(Object data, String command) {
        for (UpdateListener listener : listeners) {
            listener.updated(data, command);
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
