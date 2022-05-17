package project.world.ship;

import project.core.Content.*;
import project.game.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.*;
import project.world.modifiers.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Stores stats for a shield. */
public class Shield extends Modifier{
    public Color color = new Color(120, 120, 255);
//    public Color color = new Color(255, 120, 120);

    public float max = 100;
    public float regen = 15;

    public Shield(String name){
        super(name);

        tag = "SHIELD";
    }

    @Override
    public void init(){
        if(sprite == null) sprite = new Sprite(SpritePath.upgrades, "shield-" + name);

        super.init();
    }

    @Override
    public ContentType type(){
        return ContentType.shield;
    }

    @Override
    public ShieldInstance create(){
        return new ShieldInstance(this);
    }

    /** Represents an instance of a shield. */
    public class ShieldInstance extends ModInstance{
        /** Stores the current health in the shield. */
        public float value;

        public ShieldInstance(Shield type){
            super(type);
            value = shields();
        }

        public float shields(){
            return (max + rules.add(maxShields, Team.player)) * rules.mult(maxShields, Team.player);
        }

        public float regen(){
            return (regen + rules.add(shieldRegen, Team.player)) * rules.mult(shieldRegen, Team.player) / 100f / 60f;
        }

        /** Returns ratio of the shields current value to it's maximum value. */
        public float fin(){
            return value / shields();
        }

        /** Updates this shield. */
        public void update(){
            value = min(shields(), value + shields() * regen());
        }

        @Override
        public Shield type(){
            return (Shield)type;
        }
    }
}