package org.freekode.wowbot.tools;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class StaticFunc {
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

    public static WinUser.WINDOWINFO upWindow(String windowClass, String windowName) throws Exception {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(windowClass, windowName);

        if (hwnd != null) {
            WinUser.WINDOWINFO info = new WinUser.WINDOWINFO();
            User32.INSTANCE.GetWindowInfo(hwnd, info);
            User32.INSTANCE.ShowWindow(hwnd, 9);
            User32.INSTANCE.SetForegroundWindow(hwnd);
            return info;
        } else {
            throw new Exception("there is no window " + windowClass + ":" + windowName);
        }
    }

    public static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {
        if (image.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            BufferedImage convertedImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            convertedImg.getGraphics().drawImage(image, 0, 0, null);
            image = convertedImg;
        }

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }

    public static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = image.getRGB(col, row);
            }
        }

        return result;
    }

    public static boolean isSimilarColor(Color first, Color second, double similarity) {
        Vector3D firstVector = new Vector3D(first.getRed(), first.getGreen(), first.getBlue());
        Vector3D secondVector = new Vector3D(second.getRed(), second.getGreen(), second.getBlue());

        double distance = Vector3D.distance(firstVector, secondVector);
        return distance <= similarity;
    }

    public static Rectangle calculateCutSquare(Rectangle main, Rectangle sub) {
        int startX = (int) (main.getX() + sub.getX());
        int startY = (int) (main.getY() + sub.getY());
        int width = (int) sub.getWidth();
        int height = (int) sub.getHeight();

        return new Rectangle(startX, startY, width, height);
    }

    public static int[] findColor(int[][] pixels, Color[] colors, double similarity) {
//        int[][] pixels = convertTo2DWithoutUsingGetRGB(image);

        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[y].length; x++) {
                Color imageColor = new Color(pixels[y][x]);
                for (Color color : colors) {
                    if (StaticFunc.isSimilarColor(imageColor, color, similarity)) {
                        return new int[]{x, y, color.getRGB()};
                    }
                }
            }
        }

        return null;
    }

    public static BufferedImage cutImage(Rectangle rectangle, boolean writeImage, String fileName) {
        try {
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(rectangle);

            if (writeImage) {
                File file = new File("images/" + fileName + ".png");
                ImageIO.write(image, "png", file);
            }

            return image;
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
