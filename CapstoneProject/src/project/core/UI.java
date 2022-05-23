package project.core;

import project.ui.screens.*;

/** Stores the screens and UI. */
public class UI{
    public MenuScreen menuScreen;
    public GameScreen gameScreen;
    public PauseScreen pauseScreen;
    public UpgradeScreen upgradeScreen;
    public LoseScreen loseScreen;

    /** Initializes the UI. */
    public void init(){
        menuScreen = new MenuScreen();
        gameScreen = new GameScreen();
        pauseScreen = new PauseScreen();
        upgradeScreen = new UpgradeScreen();
        loseScreen = new LoseScreen();

        menuScreen.init();
        gameScreen.init();
        pauseScreen.init();
        upgradeScreen.init();
        loseScreen.init();
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
