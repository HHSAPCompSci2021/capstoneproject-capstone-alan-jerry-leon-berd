package project.ui.bars;

import static project.Vars.*;

/** Represents a segmented bar. */
public class SegmentedBar extends ProgressBar{
    public int segments;
    public float padding;

    public SegmentedBar(float width, float height, int segments, float padding){
        super(width, height);
        this.segments = segments;
        this.padding = padding;
    }

    @Override
    public float width(){
        return (int)(segments * progress.get() + 0.99f) * width / segments;
    }

    @Override
    public void draw(){
        float real = (width - padding * (segments - 1)) / segments, inc = real + padding;

        canvas.fill(color());
        int solid = (int)(segments * progress.get() - 0.01f);
        for(int i = 0;i < solid;i++) canvas.rect(0 + inc * i, 0, real, height);
        canvas.fill(color(), (segments * progress.get() - solid) * 255);
        canvas.rect(0 + inc * solid, 0, real, height);

        canvas.glow(glow(), 5, color(), 5, i -> {
            canvas.rectc(width * progress.get() / 2, height / 2, width() + i, height);
        });
    }
}
