package project.world.bullets;

import gameutils.struct.*;
import gameutils.util.*;
import project.core.Content.*;
import project.graphics.*;
import project.graphics.Sprite.*;
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
    public float damage = 20;
    public float knockback = 0.05f;
    public int pierce = 1;

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
            speed *= type().speed * rules.bulletSpeed(team);
        }

        @Override
        public void update(){
            pos.add(vel.set(speed, 0).rot(rotation));

            world.ships.raycast(pos.x, pos.y, size + maxEntitySize, rotation, speed, (s, pos) -> {
                if(s.team != this.team && !collided.contains(s) && dst(s, pos) < s.size() + size){
                    collided.add(s);
                    s.apply(tmp.set(vel).scl(knockback * rules.bulletKnockback(team)));
                    s.damage(damage * rules.bulletDamage(team));
                }
            });
        }

        @Override
        public void draw(){
            Effects.glow.drawc(pos.x, pos.y, size() * 15, size() * 15, origin.color(), 30);

            if(sprite == null) return; //TODO: Sprites for everything
            sprite.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, origin.color());
            sprite.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, Color.white, 100);
        }

        @Override
        public void remove(){
            if(world.bounds.contains(pos)) Effects.explosion.at(pos.x, pos.y, e -> e.scale(size));
        }

        @Override
        public boolean keep(){
            return world.bounds.contains(pos) && collided.size < pierce;
        }

        @Override
        public Bullet type(){
            return (Bullet)type;
        }
    }
}
