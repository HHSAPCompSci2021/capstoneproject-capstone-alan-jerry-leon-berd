package project.world.ship.shields;

import project.game.*;
import project.graphics.*;
import project.world.ship.shields.Shield.*;
import project.world.ship.shields.ShockwaveShield.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores stats for a shield that slows down time. */
public class TemporalShield extends Shield{
    protected float slowest = 0.2f;

    /**
     * Create a temporal shield with the specified name
     * @param name the name
     */
    public TemporalShield(String name){
        super(name);

        max = 75;
        regen = 10;
        respawn = 15;

        color = new Color(80, 170, 80);
    }

    @Override
    public TemporalShieldInstance create(){
        return new TemporalShieldInstance(this);
    }

    protected class TemporalShieldInstance extends ShieldInstance{
        public TemporalShieldInstance(TemporalShield type){
            super(type);
        }

        @Override
        public void damage(float damage){
            super.damage(damage);

            delta = slowest;
        }
    }
}
