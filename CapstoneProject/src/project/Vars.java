package project;

import project.core.*;

/** Contains many static fields, all of which are contents either essential to the program itself of determining important aspects of the program. */
public class Vars{
    public static boolean debug = false;
    public static int width = 1200, height = 800;
    public static Canvas canvas;

    public static void init(){
        canvas = new Canvas();

        canvas.init();
    }
}
