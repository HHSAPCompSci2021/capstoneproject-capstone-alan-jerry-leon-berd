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
        canvas.fill(color());
        canvas.rect(0, 0, width * progress.get(), height);

        canvas.glow(glow(), 5, color(), 5, i -> {
            canvas.rectc(width * progress.get() / 2, height / 2, width() + i, height);
        });
    }
}
