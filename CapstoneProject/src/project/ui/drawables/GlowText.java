package project.ui.drawables;

import static project.Vars.*;

/** Represents a glowing text box. */
public class GlowText extends Text{
    public float glow = 1;

    public GlowText glow(float glow){
        this.glow = glow;
        return this;
    }

    @Override
    public void draw(){
        super.draw();

        canvas.pushMatrix();
        canvas.scale(xScale, yScale);
        canvas.glow(glow / 5, 0.005f, color(), 3, i -> {
            canvas.text(text, (i - (glow / 10)) * size(), 0);
        });
        canvas.popMatrix();
    }
}
