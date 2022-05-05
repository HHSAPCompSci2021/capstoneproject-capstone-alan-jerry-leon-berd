package project;

import project.core.*;

public class Vars{
    public static boolean debug = false;
    public static int width = 1200, height = 800;
    public static Canvas canvas;

    public static void init(){
        canvas = new Canvas();

        canvas.init();
    }
}
