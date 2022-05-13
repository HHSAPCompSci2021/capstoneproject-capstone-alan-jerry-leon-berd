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
    public float rotation = 0;

    public Set<Ship> collided = new Set<>();

    public Ship(Type type){
        super(type);
    }

    public void apply(Vec2 force){
        apply(force.x, force.y);
    }

    public void apply(float x, float y){
        vel.add(x / mass(), y / mass());
    }

    public abstract float mass();

    @Override
    public abstract float size();

    public abstract float accel();

    public abstract float rotate();

    public abstract Color color();

    public void damage(float damage){
        life -= damage;
    }

    public void thrust(){
        apply(Tmp.v1.set(accel(), 0).rot(rotation));
    }

    public void rotate(float angle){
        rotation = turn(rotation, angle, rotate());
    }

    @Override
    public void update(){
        float x = pos.x, y = pos.y;

        super.update();

        world.ships.raycast(x, y, size() + maxEntitySize, vel.ang(), vel.len(), (s, pos) -> {
            if(s != this && !collided.contains(s) && dst(s, pos) < s.size() + size()){
                if(s.team != team) s.damage(vel.len() * mass() * rules.ramDamage(team));
                s.apply(Tmp.v1.set(s.pos).sub(pos).nor().scl(universalDamping * (s.team != team ? 10 : 1) * rules.collisionForce(team)));
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
        Effects.shockwave1.at(pos.x, pos.y, e -> e.scale(size()));
        Effects.fragment5.at(pos.x, pos.y, e -> e.scale(size()));
    }
}