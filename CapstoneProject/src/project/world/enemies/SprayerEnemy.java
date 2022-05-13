package project.world.enemies;

import project.content.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.bullets.*;
import project.world.bullets.Bullet.*;

import java.awt.*;

import static project.Vars.*;

public class SprayerEnemy extends Enemy{
    public float shootDuration = 120;
    public float shootRotation = 1;
    public float shootReload = 10;

    public int bullets = 8;
    public float offset = 20;

    public SprayerEnemy(){
        super();

        accel = 0.02f;
        color = new Color(255, 110, 50);
        bullet = new VolleyBullet(){{
            speed = 5;
        }};
        reload = 0.25f;
        size = 10;
    }

    @Override
    public void init(){
        if(sprite == null) sprite = new Sprite(SpritePath.enemies, "gyrogun-1");

        super.init();
    }

    @Override
    public SprayerEnemyEntity create(){
        return new SprayerEnemyEntity(this);
    }

    public class SprayerEnemyEntity extends EnemyEntity{
        public int shootingTimer = 1000;
        public float shootReloadt = 0;

        public SprayerEnemyEntity(SprayerEnemy type){
            super(type);
        }

        @Override
        public void thrust(float accel){
            if(shootingTimer < shootDuration) accel /= 10;
            super.thrust(accel);
        }

        @Override
        public void rotate(float angle, float speed){
            if(shootingTimer >= shootDuration) super.rotate(angle, speed);
        }

        @Override
        public void update(){
            super.update();

            shootingTimer++;

            if(shootingTimer < shootDuration){
                rotation += shootRotation;
                shootReloadt += shootReload * rules.weaponReload(team);

                if(shootReloadt >= 60){
                    shootReloadt = 0;
                    for(int i = 0;i < bullets;i++){
                        BulletEntity b = bullet.create();
                        b.pos.set(pos);
                        b.team = team;
                        b.rotation = rotation + 360f * i / bullets;
                        b.origin = this;
                        world.bullets.add(b);
                    }
                }
            }
        }

        @Override
        public void shoot(){
            if(shootingTimer < shootDuration) return;

            reloadt += reload * rules.weaponReload(team);

            if(reloadt >= 60){
                reloadt %= 60;
                shootingTimer = 0;
            }
        }
    }
}
