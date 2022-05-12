package project.world.ship;

import gameutils.math.*;
import project.*;
import project.core.Content.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.*;

import static project.Vars.*;

/** Stores stats for a hull. */
public class Hull extends Type{
    public Sprite sprite;

    public float health = 100;

    public float accel = 0.2f; //Engine acceleration
    public float rotate = 10; //Rotate speed
    public float mass = 1; //Ship mass
    public float size = 10; //Hitbox size

    public Vec2 shootPos = new Vec2(10, 0);

    @Override
    public void init(){
        super.init();

        System.out.println("Initialized");

        if(sprite == null) sprite = new Sprite(SpritePath.ships, "standard");

        System.out.println(sprite);
    }

    @Override
    public ContentType type(){
        return ContentType.hull;
    }

    @Override
    public HullInstance create(){
        return new HullInstance(this);
    }

    /** Represents an instance of a hull. */
    public class HullInstance extends Instance{
        public HullInstance(Hull type){
            super(type);
        }

        public Vec2 shootPos(){
            return Tmp.v1.set(world.player.hull.type().shootPos).rot(world.player.rotation).add(world.player.pos);
        }

        public void update(){
        }

        @Override
        public Hull type(){
            return (Hull)type;
        }
    }
}
