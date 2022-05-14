package project.world.ship;

import gameutils.math.*;
import gameutils.struct.*;
import gameutils.util.*;
import project.*;
import project.graphics.*;
import project.world.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Represents a ship. */
public abstract class Ship extends Entity{
    /** Stores the rotation of this ship. */
    public float rotation = 0;

    public Set<Ship> collided = new Set<>();

    public Ship(Type type){
        super(type);
    }

    /** Applies the specified force on this ship. */
    public void apply(Vec2 force){
        apply(force.x, force.y);
    }

    public void apply(float x, float y){
        vel.add(x / mass(), y / mass());
    }

    /** Returns the mass of this ship. */
    public abstract float mass();

    @Override
    public abstract float size();

    /** Returns the acceleration of this ship. */
    public abstract float accel();

    /** Returns the rotation speed of this ship. */
    public abstract float rotate();

    /** Returns the color of this ship. */
    public abstract Color color();

    /** Returns the sprite of this ship. */
    public abstract Sprite sprite();

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
    public void update(){
        float x = pos.x, y = pos.y;

        super.update();

        world.ships.raycast(x, y, size() + maxEntitySize, vel.ang(), vel.len(), (s, pos) -> {
            if(s != this && !collided.contains(s) && dst(s, pos) < s.size() + size()){
                if(s.team != team) s.damage(vel.len() * mass() * rules.ramDamageMult(team));
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
        Effects.shockwave.at(pos.x, pos.y, e -> e.color(0, color()).set(3, size()));
        Effects.fragment.at(pos.x, pos.y, e -> e.color(0, color()).set(5, size()));
    }
}