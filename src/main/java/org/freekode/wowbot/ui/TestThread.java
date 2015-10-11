package org.freekode.wowbot.ui;

public class TestThread extends Thread {
    @Override
    public void run() {
        System.out.println("start");
        while (true) {
            System.out.println("go");

            if (Thread.currentThread().isInterrupted()) {
                System.out.println("stop");
                break;
            }
        }
    }
}
