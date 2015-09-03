package org.freekode.wowbot.tools;

import com.sun.istack.internal.Nullable;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class StaticFunctions {
    public static double getAzimuth(Vector3D a, Vector3D b) {
        double bCt = (b.getY() - a.getY()) * -1;
        double cCt = Vector3D.distance(a, b);
        double rad = Math.acos(bCt / cCt);

        double azimuth;
        if (b.getX() > a.getX()) {
            azimuth = Math.PI * 2 - rad;
        } else {
            azimuth = rad;
        }

        return azimuth;
    }

    public static WinUser.WINDOWINFO upWindow(String windowClass, String windowName) {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(windowClass, windowName);

        WinUser.WINDOWINFO info = new WinUser.WINDOWINFO();
        User32.INSTANCE.GetWindowInfo(hwnd, info);

        if (hwnd != null) {
            User32.INSTANCE.ShowWindow(hwnd, 9);
            User32.INSTANCE.SetForegroundWindow(hwnd);
            return info;
        } else {
            return null;
        }
    }
}
