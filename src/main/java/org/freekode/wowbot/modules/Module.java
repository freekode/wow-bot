package org.freekode.wowbot.modules;

import org.freekode.wowbot.ai.Intelligence;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class Module implements PropertyChangeListener {
    public abstract void buildAI();

    public abstract Component getUI();

    public abstract Intelligence getAI();

    public abstract String getName();

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        switch (e.getPropertyName()) {
            case "progress":
                progress(e);
                break;
            case "custom":
                property(e);
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
    }

    public void property(PropertyChangeEvent e) {
    }

    public void started(PropertyChangeEvent e) {
    }

    public void pending(PropertyChangeEvent e) {
    }

    public void done(PropertyChangeEvent e) {
    }
}
