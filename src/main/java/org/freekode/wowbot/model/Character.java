package org.freekode.wowbot.model;

public class Character {
    public static final Character INSTANCE = new Character();

    private Double x;
    private Double y;
    private Double pitch;
    private Double azimyth;


    private Character() {}

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getPitch() {
        return pitch;
    }

    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    public Double getAzimyth() {
        return azimyth;
    }

    public void setAzimyth(Double azimyth) {
        this.azimyth = azimyth;
    }
}
