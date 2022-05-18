package project.ui.drawables;

import gameutils.func.*;
import project.ui.*;

import static gameutils.util.Mathf.*;

public class Hexagon extends Table{
    public Hexagon(){
    }

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

    public float len(){
        return width / rt2(3f) - 0.2f;
    }

    public Hexagon left(){
        return (Hexagon)new Hexagon().x(x - width).y(y).width(width);
    }

    public Hexagon topLeft(){
        return (Hexagon)new Hexagon().x(x - width / 2).y(y - len() * 3 / 2).width(width);
    }

    public Hexagon topRight(){
        return (Hexagon)new Hexagon().x(x + width / 2).y(y - len() * 3 / 2).width(width);
    }

    public Hexagon right(){
        return (Hexagon)new Hexagon().x(x + width).y(y).width(width);
    }

    public Hexagon bottomRight(){
        return (Hexagon)new Hexagon().x(x + width / 2).y(y + len() * 3 / 2).width(width);
    }

    public Hexagon bottomLeft(){
        return (Hexagon)new Hexagon().x(x - width / 2).y(y + len() * 3 / 2).width(width);
    }
}
