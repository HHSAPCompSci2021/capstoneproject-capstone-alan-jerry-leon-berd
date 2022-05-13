package project.ui.bars;

import gameutils.func.prim.*;
import project.ui.*;

/** Represents a progress bar. */
public abstract class ProgressBar extends Table{
    public Floatp progress;

    public ProgressBar(float width, float height){
        width(width).height(height);
    }

    /** Set the progress of the bar to the specified float (0-1). */
    public ProgressBar progress(float progress){
        return progress(() -> progress);
    }

    /** Set the progress of the bar to the specified runnable (0-1). */
    public ProgressBar progress(Floatp progress){
        this.progress = progress;
        return this;
    }
}
