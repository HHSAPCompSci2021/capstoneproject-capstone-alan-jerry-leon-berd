package project.world.bullets;

import java.awt.*;
import project.content.*;
import project.graphics.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Contains presets for a lance bullet. */
public class LanceBullet extends Bullet{
    public float damageInterval = 2;

    public LanceBullet(){
        super();

        sprite.set("lance");
        size = 10;
        speed = 40;
        lifetime = 120;
        damage = 2;
        reflectable = false;
    }

    public LanceBulletEntity create(){
        return new LanceBulletEntity(this);
    }

    /** Represents and simulates a lance bullet. */
    public class LanceBulletEntity extends BulletEntity{
        public LanceBulletEntity(LanceBullet type){
            super(type);
        }

        @Override
        public float fin(){
            return rt(super.fin() < 0.5f ? super.fin() * 2 : 2f - super.fin() * 2f, 5);
        }


        protected float length(){
            return size * 10 * speed / 10 * fin();
        }

        @Override
        public float damage(){
            return super.damage();
        }

        @Override
        public void init(){
            super.init();
            lifetime = bullet.lifetime;
            rotation -= origin.rotation();
        }

        @Override
        public void update(){
            pos.set(origin.pos);
            life += delta;

            if(life % damageInterval < 1){
                collided.clear();
                world.ships.raycast(pos.x, pos.y, size + maxEntitySize, rotation + origin.rotation(), length(), (s, pos) -> {
                    if(s.team != team && s.keep() && !collided.contains(s) && dst(s, pos) < s.size() + size){
                        collided.add(s);
                        this.pos.set(pos);
                        s.entry(StatusEffects.vulnerable, 20);
                        hit(s);
                    }
                });
            }
            if(!origin.statuses().contains(t -> t.type() == StatusEffects.slow)) origin.entry(StatusEffects.slow, bullet.lifetime);
        }

        @Override
        public void draw(){
            sprite.drawc(origin.pos.x, origin.pos.y, size * 15 * fin(), length() * 2.5f, origin.rotation() + rotation + 90, origin.color(), 50);
            sprite.drawc(origin.pos.x, origin.pos.y, size * 13 * fin(), length() * 2.3f, origin.rotation() + rotation + 90, origin.color(), 100);
            sprite.drawc(origin.pos.x, origin.pos.y, size * 11 * fin(), length() * 2.1f, origin.rotation() + rotation + 90, Color.white, 100);
            sprite.drawc(origin.pos.x, origin.pos.y, size * 9 * fin(), length() * 1.9f, origin.rotation() + rotation + 90, Color.white, 200);

            Effects.glow.drawc(origin.pos.x, origin.pos.y, size * 5 * fin(), size * 15 * fin(), origin.rotation() + rotation, origin.color(), 100);
            Effects.glow.drawc(origin.pos.x, origin.pos.y, size * 3 * fin(), size * 10 * fin(), origin.rotation() + rotation, Color.white, 200);
        }

        @Override
        public boolean keep(){
            return life < lifetime && origin.keep();
        }
    }
}