package project.world.bullets;

import project.graphics.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Contains stats for a laser bullet. */
public class LaserBullet extends Bullet{
    public LaserBullet(){
        super();
        damage = 50;
        size = 5;
    }

    public LaserBulletEntity create(){
        return new LaserBulletEntity(this);
    }

    /** Represents and simulates a laser bullet. */
    public class LaserBulletEntity extends BulletEntity{
        public LaserBulletEntity(LaserBullet type){
            super(type);
        }

        @Override
        public void init(){
            world.ships.raycast(pos.x, pos.y, size + maxEntitySize, rotation, maxLineLen, (s, pos) -> {
                if(s.team != team && s.keep() && !collided.contains(s) && dst(s, pos) < s.size() + size){
                    collided.add(s);
                    s.damage(damage * rules.bulletDamage(team));
                    Effects.explosion.at(pos.x, pos.y, e -> e.scale(size / 2));
                }
            });

            Effects.laserLine.at(pos.x, pos.y, e -> e.set(0, rotation).scale(size));
        }

        @Override
        public void update(){
        }

        @Override
        public void draw(){
        }

        @Override
        public void remove(){
        }

        @Override
        public boolean keep(){
            return false;
        }
    }
}
