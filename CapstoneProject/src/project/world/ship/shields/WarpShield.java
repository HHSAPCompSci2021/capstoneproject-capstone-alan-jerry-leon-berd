package project.world.ship.shields;

import gameutils.math.*;
import project.graphics.*;
import project.world.ship.shields.ShockwaveShield.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores stats for a shield that teleports the player. */
public class WarpShield extends CooldownShield{
    /**
     * Create a warp shield with the specified name
     * @param name the name
     */
    public WarpShield(String name){
        super(name);

        max = 120;
        regen = 8;
        respawn = 8;
        cooldown = 1.5f;

        color = new Color(150, 0, 255);
    }

    @Override
    public WarpShieldInstance create(){
        return new WarpShieldInstance(this);
    }

    protected class WarpShieldInstance extends CooldownShieldInstance{
        public WarpShieldInstance(WarpShield type){
            super(type);
        }

        @Override
        public void effect(){
            Effects.upgrade.at(0, 0, e -> e.set(0, player().size()).parent(new Vec2(player().pos)));
            player().pos.set(random(0, width), random(0, height));
        }
    }
}
