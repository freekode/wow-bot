package org.freekode.wowbot.tools;

public class ConfigKeys {
    public static final String YAML_CONFIG_FILENAME = "wowbot.yaml";

    public static final int ADDON_OFFSET_X = 0;
    public static final int ADDON_OFFSET_Y = 0;
    public static final int ADDON_SIDE_PX = 10;
    public static final int ADDON_COLUMNS = 4;
    public static final int ADDON_ROWS = 4;

    public static final int RECEIVER_UPDATE_INTERVAL_MS = 10;

    /**
     * how many ms need to run approximately 0.001 distance
     */
    public static final int RUN_DOUBLE_O_ONE = 3;

    /**
     * ms to change azimuth by 0.001
     */
    public static final int KEY_YAW_DOUBLE_O_ONE = 3;

    /**
     * how many px need to change yaw to 0.005 rad by mouse
     */
    public static final int MOUSE_YAW_DOUBLE_O_ONE = 1;

    /**
     * how many px need to change pitch to 0.01 rad by mouse
     */
    public static final int MOUSE_PITCH_DOUBLE_O_TWO = 3;

    /**
     * set this pitch for gathering
     */
    public static final double GATHER_PITCH = -0.5;

    public static final double STANDARD_PITCH = -0.25;
    public static final double PITCH_TOLERANCE = 0.01;
    public static final double AZIMUTH_TOLERANCE = 0.005;
    public static final double AZIMUTH_KEY_TOLERANCE = 0.13;
    public static final double DISTANCE_TOLERANCE = 0.075;

    public static final String WINDOW_CLASS = "GxWindowClass";
    public static final String WINDOW_NAME = "World of Warcraft";

    /**
     * delay for driver
     */
    public static final int AUTO_DELAY_MS = 40;
}
