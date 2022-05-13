package project.ui.drawables;

import gameutils.func.*;
import project.ui.*;

import static project.Vars.*;

/** Represents any piece of UI that has supports a runnable as a renderer. */
public class Drawable extends Table{
    public Cons<Drawable> drawer;

    /** Set the renderer of this Drawable. */
    public Drawable drawer(Cons<Drawable> drawer){
        this.drawer = drawer;
        return this;
    }

    @Override
    public void draw(){
        canvas.fill(color());
        if(drawer != null) drawer.get(this);
    }
}
