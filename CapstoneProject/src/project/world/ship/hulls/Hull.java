package project.world.ship.hulls;

import gameutils.math.*;
import gameutils.struct.*;
import project.*;
import project.core.Content.*;
import project.game.*;
import project.graphics.*;
import project.world.modifiers.*;
import project.world.ship.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Stores stats for a hull. */
public class Hull extends Modifier implements ShipType{
    public ShipSprite ship = new ShipSprite();

    public float health = 100;

    public float accel = 0.25f;
    public float rotate = 10;
    public float mass = 1;
    public float size = 10;
    public float ram = 10;
    public float regen = 2;
    public float drag = 1;

    public Vec2 shootPos = new Vec2(5, 0);
    public float shootRot = 0;
    public Seq<Thruster> thrusters = new Seq<>();

    public Hull(String name){
        super(name);

        tag = "HULL";

        sprite.set("hull-" + name);
        ship.set(name);
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
        public HullInstance(Hull type){
            super(type);
        }

        /** Returns the position to create bullets, taking the player's rotation into account. */
        public Vec2 shootPos(){
            return Tmp.v1.set(shootPos).rot(world.player.rotation).add(world.player.pos);
        }

        public float shootRot(){
            return shootRot;
        }

        public void shot(){
        }

        public float regen(){
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

    public static class ShipSprite extends Sprite{
        public ShipSprite set(String name){
            super.set(SpritePath.ships, name);
            return this;
        }
    }
}