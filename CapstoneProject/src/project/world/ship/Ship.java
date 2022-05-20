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
    /** Stores the rotation of this ship. */
    public float rotation = 0;

    public Seq<StatusEntry> buffer = new Seq<>();
    public Seq<StatusEntry> statuses = new Seq<>();
    public Set<Ship> collided = new Set<>();

    public float[] mult, add;

    public Ship(ShipType type){
        super((Type)type);
        mult = new float[Rule.all.length];
        add = new float[Rule.all.length];
        recalc();
    }

    /** Applies the specified force on this ship. */
    public void apply(Vec2 force){
        apply(force.x, force.y);
    }

    public void apply(float x, float y){
        vel.add(x / mass(), y / mass());
    }

    public void entry(StatusEffect effect){
        entry(effect, 10000000);
    }

    public void entry(StatusEffect effect, float duration){
        StatusEntry entry = effect.create();
        entry.lifetime = duration;
        entry.ship(this);
        statuses.add(entry);
        recalc();
    }

    public void recalc(){
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

    public float add(Rule rule){
        return add[rule.id()];
    }

    public float mult(Rule rule){
        return max(mult[rule.id()], 0);
    }

    public float calc(Rule rule, float base){
        return (base + rules.add(rule, team) + add(rule)) * rules.mult(rule, team) * mult(rule);
    }

    /** Returns the sprite of this ship. */
    public Sprite sprite(){
        return null;
    }

    @Override
    public float fin(){
        return life / health();
    }

    public float mass(){
        return calc(shipMass, ship().mass());
    }

    public float health(){
        return calc(maxHull, ship().health());
    }

    public float vulnerability(){
        return calc(vulnerability, 1f);
    }

    @Override
    public float size(){
        return ship().size();
    }

    public float accel(){
        return calc(enginePower, ship().accel());
    }

    public float rotate(){
        return calc(rotateSpeed, ship().rotate()) * delta;
    }

    public float ram(){
        return calc(ramDamage, ship().ram());
    }

    public float drag(){
        return calc(shipDrag, ship().drag());
    }

    public float resistance(){
        return calc(ramResistance, 1f);
    }

    public boolean spawned(){
        return statuses.contains(b -> b.type() == Statuses.spawned); //Note that since spawned is usually the first effect added, this isn't very inefficient
    }

    /** Deals the specified damage to this ship. */
    public void damage(float damage){
        life -= damage * vulnerability();
    }

    /** Moves the ship forward. */
    public void thrust(){
        apply(Tmp.v1.set(accel(), 0).rot(rotation));
    }

    /** Rotates the ship to the specified angle. */
    public void rotate(float angle){
        rotation = turn(rotation, angle, rotate());
    }

    public void wrap(){
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