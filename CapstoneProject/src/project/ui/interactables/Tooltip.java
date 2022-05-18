package project.ui.interactables;

import gameutils.func.*;
import gameutils.func.prim.*;
import project.ui.*;

import static project.Vars.*;

/** Represents a piece of UI which is only drawn and updated when it's parent is hovered over. */
public class Tooltip extends Table{
    public boolean translated;
    public Boolf<Table> draw = t -> t.parent.bounds().contains(input.mouse);

    public Tooltip(){
        super();
    }

    public Tooltip(Cons<Tooltip> cons){
        this();
        cons.get(this);
    }

    public Tooltip draw(Boolf<Table> draw){
        this.draw = draw;
        return this;
    }

    public Tooltip translated(boolean translated){
        this.translated = translated;
        return this;
    }

    @Override
    public float x(){
        return translated ? super.x() : x;
    }

    @Override
    public float y(){
        return translated ? super.y() : y;
    }

    @Override
    public float width(){
        return 0;
    }

    @Override
    public float height(){
        return 0;
    }

    @Override
    public void process(){
        if(draw.get(this)) super.process();
    }
}
