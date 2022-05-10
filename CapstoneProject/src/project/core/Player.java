package project.core;

import gameutils.struct.*;
import gameutils.util.*;
import project.content.*;
import project.core.Events.Event;
import project.core.Input.*;
import project.graphics.*;
import project.world.modifiers.*;
import project.world.modifiers.Modifier.*;
import project.world.ship.Hull.*;
import project.world.ship.Shield.*;
import project.world.ship.*;
import project.world.ship.weapons.Weapon.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Represents the player ship. */
public class Player extends Ship{
    public HullInstance hull;
    public ShieldInstance shield;
    public WeaponInstance weapon;

    public int level;
    public float exp;

    public Seq<ModEntry> modifiers = new Seq<>();

    public Player(){
        super(null);
        team = Team.player;
        hull = Gear.normal.create();
        shield = Gear.shield.create();
        weapon = Gear.bullet.create();
        life = hull.type().health;
    }

    public float fin(){
        return life / hull.type().health;
    }

    @Override
    public float mass(){
        return hull.type().mass * rules.shipMass(team);
    }

    @Override
    public float size(){
        return hull.type().size;
    }

    @Override
    public Color color(){
        return shield.type().color;
    }

    public void addMod(Modifier mod){
        modifiers.add(mod.create());
        events.call(Event.modChange);
    }

    @Override
    public void damage(float damage){
        events.call(Event.playerDamage);
        if(shield.value > 0){
            if(shield.value > damage) shield.value -= damage;
            else{
                shield.value = 0;
                life -= damage - shield.value;
            }
        }else life -= damage;
    }

    @Override
    public void init(){
        events.on(Event.expGain, e -> {
            while(exp > pow(expScaling, level) * baseLevelExp){
                exp -= pow(expScaling, level) * baseLevelExp;
                level++;
                events.call(Event.levelUp);
            }
        });
    }

    @Override
    public void update(){
        super.update();

        rotate(tmp.set(input.mouse).sub(pos).ang(), hull.type().rotate * rules.rotateSpeed(team));

        hull.update();
        weapon.update();
        shield.update();

        if(input.pressed(KeyBind.thrust)) thrust(hull.type().accel * rules.engineAcceleration(team));

        if(!world.bounds.contains(pos)){
            pos.x = clamp(pos.x, world.bounds.x, world.bounds.x + world.bounds.w);
            pos.y = clamp(pos.y, world.bounds.y, world.bounds.y + world.bounds.h);
        }

        for(ModEntry m : modifiers) m.update();
    }

    @Override
    public void draw(){
        Effects.glow.drawc(pos.x, pos.y, size() * 10, size() * 10, color(), 50);

        hull.type().sprite.drawc(pos.x, pos.y, size() * 5, size() * 5, rotation + 90, color());

        canvas.noFill();
        canvas.stroke(color());
        canvas.strokeWeight(2);
        canvas.ellipse(pos.x, pos.y, size() * 5, size() * 5);

        super.draw();
    }

    public boolean keep(){
        return !(life <= 0);
    }
}
