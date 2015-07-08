package org.freekode.wowbot;


import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {
    public static final String WINDOW_CLASS = "Photo_Lightweight_Viewer";
    public static final String WINDOW_NAME = "World of Warcraft";


    public static void main(String[] args) {
//        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(WINDOW_CLASS, WINDOW_NAME);
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(WINDOW_CLASS, null);

        WinUser.WINDOWINFO info = new WinUser.WINDOWINFO();
        User32.INSTANCE.GetWindowInfo(hwnd, info);



//        System.out.println("info = " + info);
//        System.out.println("info client = " + info.rcClient);
//        System.out.println("info window  = " + info.rcWindow);

//
//        if (hwnd != null) {
//            User32.INSTANCE.ShowWindow(hwnd, 9);
//            User32.INSTANCE.SetForegroundWindow(hwnd);
//        }

        try {
            Robot robot = new Robot();
            robot.mouseMove(info.rcClient.left + 10, info.rcClient.top + 45);

//            Rectangle rect = new Rectangle(0, 0, 500, 500);
//            BufferedImage image = robot.createScreenCapture(rect);
//            Color color = robot.getPixelColor(50, 50);
//            System.out.println("color = " + color.getBlue());
//
//            System.out.println("rgb = " + image.getRGB(50, 50));
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
