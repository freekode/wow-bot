package org.freekode.wowbot.tools;

public class ConfigKeys {
    public static final int ADDON_OFFSET_X = 0;
    public static final int ADDON_OFFSET_Y = 0;
    public static final int ADDON_SIDE_PX = 10;
    public static final int ADDON_COLUMNS = 4;
    public static final int ADDON_ROWS = 4;

    public static final int RECEIVER_UPDATE_INTERVAL_MS = 100;

    /**
     * how many ms need to run approximately 0.1 distance
     */
    public static final int RUN_POINT_ONE = 357;
    /**
     * how many ms need to change yaw using keyboard
     */
    public static final int KEY_YAW_DOUBLE_O_ONE = 3;
    /**
     * how many px need to change yaw to 0.02 rad by mouse
     */
    public static final int MOUSE_YAW_DOUBLE_O_TWO = 5;
    /**
     * how many px need to change pitch to 0.02 rad by mouse
     */
    public static final int MOUSE_PITCH_DOUBLE_O_TWO = 5;

    public static final double STANDARD_PITCH = -0.25;
    public static final double PITCH_TOLERANCE = 0.02;
    public static final double AZIMUTH_TOLERANCE = 0.02;
    public static final double DISTANCE_TOLERANCE = 0.05;

    public static final String WINDOW_CLASS = "GxWindowClass";

    public static final String WINDOW_NAME = "World of Warcraft";
}
