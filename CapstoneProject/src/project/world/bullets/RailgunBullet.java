package project.world.bullets;

import gameutils.math.*;
import project.world.ship.*;

import java.awt.*;

import static project.Vars.*;

/** Contains stats for a railgun bullet. */
public class RailgunBullet extends Bullet{
    public RailgunBullet(){
        super();

        sprite.set("railgun");

        size = 10;
        speed = 75;
        damage = 100;
        trailDuration = 20;
        trailSize = 5;
    }

    @Override
    public RailgunBulletEntity create(){
        return new RailgunBulletEntity(this);
    }

    /** Represents and simulates a railgun bullet. */
    public class RailgunBulletEntity extends BulletEntity{
        public float spent;

        public RailgunBulletEntity(RailgunBullet type){
            super(type);
        }

        @Override
        public float damage(){
            return (super.damage() * speed / bullet.speed) - spent;
        }

        @Override
        public void init(){
            super.init();
        }

        @Override
        public void hit(Ship s){
            float tmp = s.life;
            super.hit(s);
            spent += tmp;
        }

        @Override
        public void draw(){
            sprite.drawc(pos.x, pos.y, size() * 10, speed * 2, rotation + 90, color());
            sprite.drawc(pos.x, pos.y, size() * 10, speed * 2, rotation + 90, Color.white, 200);
        }

        @Override
        public boolean keep(){
            return world.bounds.contains(pos) && (life < lifetime || lifetime <= 0) && damage() > 0;
        }
    }
}
