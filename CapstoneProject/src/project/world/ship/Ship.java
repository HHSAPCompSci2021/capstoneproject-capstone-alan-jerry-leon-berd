package project.world.ship;

import gameutils.math.*;
import gameutils.struct.*;
import project.*;
import project.core.*;
import project.graphics.*;
import project.world.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Represents a ship. */
public abstract class Ship extends Entity{
    /** Stores the rotation of this ship. */
    public float rotation = 0;

    public Set<Ship> collided = new Set<>();

    public Ship(ShipType type){
        super((Type)type);
    }

    /** Applies the specified force on this ship. */
    public void apply(Vec2 force){
        apply(force.x, force.y);
    }

    public void apply(float x, float y){
        vel.add(x / mass(), y / mass());
    }

    /** Returns the color of this ship. */
    public abstract Color color();

    /** Returns the sprite of this ship. */
    public abstract Sprite sprite();

    public float mass(){
        return (ship().mass() + rules.add(shipMass, team)) * rules.mult(shipMass, team);
    }

    public float health(){
        return (ship().health() + rules.add(maxHull, team)) * rules.mult(maxHull, team);
    }

    @Override
    public float size(){
        return ship().size();
    }

    public float accel(){
        return (ship().accel() + rules.add(enginePower, team)) * rules.mult(enginePower, team);
    }

    public float rotate(){
        return (ship().rotate() + rules.add(rotateSpeed, team)) * rules.mult(rotateSpeed, team);
    }

    public float ram(){
        return (ship().ram() + rules.add(ramDamage, team)) * rules.mult(ramDamage, team);
    }

    /** Deals the specified damage to this ship. */
    public void damage(float damage){
        life -= damage;
    }

    /** Moves the ship forward. */
    public void thrust(){
        apply(Tmp.v1.set(accel(), 0).rot(rotation));
    }

    /** Rotates the ship to the specified angle. */
    public void rotate(float angle){
        rotation = turn(rotation, angle, rotate());
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
                if(s.team != team) s.damage(vel.len() * mass() * ram());
                s.apply(Tmp.v1.set(s.pos).sub(pos).nor().scl(universalDamping * (s.team != team ? 10 : 1)));
                apply(Tmp.v1.inv());
                collided.add(s);
            }
        });
        collided.clear();

        vel.scl(1f - universalDrag);
    }

    @Override
    public void draw(){
        Effects.glow.drawc(pos.x, pos.y, size() * 20, size() * 20, color(), 20);
        Effects.glow.drawc(pos.x, pos.y, size() * 5, size() * 5, Color.white, 30);
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