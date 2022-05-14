package project.world.ship;

import gameutils.math.*;
import gameutils.struct.*;
import project.*;
import project.core.Content.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.*;
import project.world.modifiers.*;

import static project.Vars.*;

/** Stores stats for a hull. */
public class Hull extends Modifier{
    public Sprite ship;

    public float health = 100;

    public float accel = 0.2f; //Engine acceleration
    public float rotate = 10; //Rotate speed
    public float mass = 1; //Ship mass
    public float size = 10; //Hitbox size

    public Vec2 shootPos = new Vec2(10, 0);
    public Seq<Thruster> thrusters = new Seq<>();

    public Hull(String name){
        super(name);
    }

    @Override
    public void init(){
        if(sprite == null) sprite = new Sprite(SpritePath.upgrades, "hull-" + name);
        if(ship == null) ship = new Sprite(SpritePath.ships, name);
        if(thrusters.size == 0) thrusters.add(new Thruster(0, 8, 6f, 25f, 0));

        super.init();
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
    public class HullInstance extends ModInstance{
        public HullInstance(Hull type){
            super(type);
        }

        /** Returns the ratio of current hp to max health. */
        public float fin(){
            return world.player.life / health;
        }

        /** Returns the position to create bullets, taking the player's rotation into account. */
        public Vec2 shootPos(){
            return Tmp.v1.set(world.player.hull.type().shootPos).rot(world.player.rotation).add(world.player.pos);
        }

        /** Updates this hull. */
        public void update(){
        }

        /** Creates the thrust effects of this ship. */
        public void thrust(){
            if(!canvas.timer(10)) return;
            for(Thruster t : thrusters) t.effect(world.player);
        }

        @Override
        public Hull type(){
            return (Hull)type;
        }
    }

}
