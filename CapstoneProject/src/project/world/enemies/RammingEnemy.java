package project.world.enemies;

import gameutils.math.*;
import project.*;
import project.core.*;
import project.world.bullets.Bullet.*;
import project.world.bullets.*;
import project.world.enemies.EnemyPart.*;
import project.world.enemies.RammingEnemy.RammingSide.*;
import project.world.enemies.RammingEnemy.RammingThruster.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class RammingEnemy extends MultiEnemy{
    public RammingSide side;
    public RammingThruster thruster;

    public RammingEnemy(){
        super();

        sprite.set("juggernaut-3");

        accel = 0.5f;
        rotate = 0.5f;
        color = new Color(255, 0, 120);
        health = 750;
        size = 25;
    }

    public void init(){
        if(side == null) side = new RammingSide();
        if(thruster == null) thruster = new RammingThruster();

        pieces.addAll(side, side, thruster);

        super.init();
    }

    @Override
    public RammingEnemyEntity create(){
        return new RammingEnemyEntity(this);
    }

    public class RammingEnemyEntity extends MultiEnemyEntity{
        public RammingEnemyEntity(RammingEnemy type){
            super(type);
        }

        @Override
        public void damage(float damage){
            for(EnemyPartEntity p : parts)
                if(p.keep()){
                    super.damage(damage / 5);
                    return;
                }
            super.damage(damage);
        }

        public RammingSideEntity side1(){
            return (RammingSideEntity)parts.get(0);
        }

        public RammingSideEntity side2(){
            return (RammingSideEntity)parts.get(1);
        }

        public RammingThrusterEntity thruster(){
            return (RammingThrusterEntity)parts.get(2);
        }

        public void positions(){
            side1().pos.set(Tmp.v1.set(side1().type().offset).rot(rotation + 90).add(pos));
            side2().pos.set(Tmp.v1.set(side2().type().offset).scl(-1, 1).rot(rotation + 90).add(pos));
            thruster().pos.set(Tmp.v1.set(thruster().type().offset).rot(rotation + 90).add(pos));
        }

        @Override
        public void init(){
            super.init();

            side2().flip = true;

            side1().reloadt = side2().reloadt = random(0, 60);
            thruster().reloadt = random(0, 60);

            positions();
        }

        @Override
        public void update(){
            super.update();

            rotate(Tmp.v1.set(world.player.pos).sub(pos).ang());
            thrust();

            positions();

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

    public class RammingSide extends EnemyPart{
        public EnemySprite flipSprite = new EnemySprite();

        public Vec2 offset = new Vec2(-22, 42);

        public int shootDuration = 30;
        public int shootInterval = 3;
        public float barrel = 240;
        public float inaccuracy = 20;

        public float velRand = 0.7f;

        public RammingSide(){
            super();

            sprite.set("juggernaut-side-1");
            flipSprite.set("juggernaut-side-2");
            reload = 0.5f;

            size = 15;

            bullet = new MissileBullet(){{
                speed = 5;
                accel = 0.1f;
                homingPower = 0.03f;
                homingRange = 2000;
                lifetime = 4 * 60f;
            }};
        }

        @Override
        public void init(){
            super.init();
        }

        @Override
        public RammingSideEntity create(){
            return new RammingSideEntity(this);
        }

        public class RammingSideEntity extends EnemyPartEntity{
            public boolean flip;

            public RammingSideEntity(RammingSide type){
                super(type);
            }

            @Override
            public void update(){
                super.update();

                rotation = parent.rotation;

                reloadt += reload();
                if(reloadt >= 60 && (reloadt - 60) % shootInterval < reload()){
                    BulletEntity b = shoot((flip ? 360 - barrel : barrel) + random(-inaccuracy, inaccuracy));
                    if(b != null) b.speed *= random(velRand, 1f);
                }
                if(reloadt >= 60 + shootDuration * reload()) reloadt = 0;
            }

            @Override
            public void glow(){
            }

            @Override
            public void draw(){
                if(flip) flipSprite.drawc(pos.x, pos.y, size() * 5, size() * 5, rotation, Color.white);
                else super.draw();
            }

            @Override
            public RammingSide type(){
                return (RammingSide)type;
            }

            @Override
            public boolean keep(){
                return life > 0;
            }
        }
    }

    public class RammingThruster extends EnemyPart{
        public Vec2 offset = new Vec2(0, 50);

        public float ramPower = 200;

        public RammingThruster(){
            super();

            sprite.set("juggernaut-thruster");

            reload = 0.1f;
            size = 18;
        }

        @Override
        public RammingThrusterEntity create(){
            return new RammingThrusterEntity(this);
        }

        public class RammingThrusterEntity extends EnemyPartEntity{
            public RammingThrusterEntity(RammingThruster type){
                super(type);
            }

            @Override
            public void update(){
                super.update();

                rotation = parent.rotation;

                reloadt += reload();
                if(reloadt >= 60){
                    reloadt = 0;
                    apply(Tmp.v1.set(ramPower, 0).rot(rotation));
                }
            }

            @Override
            public void glow(){
            }

            @Override
            public RammingThruster type(){
                return (RammingThruster)type;
            }

            @Override
            public boolean keep(){
                return life > 0;
            }
        }
    }
}