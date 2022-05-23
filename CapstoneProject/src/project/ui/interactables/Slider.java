package project.ui.interactables;

import gameutils.func.prim.*;
import project.ui.drawables.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Represents a slider. */
public class Slider extends Drawable{
    private Floatc slide;
    private Floatp value;

    /**
     * Set the runnable that is called whenever this slider is interacted with. The float it is called with (0-1) represents the ratio value of the current slider.
     * @param slide the runnable
     * @return itself, for chaining
     */
    public Slider slide(Floatc slide){
        this.slide = slide;
        return this;
    }

    /**
     * Sets the runnable that return the value of the slider (0-1).
     * @param value the value
     * @return itself, for chaining
     */
    public Slider value(Floatp value){
        this.value = value;
        return this;
    }

    /**
     * Returns the value (0-1) of this slider
     * @return the value
     */
    public float value(){
        return value.get();
    }

    @Override
    public void update(){
        super.update();
        if(bounds().contains(input.mouse) && input.mouseLeft()){
            slide.get(clamp((input.mouse.x - x()) / width(), 0, 1));
        }
    }
}
