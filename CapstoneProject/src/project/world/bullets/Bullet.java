package project.world.bullets;

import gameutils.math.*;
import gameutils.struct.*;
import project.*;
import project.core.Content.*;
import project.graphics.*;
import project.world.*;
import project.world.ship.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Contains stats for a bullet. */
public class Bullet extends Type{
    public Sprite sprite;

    public float speed = 10;
    public float size = 5;
    public float damage = 10;
    public float knockback = 0.05f;
    public int pierce = 1;
    public float lifetime = -1;

    public float splashRadius = -1;
    public float splashDamage = 0;

    public int trailDuration = -1;
    public float trailSize = 3;

    @Override
    public ContentType type(){
        return ContentType.bullet;
    }

    public BulletEntity create(){
        return new BulletEntity(this);
    }

    /** Represents and simulates a bullet. */
    public class BulletEntity extends Entity{
        public float rotation, speed = 1f;
        public Vec2 pPos;

        public Ship origin;

        public Set<Entity> collided = new Set<>();

        public BulletEntity(Bullet type){
            super(type);
        }

        @Override
        public float size(){
            return size;
        }

        @Override
        public void init(){
            speed *= speed();
        }

        public float speed(){
            return type().speed * rules.bulletSpeedMult(team);
        }

        public float knockback(){
            return knockback * rules.bulletKnockbackMult(team);
        }

        public float damage(){
            return damage * rules.bulletDamageMult(team);
        }

        public float splashRadius(){
            return (rules.splashRadiusAdd(team) + splashRadius) * rules.splashRadiusMult(team);
        }

        public float splashDamage(){
            return (rules.splashDamageAdd(team) + splashDamage) * rules.splashDamageMult(team);
        }

        @Override
        public void update(){
            life++;

            pos.add(vel.set(speed, 0).rot(rotation));

            world.ships.raycast(pos.x, pos.y, size + maxEntitySize, rotation, speed, (s, pos) -> {
                if(s.team != this.team && !collided.contains(s) && dst(s, pos) < s.size() + size){
                    collided.add(s);
                    s.apply(Tmp.v1.set(vel).scl(knockback()));
                    s.damage(damage());
                }
            });

            if(trailDuration > 0){
                if(pPos == null) pPos = new Vec2();
                else Effects.trail.at(pos.x, pos.y, e -> e.color(0, origin.color()).set(3, pPos.x).set(4, pPos.y).set(5, trailSize).lifetime(trailDuration));
                pPos.set(pos);
            }
        }

        @Override
        public void draw(){
            Effects.glow.drawc(pos.x, pos.y, size() * 15, size() * 15, origin.color(), 30);

            if(sprite == null){
                //TODO: Sprites for everything
                canvas.fill(origin.color());
                canvas.ellipse(pos.x, pos.y, size());
                canvas.fill(255, 255, 255, 200);
                canvas.ellipse(pos.x, pos.y, size());
            }else{
                sprite.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, origin.color());
                sprite.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, Color.white, 200);
            }
        }

        @Override
        public void remove(){
            if(splashRadius() > 0){
                world.ships.query(pos.x, pos.y, splashRadius() + maxEntitySize, e -> {
                    if(e.team != team && dst(e, pos) < e.size() + splashRadius()) e.damage(splashDamage() * dst(e, pos) / (e.size() + splashRadius()));
                });

                Effects.shockwave.at(pos.x, pos.y, e -> e.color(0, origin.color()).set(3, splashRadius() / 2).lifetime(rt2(splashRadius()) * 1.5f));
            }
        }

        @Override
        public boolean keep(){
            return world.bounds.contains(pos) && collided.size < pierce && (life < lifetime || lifetime <= 0);
        }

        @Override
        public Bullet type(){
            return (Bullet)type;
        }
    }
}
