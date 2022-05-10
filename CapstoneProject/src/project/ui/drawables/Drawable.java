package project.ui.drawables;

import gameutils.func.*;
import project.ui.*;

import static project.Vars.*;

public class Drawable extends Table{
    public Cons<Drawable> drawer;

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
