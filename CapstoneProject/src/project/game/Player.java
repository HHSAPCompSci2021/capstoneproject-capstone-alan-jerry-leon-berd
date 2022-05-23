package project.game;

import gameutils.struct.*;
import project.*;
import project.content.*;
import project.core.Events.Event;
import project.core.Input.*;
import project.graphics.*;
import project.world.modifiers.*;
import project.world.modifiers.Modifier.*;
import project.world.ship.*;
import project.world.ship.hulls.Hull.*;
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

    public int level = 3, spent;
    public float exp;

    public Seq<ModInstance> modifiers = new Seq<>();

    public Player(){
        super(null);
        team = Team.player;
        hull = Hulls.standard.create();
        shield = Shields.standard.create();
        weapon = Weapons.blaster.create();
        life = health();
    }

    /**
     * Returns the ratio of current hp to max health.
     * @return the ratio
     */
    public float fin(){
        return life / health();
    }

    @Override
    public Color color(){
        if(debug) return Color.getHSBColor(canvas.frameCount / 100f, 1f, 1f); //RGB PLAYER RGB PLAYER
        return shield.type().color();
    }

    @Override
    public Sprite sprite(){
        return hull.type().ship;
    }

    /**
     * Add a modifier to this player.
     * @param mod the modifier
     */
    public void addMod(Modifier mod){
        modifiers.add(mod.create());
    }

    /**
     * Returns whether the player has the specified modifier
     * @param mod the modifier
     * @return whether the player has it or not
     */
    public boolean hasMod(Modifier mod){
        return modifiers.contains(m -> m.type() == mod);
    }

    @Override
    public void damage(float damage){
        events.call(Event.playerDamaged);
        float real = damage * vulnerability();

        if(!shield.broken()){
            life -= max(real - shield.value(), 0);
            shield.damage(real);
        }else life = max(life - real, 0);
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

        if(delta < 0.5f) pos.add(Tmp.v1.set(vel).scl(delta)); //Double speed for temporal shield
    }

    @Override
    public void draw(){
        super.draw();

        sprite().drawc(pos.x + 5, pos.y + 5, size() * 6, size() * 6, rotation + 90, Color.black, 50);

        sprite().drawc(pos.x, pos.y, size() * 6, size() * 6, rotation + 90, color());
        sprite().drawc(pos.x, pos.y, size() * 6, size() * 6, rotation + 90, Color.white, 200);

        if(!shield.broken()){
            canvas.noFill();
            canvas.stroke(color(), 255 * shield.fin());
            canvas.strokeWeight(3);
            canvas.ellipse(pos.x, pos.y, size() * 5, size() * 5);

//            canvas.stroke(Color.white, 200 * shield.fin());
//            canvas.strokeWeight(3);
//            canvas.ellipse(pos.x, pos.y, size() * 5, size() * 5);
        }

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

    @Override
    public ShipType ship(){
        return hull.type();
    }
}
