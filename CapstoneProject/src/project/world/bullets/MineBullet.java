package project.world.bullets;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Contains stats for a mine bullet. */
public class MineBullet extends Bullet{
    public float lifetime = 600;
    public float drag = 0.95f;
    public float accel = 0.1f;
    public float range = 25;

    @Override
    public MineBulletEntity create(){
        return new MineBulletEntity(this);
    }

    /** Represents and simulates a mine bullet. */
    public class MineBulletEntity extends BulletEntity{
        public float drawRot, rotVel;

        public MineBulletEntity(MineBullet type){
            super(type);
            rotVel = random(-50, 50);
        }

        @Override
        public void update(){
            drawRot += rotVel;

            super.update();

            world.ships.query(pos.x, pos.y, range + maxEntitySize, e -> {
                if(keep() && e.team != team && dst(e.pos, pos) < range + e.size()) explode();
            });

            speed *= drag;
            rotVel *= drag;
        }

        @Override
        public void draw(){
            float tmp = rotation;
            rotation = drawRot;
            super.draw();
            rotation = tmp;
        }

        public void explode(){
            life = lifetime;

            world.ships.query(pos.x, pos.y, range + maxEntitySize, e -> {
                if(e.team != team) e.damage(damage);
            });
        }

        @Override
        public boolean keep(){
            return super.keep() && life < lifetime;
        }
    }
}
