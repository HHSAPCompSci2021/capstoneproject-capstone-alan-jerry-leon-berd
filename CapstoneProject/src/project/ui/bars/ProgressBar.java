package project.ui.bars;

import gameutils.func.prim.*;
import project.ui.*;

/** Represents a progress bar. */
public abstract class ProgressBar extends Table{
    protected Floatp progress;

    /**
     * Create a progress bar with the specified width and height
     * @param width the width
     * @param height the height
     */
    public ProgressBar(float width, float height){
        width(width).height(height);
    }

    /**
     * Set the progress of the bar to the specified float (0-1).
     * @param progress the progress
     * @return itself, for chaining
     */
    public ProgressBar progress(float progress){
        return progress(() -> progress);
    }

    /**
     * Set the progress of the bar to the specified runnable (0-1).
     * @param progress the progress runnable
     * @return itself, for chaining
     */
    public ProgressBar progress(Floatp progress){
        this.progress = progress;
        return this;
    }
}
