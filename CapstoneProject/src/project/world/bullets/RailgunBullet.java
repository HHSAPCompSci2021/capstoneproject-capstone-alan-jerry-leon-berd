package project.world.bullets;

import gameutils.math.*;

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
        /** The last position of this bullet. */
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
        }
    }
}
