package project.world.ship.shields;

import project.core.Content.*;
import project.game.*;
import project.world.modifiers.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Stores stats for a shield. */
public class Shield extends Modifier{
    protected Color color = new Color(120, 120, 255);

    protected float max = 100;
    protected float regen = 20;

    protected float respawn = 10;

    /**
     * Creates a new shield with the specified name
     * @param name the name
     */
    public Shield(String name){
        super(name);

        tag = "SHIELD";

        sprite.set("shield-" + name);
    }

    @Override
    public void init(){
        addPro("Shields: " + max);
        addPro("Regeneration: " + (int)regen + "%/sec");
        addPro("Cooldown: " + (int)respawn + " sec");

        super.init();
    }

    /**
     * Returns the color of this shield.
     * @return the color
     */
    public Color color(){
        return color;
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
        protected boolean broken;
        protected float value;

        /**
         * Create a new shield with the specified type.
         * @param type the type
         */
        public ShieldInstance(Shield type){
            super(type);
            value = shields();
        }

        /**
         * Returns whether this shield is broken or not.
         * @return whether this shield is broken or not
         */
        public boolean broken(){
            return broken;
        }

        /**
         * Returns amount of shields left.
         * @return the amount
         */
        public float value(){
            return value;
        }

        protected float shields(){
            return (max + rules.add(maxShields, Team.player)) * rules.mult(maxShields, Team.player);
        }

        protected float regen(){
            return (regen + rules.add(shieldRegen, Team.player)) * rules.mult(shieldRegen, Team.player) / 100f / 60f;
        }

        /**
         * Returns ratio of the shields current value to it's maximum value.
         * @return the ratio
         */
        public float fin(){
            return broken ? 0 : value / shields();
        }

        /**
         * Damage this shield with a specified amount of damage.
         * @param damage the amount of damage
         */
        public void damage(float damage){
            if(damage > value) broken = true;
            value = max(0, value - damage);
        }

        /** Updates this shield. */
        public void update(){
            if(broken){
                value += regen() / regen * 100 * 60;
                if(value > respawn * 60){
                    broken = false;
                    value = shields();
                }
            }else value = min(shields(), value + shields() * regen());
        }

        @Override
        public Shield type(){
            return (Shield)type;
        }
    }
}