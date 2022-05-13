package project.world.ship;

import gameutils.math.*;
import project.*;
import project.graphics.*;

public class Thruster{
    public Vec2 pos;
    public float width, height, rot;

    public Thruster(float x, float y, float width, float height, float rot){
        this.pos = new Vec2(x, y);
        this.width = width;
        this.height = height;
        this.rot = rot;
    }

    public void effect(Ship ship){
        Tmp.v1.set(pos).rot(ship.rotation + 90);
        Effects.thrust.at(Tmp.v1.x, Tmp.v1.y, e -> e.color(0, ship.color()).set(3, width * ship.size()).set(4, height * ship.size()).set(5, ship.rotation + rot + 90).parent(ship));
    }
}
