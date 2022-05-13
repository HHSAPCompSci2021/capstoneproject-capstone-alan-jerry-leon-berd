package project;

import jay.jaysound.*;
import project.core.*;
import project.core.Sounds.*;
import project.graphics.*;

import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;
import java.io.IOException;

import static gameutils.util.Mathf.*;

/** Contains variables that control gameplay or are essential to the game in general. */
public class Vars{
    public static boolean debug = false;

    public static int width = 1000, height = 600;
    public static float screenShake = 1f, shakeDrag = 2f;

    public static float UIscale = 1f; //Well and completely broken.
    public static boolean effectsEnabled = true;
    public static boolean glowEnabled = true;

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

    public static Sounds sounds;
    public static Events events;
    public static Effects effects;
    public static Content content;
    public static Rules rules;
    public static World world;
    public static Canvas canvas;
    public static Input input;
    public static UI ui;

    public static void init() throws CannotRealizeException, IOException, NoPlayerException {
        sounds = new Sounds();
        events = new Events();
        effects = new Effects();
        content = new Content();
        rules = new Rules();
        world = new World();
        canvas = new Canvas();
        input = new Input();
        ui = new UI();

        sounds.init();
        events.init();
        effects.init();
        content.init();
        rules.init();
        world.init();
        input.init();
        ui.init();
    }
}
