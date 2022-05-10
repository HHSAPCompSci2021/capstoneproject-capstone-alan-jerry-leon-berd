package project.ui.interactables;

import gameutils.func.*;
import project.ui.drawables.*;

import static project.Vars.*;

/** Represents a button. */
public class Button extends Drawable{
    public Cons<Button> hover, press;

    public Button hover(Cons<Button> hover){
        this.hover = hover;
        return this;
    }

    public Button press(Cons<Button> press){
        this.press = press;
        return this;
    }

    @Override
    public void update(){
        super.update();
        if(bounds().contains(input.mouse) && input.mouseLeft()){
            press.get(this);
            input.remove(input.left);
        }
    }

    @Override
    public void draw(){
        super.draw();
        if(bounds().contains(input.mouse) && hover != null) hover.get(this);
    }
}