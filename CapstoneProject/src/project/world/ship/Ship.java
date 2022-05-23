package project.world.ship;

import gameutils.math.*;
import gameutils.struct.*;
import gameutils.struct.Set;
import project.*;
import project.content.*;
import project.core.*;
import project.core.Rules.*;
import project.graphics.*;
import project.world.*;
import project.world.ship.StatusEffect.*;

import java.awt.*;
import java.util.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Represents a ship. */
public class Ship extends Entity{
    protected float rotation = 0;

    protected Seq<StatusEntry> buffer = new Seq<>();
    protected Seq<StatusEntry> statuses = new Seq<>();
    protected Set<Ship> collided = new Set<>();

    protected float[] mult, add;

    /**
     * Create a ship with the specified type
     * @param type the type
     */
    public Ship(ShipType type){
        super((Type)type);
        mult = new float[Rule.all.length];
        add = new float[Rule.all.length];
        recalc();
    }

    /**
     * Applies the specified force on this ship.
     * @param force the force
     */
    public void apply(Vec2 force){
        apply(force.x, force.y);
    }

    /**
     * Applies the specified force on this ship.
     * @param x the x component of the force
     * @param y the y component of the force
     */
    public void apply(float x, float y){
        vel.add(x / mass(), y / mass());
    }

    /**
     * Applies a status effect with an infinite duration onto this ship
     * @param effect the effect
     */
    public void entry(StatusEffect effect){
        entry(effect, 10000000);
    }

    /**
     * Applies a status effect with a specified duration onto this ship
     * @param effect the effect
     * @param duration the duration
     */
    public void entry(StatusEffect effect, float duration){
        StatusEntry entry = effect.create();
        entry.lifetime = duration;
        entry.ship(this);
        statuses.add(entry);
        recalc();
    }

    protected void recalc(){
        for(int i = 0;i < Rule.all.length;i++){
            mult[i] = 1;
            add[i] = 0;
        }

        for(StatusEntry status : statuses){
            for(int i = 0;i < Rule.all.length;i++){
                mult[i] += status.type().mult[i];
                add[i] += status.type().add[i];
            }
        }
    }

    protected float add(Rule rule){
        return add[rule.id()];
    }

    protected float mult(Rule rule){
        return max(mult[rule.id()], 0);
    }

    protected float calc(Rule rule, float base){
        return (base + rules.add(rule, team) + add(rule)) * rules.mult(rule, team) * mult(rule);
    }

    /**
     * Returns a list of status effects applied on this ship.
     * @return the list
     */
    public Seq<StatusEntry> statuses(){
        return statuses;
    }

    /**
     * Returns the rotation of this ship
     * @return the roation
     */
    public float rotation(){
        return rotation;
    }

    /** Returns the sprite of this ship. */
    public Sprite sprite(){
        return null;
    }

    @Override
    public float fin(){
        return life / health();
    }

    protected float mass(){
        return calc(shipMass, ship().mass());
    }

    /**
     * Returns the maximum health of this ship
     * @return the value
     */
    public float health(){
        return calc(maxHull, ship().health());
    }

    protected float vulnerability(){
        return calc(vulnerability, 1f);
    }

    @Override
    public float size(){
        return ship().size();
    }

    protected float accel(){
        return calc(enginePower, ship().accel());
    }

    protected float rotate(){
        return calc(rotateSpeed, ship().rotate()) * delta;
    }

    protected float ram(){
        return calc(ramDamage, ship().ram());
    }

    protected float drag(){
        return calc(shipDrag, ship().drag());
    }

    protected float resistance(){
        return calc(ramResistance, 1f);
    }

    protected boolean spawned(){
        return statuses.contains(b -> b.type() == StatusEffects.spawned); //Note that since spawned is usually the first effect added, this isn't very inefficient
    }

    /** Deals the specified damage to this ship. */
    public void damage(float damage){
        life -= damage * vulnerability();
    }

    protected void thrust(){
        apply(Tmp.v1.set(accel(), 0).rot(rotation));
    }

    protected void rotate(float angle){
        rotation = turn(rotation, angle, rotate());
    }

    protected void wrap(){
        if(!Tmp.r1.set(world.bounds).expand(size()).contains(pos)){
            pos.x = mod(pos.x - Tmp.r1.x, Tmp.r1.w) + Tmp.r1.x;
            pos.y = mod(pos.y - Tmp.r1.y, Tmp.r1.h) + Tmp.r1.y;
        }
    }

    @Override
    public void init(){
        life = health();
    }

    @Override
    public void update(){
        float x = pos.x, y = pos.y;

        super.update();

        world.ships.raycast(x, y, size() + maxEntitySize, vel.ang(), vel.len(), (s, pos) -> {
            if(s != this && !collided.contains(s) && dst(s, pos) < s.size() + size()){
                if(s.team != team) s.damage((vel.len() * mass() + s.vel.len() * s.mass()) * ram() * universalRamDamage / s.resistance());
                s.apply(Tmp.v1.set(s.pos).sub(pos).nor().scl(universalDamping * (s.team != team ? 10 : 1)));
                apply(Tmp.v1.inv());
                collided.add(s);
            }
        });
        collided.clear();

        vel.scl(1f - universalDrag * drag());

        buffer.clear();
        for(StatusEntry status : statuses){
            status.update();
            if(status.keep()) buffer.add(status);
        }
        int size = statuses.size;
        statuses.clear();
        statuses.addAll(buffer);
        if(buffer.size != size) recalc();
    }

    @Override
    public void glow(){
        super.glow();
//        Effects.glow.drawc(pos.x, pos.y, size() * 5, size() * 5, Color.white, 30);
    }

    @Override
    public void remove(){
        canvas.shake(size());
        Effects.shockwave.at(pos.x, pos.y, e -> e.color(0, color()).set(3, size() * 2));
        Effects.fragment.at(pos.x, pos.y, e -> e.color(20, color()).set(23, size()));
    }

    public ShipType ship(){
        return (ShipType)type;
    }
}