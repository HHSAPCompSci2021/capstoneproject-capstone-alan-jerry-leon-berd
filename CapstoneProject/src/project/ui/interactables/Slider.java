package project.ui.interactables;

import gameutils.func.prim.*;
import gameutils.util.*;
import project.ui.drawables.*;

import static project.Vars.*;

/** Represents a slider. */
public class Slider extends Drawable{
    public Floatc slide;
    public Floatp value;

    /** Set the runnable that is called whenever this slider is interacted with. The float it is called with (0-1) represents the ratio value of the current slider. */
    public Slider slide(Floatc slide){
        this.slide = slide;
        return this;
    }

    /** Sets the runnable that return the value of the slider (0-1). */
    public Slider value(Floatp value){
        this.value = value;
        return this;
    }

    /** Returns the value of this slider. */
    public float value(){
        return value.get();
    }

    @Override
    public void update(){
        super.update();
        if(bounds().contains(input.mouse) && input.mouseLeft()){
            slide.get(Mathf.clamp((input.mouse.x - x()) / width(), 0, 1));
        }
    }
}
