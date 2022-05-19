package project.world.bullets;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class LanceBullet extends Bullet{
    public float damageInterval = 5;

    public LanceBullet(){
        super();

        sprite.set("lance");
        size = 10;
        speed = 40;
        lifetime = 120;
        damage = 15;
    }

    public LanceBulletEntity create(){
        return new LanceBulletEntity(this);
    }

    /** Represents and simulates a laser bullet. */
    public class LanceBulletEntity extends BulletEntity{
        public float damageScale = 1f;

        public LanceBulletEntity(LanceBullet type){
            super(type);
        }

        public float length(){
            float fin = life / lifetime;
            return size * 10 * speed / 10 * rt(fin < 0.5f ? fin * 2 : 2f - fin * 2f, 5);
        }

        @Override
        public float damage(){
            return super.damage() * damageScale;
        }

        @Override
        public void init(){
            super.init();
            lifetime = bullet.lifetime;
            rotation -= origin.rotation;
        }

        @Override
        public void update(){
            pos.set(origin.pos);
            life ++;

            if(life % damageInterval < 1){
                collided.clear();
                world.ships.raycast(pos.x, pos.y, size + maxEntitySize, rotation + origin.rotation, length(), (s, pos) -> {
                    if(s.team != team && s.keep() && !collided.contains(s) && dst(s, pos) < s.size() + size){
                        collided.add(s);
                        this.pos.set(pos);
                        hit(s);
                    }
                });
            }
        }

        @Override
        public void draw(){
            float fin = life / lifetime;
            float scl = rt(fin < 0.5f ? fin * 2 : 2f - fin * 2f, 5);
            sprite.drawc(origin.pos.x, origin.pos.y, size * 15 * scl, length() * 2.5f, origin.rotation + rotation + 90, origin.color(), 50);
            sprite.drawc(origin.pos.x, origin.pos.y, size * 13 * scl, length() * 2.3f, origin.rotation + rotation + 90, origin.color(), 100);
            sprite.drawc(origin.pos.x, origin.pos.y, size * 11 * scl, length() * 2.1f, origin.rotation + rotation + 90, Color.white, 100);
            sprite.drawc(origin.pos.x, origin.pos.y, size * 9 * scl, length() * 1.9f, origin.rotation + rotation + 90, Color.white, 200);
        }

        @Override
        public boolean keep(){
            return life < lifetime;
        }
    }
}