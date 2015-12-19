package org.freekode.wowbot.beans.interfaces;

public abstract class Controller {
    /**
     * to control the character
     */
    private Driver driver;

    /**
     * to receive the information
     */
    private Receiver receiver;


    public Controller(Driver driver, Receiver receiver) {
        this.driver = driver;
        this.receiver = receiver;
    }

    public Driver getDriver() {
        return driver;
    }

    public Receiver getReceiver() {
        return receiver;
    }
}
