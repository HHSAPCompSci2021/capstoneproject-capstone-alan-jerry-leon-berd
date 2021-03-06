package project.world.bullets;

import project.*;
import project.world.ship.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Contains presets for a missile bullet. */
public class MissileBullet extends Bullet{
    protected float accel = 0.4f;

    protected float homingPower = 0.02f;
    protected float homingRange = 500;

    protected float waveFrequency = 1.5f;
    protected float waveAmplitude = 2;

    public MissileBullet(){
        super();

        sprite.set("salvo");
        damage = 10;
        speed = 1f;
        size = 4;
        knockback = 0.01f;
        lifetime = 2f * 60;
        trailDuration = 8;
    }

    @Override
    public MissileBulletEntity create(){
        return new MissileBulletEntity(this);
    }

    /** Represents and simulates a missile bullet. */
    public class MissileBulletEntity extends BulletEntity{
        public byte dir;
        public Ship target;

        public MissileBulletEntity(MissileBullet type){
            super(type);
        }

        @Override
        public void init(){
            super.init();
            life = random(-360 / waveFrequency, 0);
            dir = (byte)(randInt(0, 1) == 0 ? -1 : 1);
        }

        @Override
        public void update(){
            life += random(0, 2) * dir;
            speed += accel * delta;

            super.update();

            rotation += sin(life * waveFrequency) * waveAmplitude * delta;
            if(target == null || !target.keep() || target.team == team){
                world.ships.query(pos.x, pos.y, homingRange, e -> {
                    if(e.team != team) target = e;
                });
            }else{
                float wanted = Tmp.v1.set(target.pos).sub(pos).ang();
                rotation = turn(rotation, wanted, homingPower * speed * speed);
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
