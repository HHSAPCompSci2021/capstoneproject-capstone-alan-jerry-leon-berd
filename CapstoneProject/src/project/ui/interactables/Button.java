package project.ui.interactables;

import gameutils.func.*;
import project.core.*;
import project.ui.drawables.*;

import static project.Vars.*;

/** Represents a button. */
public class Button extends Drawable{
    private Cons<Button> hover, press;

    /**
     * Sets the renderer that is drawn when this button is hovered over.
     * @param hover the renderer
     * @return itself, for chaining
     */
    public Button hover(Cons<Button> hover){
        this.hover = hover;
        return this;
    }

    /**
     * Sets the runnable that is run when this button is pressed.
     * @param press the runnable
     * @return itself, for chaining
     */
    public Button press(Cons<Button> press){
        this.press = press;
        return this;
    }

    @Override
    public void update(){
        super.update();
        if(bounds().contains(input.mouse) && input.mouseLeft()){
            if(soundEffects) Sounds.playSound("ComputerSFX.mp3");
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