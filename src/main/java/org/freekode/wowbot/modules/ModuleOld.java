package org.freekode.wowbot.modules;

import org.freekode.wowbot.beans.ai.IntelligenceThread;

import java.awt.*;

public abstract class ModuleOld {
    public abstract Component getUI();

    public abstract IntelligenceThread getAi();
}
