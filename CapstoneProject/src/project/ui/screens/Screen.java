package project.ui.screens;

/** Represents a screen. Only one can be up at a time, and is rendered and updated in Canvas. */
public abstract class Screen{
    /** Initializes this screen. */
    public void init(){
    }

    /** Rebuilds the UI on this screen. */
    public void rebuild(){
    }

    /** Updates this screen. */
    public void update(){
    }

    /** Draws this screen. */
    public void draw(){
    }
}
