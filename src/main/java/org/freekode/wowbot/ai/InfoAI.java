package org.freekode.wowbot.ai;

import org.freekode.wowbot.entity.info.InfoUpdateEntity;

public class InfoAI extends Intelligence<InfoUpdateEntity> {
    public static final int UPDATE_INTERVAL = 200;

    @Override
    public Boolean processing() throws InterruptedException {
        while (true) {
            Double x = getController().getReceiver().getX();
            Double y = getController().getReceiver().getY();
            Double azimuth = getController().getReceiver().getAzimuth();
            Double pitch = getController().getReceiver().getPitch();
            Boolean inCombat = getController().getReceiver().isInCombat();
            Boolean ore = getController().getReceiver().isOre();
            Boolean herb = getController().getReceiver().isHerb();
            Boolean bagUpdate = getController().getReceiver().bagUpdated();
            Boolean hasTarget = getController().getReceiver().hasTarget();
            Boolean inActionRange = getController().getReceiver().isInActionRange();

            send(new InfoUpdateEntity(x, y, azimuth, pitch, inCombat, ore, herb, bagUpdate, hasTarget, inActionRange));

            Thread.sleep(UPDATE_INTERVAL);
        }
    }
}
