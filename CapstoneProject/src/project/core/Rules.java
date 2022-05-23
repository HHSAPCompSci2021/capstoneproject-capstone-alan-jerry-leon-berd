package project.core;

import project.core.Events.*;
import project.game.*;
import project.world.modifiers.*;
import project.world.modifiers.Modifier.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Stores the multipliers for each rule in the game. */
public class Rules{
    public float[][][] rules;

    public Rules(){
    }

    /** Initializes all rules. */
    public void init(){
        rules = new float[Team.all.length][Rule.all.length][2];

        reset();

        events.on(Event.modChange, e -> {
            reset();

            for(ModInstance m : world.player.modifiers) process(m);

            process(world.player.hull);
            process(world.player.shield);
            process(world.player.weapon);
        });
    }

    public void process(ModInstance m){
        for(int i = 0;i < all.length;i++){
            rules[Team.player.id()][i][0] += m.type().mult[i];
            rules[Team.player.id()][i][1] += m.type().add[i];
        }
    }

    public void reset(){
        for(int i = 0;i < Team.all.length;i++){
            for(int j = 0;j < Rule.all.length;j++){
                rules[i][j][0] = 1;
                rules[i][j][1] = 0;
            }
        }
    }

    public float mult(Rule rule, Team team){
        return max(rules[team.id()][rule.id()][0], 0);
    }

    public float add(Rule rule, Team team){
        return rules[team.id()][rule.id()][1];
    }

    /** Represents a specific rule. */
    public enum Rule{
        globalDamage("global damage"),
        bulletDamage("bullet damage"),
        ramDamage("hull damage"),
        blastDamage("blast damage"),
        blastRadius("blast radius"),

        bulletSpeed("projectile speed"),
        bulletKnockback("projectile knockback"),

        weaponReload("weapon reload"),
        weaponRecoil("weapon recoil"),
        weaponCharges("weapon charges"),
        shotProjectiles("shot projectiles"),
        shotSpread("shot spread"),

        enginePower("engine power"),
        rotateSpeed("rotate speed"),
        shipMass("ship mass"),
        shipDrag("ship drag"),

        maxHull("maximum hull"),
        hullRegen("passive regeneration rate"),
        maxShields("shields"),
        shieldRegen("shield"),

        vulnerability("vulnerability"),
        armorStack("armor stack"),
        ramResistance("collision resistance"),

        expGain("exp gained");

        public static Rule[] all = values();

        public final String name;

        Rule(String name){
            this.name = name;
        }

        /** Returns the id of this content. Effectively the same as ordinal(). */
        public int id(){
            return ordinal();
        }
    }
}
