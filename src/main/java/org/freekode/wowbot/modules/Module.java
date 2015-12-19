package org.freekode.wowbot.modules;

import org.freekode.wowbot.beans.ai.IntelligenceOld;

import java.awt.*;

public abstract class Module {
    public abstract Component getUI();

    public abstract IntelligenceOld getAi();
}
