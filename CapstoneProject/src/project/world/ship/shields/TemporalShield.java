package project.world.ship.shields;

import project.game.*;
import project.graphics.*;
import project.world.ship.shields.Shield.*;
import project.world.ship.shields.ShockwaveShield.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class TemporalShield extends Shield{
    public float slowest = 0.2f;

    public TemporalShield(String name){
        super(name);

        max = 75;
        regen = 10;
        respawn = 15;

        color = new Color(20, 170, 20);
    }

    @Override
    public TemporalShieldInstance create(){
        return new TemporalShieldInstance(this);
    }

    /** Represents an instance of a shield. */
    public class TemporalShieldInstance extends ShieldInstance{
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
