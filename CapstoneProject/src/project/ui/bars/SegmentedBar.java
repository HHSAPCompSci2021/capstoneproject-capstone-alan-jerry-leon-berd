package project.ui.bars;

import gameutils.func.prim.*;

import static project.Vars.*;

/** Represents a segmented bar. */
public class SegmentedBar extends ProgressBar{
    protected Intp segments;
    private float padding;

    /**
     *  Creates a segmented bar with the specified width, height, and padding between segments.
     * @param width the width
     * @param height the height
     * @param padding the padding
     */
    public SegmentedBar(float width, float height, float padding){
        super(width, height);
        this.padding = padding;
    }

    /**
     * Set the segments of the bar to the specified runnable
     * @param segments a runnable that returns an int, the segments
     * @return itself, for chaining
     */
    public SegmentedBar segments(Intp segments){
        this.segments = segments;
        return this;
    }

    protected int segments(){
        return segments.get();
    }

    @Override
    public float width(){
        return (int)(segments() * progress.get() + 0.99f) * width / segments();
    }

    @Override
    public void draw(){
        float real = (width - padding * (segments() - 1)) / segments(), inc = real + padding;

        canvas.fill(color(), color().getAlpha() * alpha() / 255f);
        int solid = (int)(segments() * progress.get() - 0.01f);
        for(int i = 0;i < solid;i++) canvas.rect(0 + inc * i, 0, real, height);
        canvas.fill(color(), (segments() * progress.get() - solid) * color().getAlpha() * alpha() / 255f);
        canvas.rect(0 + inc * solid, 0, real, height);
    }
}
