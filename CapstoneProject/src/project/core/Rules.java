package project.core;

import gameutils.util.*;
import project.core.Events.*;
import project.game.*;
import project.world.modifiers.Modifier.*;

import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Stores the multipliers for each rule in the game. */
public class Rules{
    public float[][] rules;

    public Rules(){
    }

    public void init(){
        rules = new float[Team.all.length][Rule.all.length];

        for(int i = 0;i < Team.all.length;i++) Structf.fill(rules[i], 1);

        events.on(Event.modChange, e -> {
            for(int i = 0;i < Team.all.length;i++) Structf.fill(rules[i], 1);

            for(ModEntry m : world.player.modifiers){
                for(int i = 0;i < all.length;i++){
                    rules[world.player.team.id()][i] += m.type().multiplier[i];
                }
            }
        });
    }

    public float bulletDamage(Team team){
        return rules[team.id()][bulletDamage.id()] * globalDamage(team);
    }

    public float ramDamage(Team team){
        return rules[team.id()][ramDamage.id()] * globalDamage(team);
    }

    public float globalDamage(Team team){
        return rules[team.id()][globalDamage.id()];
    }

    public int weaponCharges(Team team){
        return (int)rules[team.id()][weaponCharges.id()];
    }

    public int shotProjectiles(Team team){
        return (int)rules[team.id()][shotProjectiles.id()];
    }

    public float bulletSpeed(Team team){
        return rules[team.id()][bulletSpeed.id()];
    }

    public float bulletKnockback(Team team){
        return rules[team.id()][bulletKnockback.id()];
    }

    public float weaponReload(Team team){
        return rules[team.id()][weaponReload.id()];
    }

    public float weaponRecoil(Team team){
        return rules[team.id()][weaponRecoil.id()];
    }

    public float engineAcceleration(Team team){
        return rules[team.id()][engineAcceleration.id()];
    }

    public float rotateSpeed(Team team){
        return rules[team.id()][rotateSpeed.id()];
    }

    public float shipMass(Team team){
        return rules[team.id()][shipMass.id()];
    }

    public float collisionForce(Team team){
        return rules[team.id()][collisionForce.id()];
    }

    /** Represents a specific rule. */
    public enum Rule{
        globalDamage,
        bulletDamage,
        ramDamage,
        weaponCharges,
        shotProjectiles,
        bulletSpeed,
        bulletKnockback,
        weaponReload,
        weaponRecoil,
        engineAcceleration,
        rotateSpeed,
        shipMass,
        collisionForce;

        public static Rule[] all = values();

        public int id(){
            return ordinal();
        }
    }
}
