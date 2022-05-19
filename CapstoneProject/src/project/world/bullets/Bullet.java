package project.world.bullets;

import gameutils.math.*;
import gameutils.struct.*;
import project.*;
import project.game.*;
import project.graphics.*;
import project.world.*;
import project.world.ship.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Contains stats for a bullet. */
public class Bullet{
    public BulletSprite sprite = new BulletSprite().set("blast");

    public float speed = 10;
    public float size = 5;
    public float damage = 10;
    public float knockback = 0.05f;
    public float lifetime = -1;
    public float splashRadius = 0;
    public float splashDamage = 0;

    public int trailDuration = 4;
    public float trailSize = 8;
    public float trailAlpha = 255;

    public BulletEntity create(){
        return new BulletEntity(this);
    }

    /** Represents and simulates a bullet. */
    public class BulletEntity extends Entity{
        public Bullet bullet;

        public float rotation, speed = 1f;
        public Vec2 pPos = new Vec2();

        public Ship origin;

        public Set<Entity> collided = new Set<>();

        public BulletEntity(Bullet type){
            super(null);
            this.bullet = type;
        }

        @Override
        public float size(){
            return size;
        }

        @Override
        public float fin(){
            return life / lifetime;
        }

        @Override
        public Color color(){
            return origin.color();
        }

        public float speed(){
            return (bullet.speed + rules.add(bulletSpeed, origin.team)) * rules.mult(bulletSpeed, origin.team);
        }

        public float knockback(){
            return (knockback + rules.add(bulletKnockback, origin.team)) * rules.mult(bulletKnockback, origin.team);
        }

        public float damage(){
            return (damage + rules.add(bulletDamage, origin.team)) * rules.mult(bulletDamage, origin.team);
        }

        public float blastRadius(){
            return (splashRadius + rules.add(blastRadius, origin.team)) * rules.mult(blastRadius, origin.team);
        }

        public float blastDamage(){
            return damage() * (splashDamage + rules.add(blastDamage, origin.team)) * rules.mult(blastDamage, origin.team) / 10f;
        }

        public void hit(Ship s){
            if(damage() < 0) return;

            collided.add(s);

            if(s.team != Team.player) world.player.lastHit = s;

            if(blastRadius() > 0){
                canvas.shake(rt2(blastRadius()));

                world.ships.query(pos.x, pos.y, blastRadius() + maxEntitySize, e -> {
                    if(e.team != team && dst(e, pos) < e.size() + blastRadius()) e.damage(blastDamage() * (1f - dst(e, pos) / (e.size() + blastRadius())));
                });

                Effects.shockwave.at(pos.x, pos.y, e -> e.color(0, color()).set(3, blastRadius() / 2).set(4, 3).lifetime(rt2(blastRadius()) * 2.5f));
                Effects.explosion.at(pos.x, pos.y, e -> e.color(0, color()).set(3, blastRadius() / 2));
            }else Effects.fragment.at(pos.x, pos.y, e -> e.color(20, color()).set(23, size() * 2));

            s.apply(Tmp.v1.set(vel).scl(knockback()));
            s.damage(damage());
        }

        @Override
        public void init(){
            speed *= speed();
            pPos.set(pos);
        }

        @Override
        public void update(){
            life += delta;

            vel.set(speed, 0).rot(rotation);

            world.ships.raycast(pos.x, pos.y, size + maxEntitySize, rotation, speed, (s, pos) -> {
                if(s.team != this.team && !collided.contains(s) && dst(s, pos) < s.size() + size) hit(s);
            });

            super.update();

            if(trailDuration > 0) Effects.trail.at(pos.x, pos.y, e -> e.color(0, color()).set(3, pPos.x).set(4, pPos.y).set(5, trailSize).lifetime(trailDuration));
            pPos.set(pos);
        }

        @Override
        public void draw(){
            sprite.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, color());
            sprite.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, Color.white, 200);
        }

        @Override
        public boolean keep(){
            return world.bounds.contains(pos) && collided.size <= 0 && (life < lifetime || lifetime <= 0);
        }
    }

    public class BulletSprite extends Sprite{
        public BulletSprite set(String name){
            super.set(SpritePath.bullets, name);
            return this;
        }
    }
}
