package project.world.ship.hulls;

import gameutils.math.*;
import gameutils.struct.*;
import project.*;
import project.core.Content.*;
import project.game.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.modifiers.*;
import project.world.ship.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Stores stats for a hull. */
public class Hull extends Modifier implements ShipType{
    public ShipSprite ship = new ShipSprite();

    protected float health = 100;

    protected float accel = 0.25f;
    protected float rotate = 10;
    protected float mass = 1;
    protected float size = 10;
    protected float ram = 10;
    protected float regen = 2;
    protected float drag = 1;

    protected Vec2 shootPos = new Vec2(5, 0);
    protected float shootRot = 0;
    protected Seq<Thruster> thrusters = new Seq<>();

    /**
     * Create a new hull, with the specified name
     * @param name the name
     */
    public Hull(String name){
        super(name);

        tag = "HULL";

        sprite.set("hull-" + name);
        ship.set("standard");
        thrusters.add(new Thruster(0, 7, 6f, 25f, 0));
    }

    @Override
    public void init(){
        addPro("Health: " + (int)health);
        addPro("Mass: " + (int)(mass * 100));
        addPro("Engine Power: " + (int)(accel * 100));
        addPro("Rotate Speed: " + (int)rotate);
        addPro("Ramming Damage: " + (int)ram);
        addPro("Drag: " + (int)(drag * 100) + "%");
        addPro("Regeneration: " + (int)regen + "%/sec");

        super.init();
    }

    @Override
    public float accel(){
        return accel;
    }

    @Override
    public float rotate(){
        return rotate;
    }

    @Override
    public float mass(){
        return mass;
    }

    @Override
    public float size(){
        return size;
    }

    @Override
    public float health(){
        return health;
    }

    @Override
    public float ram(){
        return ram;
    }

    @Override
    public float drag(){
        return drag;
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
        /**
         * Creates the instance of the hull, ith the specified type
         * @param type the type
         */
        public HullInstance(Hull type){
            super(type);
        }

        /**
         * Returns the position to create bullets, taking the player's rotation into account.
         * @return the position
         */
        public Vec2 shootPos(){
            return Tmp.v1.set(shootPos).rot(world.player.rotation()).add(world.player.pos);
        }

        /**
         * Returns the rotation to create bullets.
         * @return the rotation
         */
        public float shootRot(){
            return shootRot;
        }

        /** Called whenever a bullet is shot by the player. */
        public void shot(){
        }

        protected float regen(){
            return (regen + rules.add(hullRegen, Team.player)) * rules.mult(hullRegen, Team.player) / 100f / 60f;
        }

        /** Updates this hull. */
        public void update(){
            world.player.life = min(world.player.life + world.player.health() * regen(), world.player.health());
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

    protected static class ShipSprite extends Sprite{
        public ShipSprite set(String name){
            super.set(SpritePath.ships, name);
            return this;
        }
    }
}
