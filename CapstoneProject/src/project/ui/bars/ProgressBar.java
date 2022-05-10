package project.ui.bars;

import gameutils.func.prim.*;
import project.ui.*;

/** Represents a progress bar. */
public abstract class ProgressBar extends Table{
    public Floatp progress;
    public Floatp glow;

    public ProgressBar(float width, float height){
        width(width).height(height);
    }

    public ProgressBar progress(float progress){
        return progress(() -> progress);
    }

    public ProgressBar progress(Floatp progress){
        this.progress = progress;
        return this;
    }

    public ProgressBar glow(Floatp glow){
        this.glow = glow;
        return this;
    }

    public float glow(){
        return glow == null ? 0 : glow.get();
    }
}
