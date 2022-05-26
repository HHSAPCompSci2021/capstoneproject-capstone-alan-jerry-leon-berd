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
    protected BulletSprite sprite = new BulletSprite().set("blast");

    protected float speed = 10;
    protected float size = 5;
    protected float damage = 10;
    protected float knockback = 0.05f;
    protected float lifetime = -1;
    protected float splashRadius = 0;
    protected float splashDamage = 0;

    protected int trailDuration = 4;
    protected float trailSize = 8;
    protected float trailAlpha = 255;

    protected boolean reflectable = true;

    /**
     * Returns whether this bullet is reflectable or not.
     * @return whether this bullet is reflectable or not.
     */
    public boolean reflectable(){
        return reflectable;
    }

    /**
     * Returns the damage of this bullet
     * @return the damage
     */
    public float damage(){
        return damage;
    }

    /**
     * Returns the lifetime of this bullet
     * @return the lifetime
     */
    public float lifetime(){
        return lifetime;
    }

    /**
     * Creates a bullet entity with this type
     * @return the bullet entity
     */
    public BulletEntity create(){
        return new BulletEntity(this);
    }

    /** Represents and simulates a bullet. */
    public class BulletEntity extends Entity{
        /** The bullet that is the type of this entity. */
        public Bullet bullet;

        /** Rotation, damage multiplier, and speed, respectively. */
        public float rotation, damage = 1f, speed = 1f;
        protected Vec2 pPos = new Vec2();

        protected Ship origin;

        protected Set<Entity> collided = new Set<>();

        /**
         * Creates a bullet entity with the specified type
         * @param type the bullet type
         */
        public BulletEntity(Bullet type){
            super(null);
            this.bullet = type;
        }

        /**
         * Ses the origin of this bullet
         * @param origin the origin
         * @return
         */
        public BulletEntity origin(Ship origin){
            this.origin = origin;
            return this;
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

        protected float speed(){
            return (bullet.speed + rules.add(bulletSpeed, origin.team)) * rules.mult(bulletSpeed, origin.team);
        }

        protected float knockback(){
            return (knockback + rules.add(bulletKnockback, origin.team)) * rules.mult(bulletKnockback, origin.team);
        }

        protected float damage(){
            return (damage + rules.add(bulletDamage, origin.team)) * rules.mult(bulletDamage, origin.team);
        }

        protected float blastRadius(){
            return (splashRadius + rules.add(blastRadius, origin.team)) * rules.mult(blastRadius, origin.team);
        }

        protected float blastDamage(){
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
            damage *= bullet.damage;
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
            sprite.drawc(pos.x + 5, pos.y + 5, size() * 5, size() * 5, rotation + 90, Color.black, 50);

            sprite.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, color());
            sprite.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, Color.white, 200);
        }

        @Override
        public boolean keep(){
            return world.bounds.contains(pos) && collided.size <= 0 && (life < lifetime || lifetime <= 0);
        }
    }

    protected static class BulletSprite extends Sprite{
        public BulletSprite set(String name){
            super.set(SpritePath.bullets, name);
            return this;
        }
    }
}
