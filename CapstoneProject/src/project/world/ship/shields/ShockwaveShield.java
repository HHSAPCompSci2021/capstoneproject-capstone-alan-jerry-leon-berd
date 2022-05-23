package project.world.ship.shields;

import project.*;
import project.game.*;
import project.graphics.*;
import project.world.ship.shields.ReflectShield.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores stats for a shield that releases a shockwave. */
public class ShockwaveShield extends Shield{
    protected float shockwaveRange = 300;
    protected float shockwaveDamage = 150;

    /**
     * Create a shockwave shield with the specified name
     * @param name the name
     */
    public ShockwaveShield(String name){
        super(name);

        max = 45;
        regen = 20;
        respawn = 6;
        color = new Color(220, 220, 0);
    }

    @Override
    public ShockwaveShieldInstance create(){
        return new ShockwaveShieldInstance(this);
    }

    protected class ShockwaveShieldInstance extends ShieldInstance{
        public ShockwaveShieldInstance(ShockwaveShield type){
            super(type);
        }

        @Override
        public void damage(float damage){
            if(damage > value){
                world.bullets.each(b -> {
                    if(b.team != Team.player && dst(b.pos, player().pos) < shockwaveRange && b.bullet.reflectable()) b.pos.set(-100000, -100000);
                });
                world.ships.query(player().pos.x, player().pos.y, shockwaveRange + maxEntitySize, s -> {
                    if(s.team != Team.player && dst(s.pos, player().pos) < shockwaveRange + s.size()) s.damage(shockwaveDamage);
                });
                Effects.shockwave.at(player().pos.x, player().pos.y, e -> e.color(0, player().color()).set(3, shockwaveRange / 2).lifetime(20));
            }

            super.damage(damage);
        }
    }
}