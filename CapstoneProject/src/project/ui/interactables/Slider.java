package project.ui.interactables;

import gameutils.func.prim.*;
import gameutils.util.*;
import project.ui.drawables.*;

import static project.Vars.*;

public class Slider extends Drawable{
    public Floatc slide;
    public Floatp value;

    public Slider slide(Floatc slide){
        this.slide = slide;
        return this;
    }

    public Slider value(Floatp value){
        this.value = value;
        return this;
    }

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
