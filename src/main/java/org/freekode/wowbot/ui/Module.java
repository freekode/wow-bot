package org.freekode.wowbot.ui;

import org.freekode.wowbot.beans.interfaces.Intelligence;

import java.awt.*;

public abstract class Module {
    private final String name;


    public Module(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Component getUI();

    public abstract Intelligence getAi();
}
