package project.world.enemies;

import project.*;
import project.core.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.bullets.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class TeleportingEnemy extends Enemy{
    public float kiteDistance = 500;

    public TeleportingEnemy(){
        super();

//        bullet = new LaserBullet(){{
//            rotate = 2f;
//            damage = 10;
//        }};
        sprite.set("tracer"); //leon change/make the sprite pls
        accel = 0.5f;
        rotate = 1f;
        color = new Color(255, 120, 0);
        health = 100;
        size = 20;
    }

    public void init(){
        super.init();
    }

    @Override
    public TeleportingEnemyEntity create(){
        return new TeleportingEnemyEntity(this);
    }

    public class TeleportingEnemyEntity extends EnemyEntity{
        public int run = -1;

        public TeleportingEnemyEntity(TeleportingEnemy type){
            super(type);
        }

        @Override
        public void damage(float damage){
            run = 0;
            super.damage(damage);
        }

        @Override
        public void update(){
            super.update();

            reloadt += reload();
            if(reloadt >= 120){
                reloadt = 0;
                shoot(0);
            }
            if(run > -1){
                run++;
                rotate(Tmp.v1.set(world.player.pos).sub(pos).ang() + 90);
                thrust();
                if(run >= 120){
                    run = -1;
                }
            }else rotate(Tmp.v1.set(world.player.pos).sub(pos).ang());
            if(dst(world.player.pos, pos) > kiteDistance) thrust();

            wrap();
        }

        @Override
        public void remove(){
            super.remove();

            Sounds.playSound("car_explosion.mp3");
        }

        @Override
        public boolean keep(){
            return life > 0;
        }
    }
}