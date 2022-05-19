package project.ui.bars;

import static project.Vars.*;

/** Represents a smooth bar. */
public class SmoothBar extends ProgressBar{
    public SmoothBar(float width, float height){
        super(width, height);
    }

    @Override
    public float width(){
        return width * progress.get();
    }

    @Override
    public void draw(){
        canvas.fill(color(), color().getAlpha() * alpha() / 255f);
        canvas.rect(0, 0, width * progress.get(), height);
    }
}
