package project.game;

import gameutils.struct.*;
import project.*;
import project.content.*;
import project.core.Events.Event;
import project.core.Input.*;
import project.graphics.*;
import project.world.modifiers.*;
import project.world.modifiers.Modifier.*;
import project.world.ship.Hull.*;
import project.world.ship.*;
import project.world.ship.shields.Shield.*;
import project.world.ship.weapons.Weapon.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Represents the player ship. */
public class Player extends Ship{
    public Ship lastHit;

    public HullInstance hull;
    public ShieldInstance shield;
    public WeaponInstance weapon;

    public int level, spent;
    public float exp;

    public Seq<ModInstance> modifiers = new Seq<>();

    public Player(){
        super(Hulls.standard);
        team = Team.player;
        hull = Hulls.standard.create();
        shield = Shields.standard.create();
        weapon = Weapons.blaster.create();
        life = health();
    }

    /** Returns the ratio of current hp to max health. */
    public float fin(){
        return life / health();
    }

    @Override
    public Color color(){
        if(debug) return Color.getHSBColor(canvas.frameCount / 100f, 1f, 1f); //RGB PLAYER RGB PLAYER
        return shield.type().color;
    }

    @Override
    public Sprite sprite(){
        return hull.type().ship;
    }

    /** Add a modifier to this player. */
    public void addMod(Modifier mod){
        modifiers.add(mod.create());
    }

    @Override
    public void damage(float damage){
        events.call(Event.playerDamaged);
        float real = damage * vulnerability();
        if(shield.value > 0){
            if(shield.value > real) shield.value -= real;
            else{
                shield.value = 0;
                life -= real - shield.value;
            }
        }else life -= real;
    }

    @Override
    public void init(){
        events.on(Event.expGain, event -> {
            while(exp > pow(expScaling, level) * baseLevelExp){
                exp -= pow(expScaling, level) * baseLevelExp;
                level++;
                events.call(Event.levelUp);
            }
        });
        events.on(Event.levelUp, event -> Effects.upgrade.at(0, 0, e -> e.set(0, size()).parent(this)));
    }

    @Override
    public void update(){
        super.update();

        rotate(Tmp.v1.set(input.mouse).sub(pos).ang());

        hull.update();
        weapon.update();
        shield.update();

        if(input.pressed(KeyBind.thrust)){
            hull.thrust();
            thrust();
        }

        wrap();

//        for(ModEntry m : modifiers) m.update();
    }

    @Override
    public void draw(){
        super.draw();

        sprite().drawc(pos.x, pos.y, size() * 5, size() * 5, rotation + 90, color());
        sprite().drawc(pos.x, pos.y, size() * 5, size() * 5, rotation + 90, Color.white, 200);

        canvas.noFill();
        canvas.stroke(color());
        canvas.strokeWeight(2);
        canvas.ellipse(pos.x, pos.y, size() * 5, size() * 5);

        canvas.stroke(Color.white, 200);
        canvas.strokeWeight(2);
        canvas.ellipse(pos.x, pos.y, size() * 5, size() * 5);

        super.draw();
    }

    @Override
    public void remove(){
        events.call(Event.playerKilled);
    }

    @Override
    public boolean keep(){
        return !(life <= 0);
    }
}
