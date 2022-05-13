package project.world;

import gameutils.math.*;
import project.game.*;

import static project.Vars.*;

/** Represents an entity in the world. */
public class Entity extends Instance implements Pos2{
    public Team team = Team.none;
    /** The position of this entity. */
    public Vec2 pos = new Vec2();
    /** The velocity of this entity. */
    public Vec2 vel = new Vec2();

    /** Stores the "life" of this entity. This can mean health, frames since created, or something else entirely. */
    public float life;

    public Entity(Type type){
        super(type);
    }

    /** Returns the size of this entity. */
    public float size(){
        return 1;
    }

    /** Initializes this entity. */
    public void init(){
    }

    /** Updates this entity. */
    public void update(){
        if(vel.len() > universalSpeedLimit) vel.nor().scl(universalSpeedLimit);

        pos.add(vel);
    }

    /** Draws this entity. */
    public void draw(){
    }

    /** Called when this entity is removed. */
    public void remove(){
    }

    /** Returns whether this entity is still valid. */
    public boolean keep(){
        return true;
    }

    @Override
    public float x(){
        return pos.x;
    }

    @Override
    public float y(){
        return pos.y;
    }
}
