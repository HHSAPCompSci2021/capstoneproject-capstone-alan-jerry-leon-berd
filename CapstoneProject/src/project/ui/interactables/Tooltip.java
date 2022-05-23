package project.ui.interactables;

import gameutils.func.*;
import gameutils.func.prim.*;
import project.ui.*;

import static project.Vars.*;

/** Represents a piece of UI which is only drawn and updated when it's parent is hovered over. */
public class Tooltip extends Table{
    private boolean translated;
    private Boolf<Table> draw = t -> t.parent.bounds().contains(input.mouse);

    public Tooltip(){
        super();
    }

    /**
     * Create a tooltip with a runnable that is called during initialization.
     * @param cons the runnable
     */
    public Tooltip(Cons<Tooltip> cons){
        this();
        cons.get(this);
    }

    /**
     * Set the runnable that returns when this should be drawn.
     * @param draw the runnable
     * @return itself, for chaining
     */
    public Tooltip draw(Boolf<Table> draw){
        this.draw = draw;
        return this;
    }

    /**
     * Set whether this tooltip is to be translated or not.
     * @param translated whether it should be translated or not
     * @return itself, for chaining
     */
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
