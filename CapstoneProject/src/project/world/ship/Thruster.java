package project.world.ship;

import gameutils.math.*;
import project.*;
import project.graphics.*;

/** Represents a thruster on a ship. For visuals only. */
public class Thruster{
    /** The position of this thruster. */
    protected Vec2 pos;
    protected float width, height, rot;

    /**
     * Creates a new thruster with the specified parameters
     * @param x the x coordinate, relative to the ship
     * @param y the y coordinate, relative to the ship
     * @param width the width of the thruster
     * @param height the height of the thruster
     * @param rot the rotational offset of the thruster, relative to the ship
     */
    public Thruster(float x, float y, float width, float height, float rot){
        this.pos = new Vec2(x, y);
        this.width = width;
        this.height = height;
        this.rot = rot;
    }

    /**
     * Creates the thruster effect.
     * @param ship the ship this thruster belongs to
     */
    public void effect(Ship ship){
        Tmp.v1.set(pos).rot(ship.rotation + 90);
        Effects.thrust.at(Tmp.v1.x, Tmp.v1.y, e -> e.color(0, ship.color()).set(3, width * ship.size()).set(4, height * ship.size()).set(5, ship.rotation + rot + 90).parent(ship));
    }
}
