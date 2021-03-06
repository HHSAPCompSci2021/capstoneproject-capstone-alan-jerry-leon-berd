package project.ui.drawables;

import gameutils.func.*;
import project.ui.*;

import static project.Vars.*;

/** Represents any piece of UI that has supports a runnable as a renderer. */
public class Drawable extends Table{
    protected Cons<Drawable> drawer;

    /**
     * Set the renderer of this Drawable.
     * @param drawer the drawer
     * @return itself, for chaining
     */
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
