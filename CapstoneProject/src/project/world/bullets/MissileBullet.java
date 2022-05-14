package project.world.bullets;

import gameutils.struct.prim.*;
import gameutils.util.*;
import project.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.ship.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Represents a missile bullet */
public class MissileBullet extends Bullet{
    public float accel = 0.4f;

    public float homingPower = 0.02f;
    public float homingRange = 200;

    public float waveFrequency = 1.5f;
    public float waveAmplitude = 2;

    public MissileBullet(){
        super();
        damage = 10;
        speed = 1f;
        size = 4;
        knockback = 0.01f;
        lifetime = 5 * 60 * 1.5f;
        trailDuration = 5;
    }

    @Override
    public void init(){
        super.init();

        if(sprite == null) sprite = new Sprite(SpritePath.bullets, "salvo");
    }

    @Override
    public MissileBulletEntity create(){
        return new MissileBulletEntity(this);
    }

    public class MissileBulletEntity extends BulletEntity{
        public Ship target;

        public MissileBulletEntity(MissileBullet type){
            super(type);
        }

        @Override
        public void init(){
            life = random(-180, 0);
        }

        @Override
        public void update(){
            life += random(0, 10);
            speed += accel;

            super.update();

            if(target == null || !target.keep()){
                rotation += sin(life * waveFrequency) * waveAmplitude;
                world.ships.query(pos.x, pos.y, homingRange, e -> {
                    if(e.team != team) target = e;
                });
            }else{
                float wanted = Tmp.v1.set(target.pos).sub(pos).ang();
                rotation = Mathf.turn(rotation, wanted, homingPower * speed * speed);
            }
        }

        @Override
        public void draw(){
            super.draw();

            Tmp.v1.setr(rotation, -7f).add(pos);
            canvas.fill(origin.color(), 30);
            canvas.ellipse(Tmp.v1.x, Tmp.v1.y, size() * 8, size() * 8);
        }
    }
}
