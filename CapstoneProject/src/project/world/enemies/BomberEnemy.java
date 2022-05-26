package project.world.enemies;

import project.*;
import project.world.bullets.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores stats for a carpet bombing enemy. */
public class BomberEnemy extends Enemy{
    protected float thrustDuration = 60;
    protected float shootInterval = 10;

    protected float inaccuracy = 20;
    protected float waveFrequency = 10;
    protected float waveAmplitude = 5;

    public BomberEnemy(){
        super();

        sprite.set("tracer-1");
        bullet = new GrenadeBullet(){{
            size = 2;
            damage = 25;
            splashRadius = 25;
            speed = 3;
        }};

        health = 40;
        color = new Color(255, 120, 0);
        reload = 1;
        rotate = 3;
        accel = 0.5f;
        size = 15;
    }

    @Override
    public BomberEnemyEntity create(){
        return new BomberEnemyEntity(this);
    }

    public class BomberEnemyEntity extends EnemyEntity{
        protected float wave;

        public BomberEnemyEntity(BomberEnemy type){
            super(type);
        }

        @Override
        public void init(){
            super.init();

            reloadt = random(0, 60);
        }

        @Override
        public void update(){
            super.update();

            if(spawned()) return;

            reloadt += reload();
            if(reloadt < 60) rotate(Tmp.v1.set(world.player.pos).sub(pos).ang());
            else{
                if((reloadt - 60) % shootInterval < reload()) shoot(180 + random(-inaccuracy, inaccuracy));
                rotation += sin(wave) * waveAmplitude * delta;
                wave += waveFrequency;
                thrust();
                float thrustDuration = 60;
                if(reloadt >= 60 + thrustDuration) reloadt = 0;
            }

            wrap();
        }

        @Override
        public boolean keep(){
            return life > 0;
        }
    }
}
