package org.freekode.wowbot.beans.ai;

public class InfoAI extends Intelligence<InfoAI.InfoUpdate> {
    @Override
    public Boolean processing() throws InterruptedException {
        while (true) {
            Double x = getController().getReceiver().getX();
            Double y = getController().getReceiver().getY();
            Double azimuth = getController().getReceiver().getAzimuth();
            Double pitch = getController().getReceiver().getPitch();
            send(new InfoUpdate(x, y, azimuth, pitch));

            Thread.sleep(200);
        }
    }

    public class InfoUpdate {
        private Double x;
        private Double y;
        private Double azimuth;
        private Double pitch;

        public InfoUpdate(Double x, Double y, Double azimuth, Double pitch) {
            this.x = x;
            this.y = y;
            this.azimuth = azimuth;
            this.pitch = pitch;
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
    }
}
