package org.freekode.wowbot.beans.ai;

public class InfoAI extends Intelligence<InfoAI.InfoUpdate> {
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

            send(new InfoUpdate(x, y, azimuth, pitch, inCombat, ore, herb, bagUpdate));

            Thread.sleep(200);
        }
    }

    public class InfoUpdate {
        private Double x;
        private Double y;
        private Double azimuth;
        private Double pitch;
        private Boolean inCombat;
        private Boolean ore;
        private Boolean herb;
        private Boolean bagUpdate;


        public InfoUpdate(Double x, Double y, Double azimuth, Double pitch, Boolean inCombat, Boolean ore,
                          Boolean herb, Boolean bagUpdate) {
            this.x = x;
            this.y = y;
            this.azimuth = azimuth;
            this.pitch = pitch;
            this.inCombat = inCombat;
            this.ore = ore;
            this.herb = herb;
            this.bagUpdate = bagUpdate;
        }

        public Double getX() {
            return x;
        }

        public Double getY() {
            return y;
        }

        public Double getAzimuth() {
            return azimuth;
        }

        public Double getPitch() {
            return pitch;
        }

        public Boolean getInCombat() {
            return inCombat;
        }

        public Boolean getOre() {
            return ore;
        }

        public Boolean getHerb() {
            return herb;
        }

        public Boolean getBagUpdate() {
            return bagUpdate;
        }
    }
}
