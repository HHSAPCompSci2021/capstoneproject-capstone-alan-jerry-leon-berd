package project.world.enemies;

import project.content.*;
import project.core.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.bullets.*;
import project.world.bullets.Bullet.*;

import java.awt.*;

import static project.Vars.*;

/** An enemy which rotates and spams bullets around itself. */
public class SprayerEnemy extends Enemy{
    public static Sprite defSprite = new Sprite(SpritePath.enemies, "gyrogun-1");
    public static Bullet defBullet = new VolleyBullet(){{
        size = 2;
        speed = 5;
    }};

    public float shootDuration = 120;
    public float shootInterval = 3;

    public int bullets = 2;
    /** Angle offset of each of the shots. */
    public float offset = 120;

    public SprayerEnemy(){
        super();

        bullet = defBullet;
        sprite = defSprite;
        accel = 0.5f;
        color = new Color(255, 110, 50);
        health = 50;
        reload = 0.25f;
        rotate = 1f;
        size = 10;
    }

    @Override
    public void init(){
        super.init();
    }

    @Override
    public SprayerEnemyEntity create(){
        return new SprayerEnemyEntity(this);
    }

    /** Represents and simulates an entity that rotates and spams bullets around itself. */
    public class SprayerEnemyEntity extends EnemyEntity{
        public SprayerEnemyEntity(SprayerEnemy type){
            super(type);
        }

        @Override
        public void update(){
            super.update();

            reloadt += reload();
            rotation += rotate();

            if(reloadt >= 60){
                if((reloadt - 60) % shootInterval < reload()){
                    for(int i = 0;i < bullets;i++) shoot(offset + 360f * i / bullets);
                }
            }
            if(reloadt >= 60 + shootDuration * reload()) reloadt = 0;
        }
    }
}
