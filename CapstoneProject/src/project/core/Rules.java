package project.core;

import gameutils.util.*;
import project.core.Events.*;
import project.game.*;
import project.world.modifiers.Modifier.*;

import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Stores the multipliers for each rule in the game. */
public class Rules{
    public float[][][] rules;

    public Rules(){
    }

    public void init(){
        rules = new float[Team.all.length][Rule.all.length][2];

        reset();

        events.on(Event.modChange, e -> {
            reset();

            for(ModEntry m : world.player.modifiers){
                for(int i = 0;i < all.length;i++){
                    rules[world.player.team.id()][i][0] += m.type().mult[i];
                    rules[world.player.team.id()][i][1] += m.type().add[i];
                }
            }
        });
    }

    public void reset(){
        for(int i = 0;i < Team.all.length;i++){
            for(int j = 0;j < Rule.all.length;j++){
                rules[i][j][0] = 1;
                rules[i][j][1] = 0;
            }
        }
    }

    /** Returns the respective multipliers based on the team given. */
    public float globalDamageMult(Team team){
        return rules[team.id()][globalDamage.id()][0];
    }

    public float bulletDamageMult(Team team){
        return rules[team.id()][bulletDamage.id()][0] * globalDamageMult(team);
    }

    public float ramDamageMult(Team team){
        return rules[team.id()][ramDamage.id()][0] * globalDamageMult(team);
    }

    public float splashDamageMult(Team team){
        return rules[team.id()][splashDamage.id()][0] * bulletDamageMult(team);
    }

    public float splashDamageAdd(Team team){
        return rules[team.id()][splashDamage.id()][1];
    }

    public float splashRadiusMult(Team team){
        return rules[team.id()][splashRadius.id()][0];
    }

    public float splashRadiusAdd(Team team){
        return rules[team.id()][splashRadius.id()][1];
    }

    public int weaponChargesAdd(Team team){
        return (int)rules[team.id()][weaponCharges.id()][1];
    }

    public int shotProjectilesAdd(Team team){
        return (int)rules[team.id()][shotProjectiles.id()][1];
    }

    public float bulletSpeedMult(Team team){
        return rules[team.id()][bulletSpeed.id()][0];
    }

    public float bulletKnockbackMult(Team team){
        return rules[team.id()][bulletKnockback.id()][0];
    }

    public float weaponReloadMult(Team team){
        return rules[team.id()][weaponReload.id()][0];
    }

    public float weaponRecoilMult(Team team){
        return rules[team.id()][weaponRecoil.id()][0];
    }

    public float engineAccelerationMult(Team team){
        return rules[team.id()][engineAcceleration.id()][0];
    }

    public float rotateSpeedMult(Team team){
        return rules[team.id()][rotateSpeed.id()][0];
    }

    public float shipMassMult(Team team){
        return rules[team.id()][shipMass.id()][0];
    }

    /** Represents a specific rule. */
    public enum Rule{
        globalDamage,
        bulletDamage,
        ramDamage,
        splashDamage,
        splashRadius,
        weaponCharges,
        shotProjectiles,
        bulletSpeed,
        bulletKnockback,
        weaponReload,
        weaponRecoil,
        engineAcceleration,
        rotateSpeed,
        shipMass;

        public static Rule[] all = values();

        public int id(){
            return ordinal();
        }
    }
}
