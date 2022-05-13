package project.world;

import gameutils.math.*;
import project.game.*;

import static project.Vars.*;

/** Represents an entity in the world. */
public class Entity extends Instance implements Pos2{
    public Team team = Team.none;
    public Vec2 pos = new Vec2(), vel = new Vec2();

    public float life;

    public Entity(Type type){
        super(type);
    }

    public float size(){
        return 1;
    }

    public void init(){
    }

    public void update(){
        if(vel.len() > universalSpeedLimit) vel.nor().scl(universalSpeedLimit);

        pos.add(vel);
    }

    public void draw(){
    }

    public void remove(){
    }

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
