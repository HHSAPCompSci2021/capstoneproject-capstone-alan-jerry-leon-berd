package project.world.ship.shields;

import project.*;
import project.game.*;
import project.graphics.*;
import project.world.ship.shields.CooldownShield.*;
import project.world.ship.shields.Shield.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class ReflectShield extends CooldownShield{
    public float reflectRange = 200;

    public ReflectShield(String name){
        super(name);

        max = 115;
        regen = 10;
        color = new Color(255, 120, 40);
        cooldown = 4.5f;
    }

    @Override
    public ReflectShieldInstance create(){
        return new ReflectShieldInstance(this);
    }

    /** Represents an instance of a shield. */
    public class ReflectShieldInstance extends CooldownShieldInstance{
        public ReflectShieldInstance(ReflectShield type){
            super(type);
        }

        @Override
        public void effect(){
            world.bullets.each(b -> {
                if(b.team != Team.player && dst(b.pos, player().pos) < reflectRange && b.bullet.reflectable){
                    b.rotation = Tmp.v1.set(b.pos).sub(player().pos).ang();
                    b.team = Team.player;
                    b.damage *= 10;
                    b.speed *= 1.5f;
                }
            });
            Effects.shockwave.at(player().pos.x, player().pos.y, e -> e.color(0, player().color()).set(3, reflectRange / 2).lifetime(20));
        }
    }
}
