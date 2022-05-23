package project.world.enemies;

import project.*;
import project.world.bullets.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class SniperEnemy extends Enemy{
    public float thrustDuration = 300;
    public float shootInterval = 10;
    public float kiteDistance = 400;

    public SniperEnemy(){
        super();

        sprite.set("beam-caster");
        bullet = new RailgunBullet(){{
            size = 6;
            damage = 25;
            splashRadius = 25;
            speed = 30;
        }};

        health = 70;
        color = new Color(255, 110, 200);
        reload = 1;
        rotate = 3;
        accel = 0.2f;
    }

    @Override
    public SniperEnemyEntity create(){
        return new SniperEnemyEntity(this);
    }

    public class SniperEnemyEntity extends EnemyEntity{

        public SniperEnemyEntity(SniperEnemy type){
            super(type);
            deathSound = "laser_impact.mp3";
        }

        @Override
        public void init(){
            super.init();

            reloadt = 250;
        }

        @Override
        public void update(){
            super.update();

            if(spawned()) return;

            reloadt += reload();
        	rotate(Tmp.v1.set(world.player.pos).sub(pos).ang());
            if(reloadt < 300) {
                if(dst(world.player.pos, pos) > kiteDistance) thrust();
            }
            if(reloadt >= 300){
                reloadt = 0;
                shoot(0);
            }
            wrap();
        }

        @Override
        public boolean keep(){
            return life > 0;
        }
    }
}
