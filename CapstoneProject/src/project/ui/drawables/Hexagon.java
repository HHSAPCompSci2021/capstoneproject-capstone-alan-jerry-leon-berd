package project.ui.drawables;

import gameutils.func.*;
import project.ui.*;

import static gameutils.util.Mathf.*;

/** Represents a hexagon. Used to for formatting. */
public class Hexagon extends Table{
    public Hexagon(){
    }

    /**
     * Create a hexagon with a runnable that is called during initialization.
     * @param cons the runnable
     */
    public Hexagon(Cons<Hexagon> cons){
        cons.get(this);
    }

    @Override
    public float width(){
        return width;
    }

    @Override
    public float height(){
        return 0;
    }

    private float len(){
        return width / rt2(3f) - 0.2f;
    }

    /**
     * Returns a hexagon to the left of this one
     * @return the hexagon
     */
    public Hexagon left(){
        return (Hexagon)new Hexagon().x(x - width).y(y).width(width);
    }

    /**
     * Returns a hexagon to the top left of this one
     * @return the hexagon
     */
    public Hexagon topLeft(){
        return (Hexagon)new Hexagon().x(x - width / 2).y(y - len() * 3 / 2).width(width);
    }

    /**
     * Returns a hexagon to the top right of this one
     * @return the hexagon
     */
    public Hexagon topRight(){
        return (Hexagon)new Hexagon().x(x + width / 2).y(y - len() * 3 / 2).width(width);
    }

    /**
     * Returns a hexagon to the right of this one
     * @return the hexagon
     */
    public Hexagon right(){
        return (Hexagon)new Hexagon().x(x + width).y(y).width(width);
    }

    /**
     * Returns a hexagon to the bottom right of this one
     * @return the hexagon
     */
    public Hexagon bottomRight(){
        return (Hexagon)new Hexagon().x(x + width / 2).y(y + len() * 3 / 2).width(width);
    }

    /**
     * Returns a hexagon to the bottom left of this one
     * @return the hexagon
     */
    public Hexagon bottomLeft(){
        return (Hexagon)new Hexagon().x(x - width / 2).y(y + len() * 3 / 2).width(width);
    }
}
