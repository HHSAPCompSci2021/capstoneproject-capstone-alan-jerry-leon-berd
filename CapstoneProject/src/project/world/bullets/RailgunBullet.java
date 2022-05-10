package project.world.bullets;

import gameutils.math.*;
import gameutils.util.*;

import static project.Vars.*;

/** Contains stats for a railgun bullet. */
public class RailgunBullet extends Bullet{
    public RailgunBullet(){
        super();
        speed = 25;
        damage = 100;
        pierce = 5;
    }

    @Override
    public RailgunBulletEntity create(){
        return new RailgunBulletEntity(this);
    }

    /** Represents and simulates a railgun bullet. */
    public class RailgunBulletEntity extends BulletEntity{
        public Vec2 pPos = new Vec2();

        public RailgunBulletEntity(RailgunBullet type){
            super(type);
        }

        @Override
        public void init(){
            super.init();
            pPos.set(pos);
        }

        @Override
        public void update(){
            pPos.set(pos);
            super.update();
        }

        @Override
        public void draw(){
            canvas.stroke(255);
            canvas.strokeWeight(size);
            canvas.line(pos.x, pos.y, pPos.x, pPos.y);

            if(glowRendered > 0) canvas.glows(size * 10, 1, origin.color(), 3, Interpf.pow5, i -> {
                if(i > size){
                    canvas.strokeWeight(i);
                    canvas.line(pos.x, pos.y, pPos.x, pPos.y);
                }
            });
        }
    }
}
