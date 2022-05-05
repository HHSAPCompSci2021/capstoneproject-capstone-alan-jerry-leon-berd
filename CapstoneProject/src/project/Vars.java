package project;

import project.core.*;

import static gameutils.util.Mathf.*;

public class Vars{
    public static boolean debug = false;
    public static int width = 1200, height = 800;
    public static float screenShake = 1f, shakeDrag = 2.5f;
    public static float universalDrag = 0.02f;
    public static float universalSpeedLimit = 25;
    public static float universalDamping = 0.3f;
    public static float raycastLength = 10;
    public static float maxEntitySize = 50;
    public static float maxLineLen = rt2(width * width + height * height);
    public static float expRange = 200;
    public static float expLifetime = 60 * 10;
    public static float expDrag = 0.05f;
    public static float baseLevelExp = 250;
    public static float expScaling = 1.1f;
    public static Canvas canvas;

    public static void init(){
        canvas = new Canvas();

        canvas.init();
    }
}
