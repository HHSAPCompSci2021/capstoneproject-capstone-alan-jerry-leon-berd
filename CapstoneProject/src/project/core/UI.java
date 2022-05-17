package project.core;

import project.ui.screens.*;

import static project.Vars.*;

/** Stores the screens and UI. */
public class UI{
    public MenuScreen menuScreen;
    public GameScreen gameScreen;
    public PauseScreen pauseScreen;

    public void init(){
        menuScreen = new MenuScreen();
        gameScreen = new GameScreen();
        pauseScreen = new PauseScreen();

        menuScreen.init();
        gameScreen.init();
        pauseScreen.init();

        canvas.screen = menuScreen;
    }

    /** Represents a x align configuration of a table. */
    public enum AlignX{
        left,
        center,
        right
    }

    /** Represents a y align configuration of a table. */
    public enum AlignY{
        top,
        center,
        bottom
    }
}
