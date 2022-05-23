package project.world.enemies;

import gameutils.math.*;
import project.*;
import project.graphics.Sprite.*;
import project.world.bullets.Bullet.*;
import project.world.bullets.*;
import project.world.enemies.EnemyPart.*;
import project.world.enemies.RammingEnemy.RammingSide.*;
import project.world.enemies.RammingEnemy.RammingThruster.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores stats for a ramming enemy. */
public class RammingEnemy extends MultiEnemy{
    protected RammingSide side;
    protected RammingThruster thruster;

    /** Create a new RammingEnemy. */
    public RammingEnemy(){
        super();

        // glow = new EnemySprite();
        sprite.set("juggernaut-1-p");

        accel = 0.5f;
        rotate = 0.5f;
        color = new Color(255, 0, 120);
        health = 750;
        size = 50;
    }

    @Override
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

    protected static class RammingThruster extends EnemyPart{
        protected Vec2 offset = new Vec2(0, 50);

        protected float ramPower = 200;

        public RammingThruster(){
            super();

//            glow = new EnemySprite();
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

                rotation = parent.rotation();

                reloadt += reload();
                if(reloadt >= 60){
                    reloadt = 0;
                    apply(Tmp.v1.set(ramPower, 0).rot(rotation));
                }
            }

            @Override
            public void glow(){
                if(glow != null) glow.drawc(pos.x, pos.y, size() * 5, size() * 5, rotation, Color.white);
            }

            @Override
            public RammingThruster type(){
                return (RammingThruster)type;
            }

            @Override
            public boolean keep(){
                return life > 0 && parent.keep();
            }
        }
    }

    protected class RammingEnemyEntity extends MultiEnemyEntity{
        public RammingEnemyEntity(RammingEnemy type){
            super(type);
            deathSound = "car_explosion.mp3";
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
        public boolean keep(){
            return life > 0;
        }
    }

    protected class RammingSide extends EnemyPart{
        protected EnemySprite flipGlow;
        protected EnemySprite flipSprite = new EnemySprite();

        protected Vec2 offset = new Vec2(-22, 42);

        protected int shootDuration = 30;
        protected int shootInterval = 3;
        protected float barrel = 240;
        protected float inaccuracy = 20;

        protected float velRand = 0.3f;

        public RammingSide(){
            super();

            // glow = new EnemySprite();
            // flipGlow = new EnemySprite();
            sprite.set("juggernaut-side-1-p");
            flipSprite.set("juggernaut-side-2-p");

            reload = 0.5f;
            size = 15;

            bullet = new MissileBullet(){{
                speed = 5;
                accel = 0.15f;
                homingPower = 0.03f;
                homingRange = 2000;
                lifetime = 4 * 60f;
            }};
        }

        @Override
        public void init(){
            super.init();

            if(flipGlow != null) flipGlow.set(SpritePath.none, flipSprite.path.substring(0, flipSprite.path.length() - 4) + "-glow");
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

                rotation = parent.rotation();

                reloadt += reload();
                if(reloadt >= 60 && (reloadt - 60) % shootInterval < reload()){
                    BulletEntity b = shoot((flip ? 360 - barrel : barrel) + random(-inaccuracy, inaccuracy));
                    if(b != null) b.speed *= random(1f - velRand, 1f);
                }
                if(reloadt >= 60 + shootDuration * reload()) reloadt = 0;
            }

            @Override
            public void glow(){
                if(!flip && glow != null) glow.drawc(pos.x, pos.y, size() * 5, size() * 5, rotation, Color.white);
                else if(flip && flipGlow != null) flipGlow.drawc(pos.x, pos.y, size() * 5, size() * 5, rotation, Color.white);
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
                return life > 0 && parent.keep();
            }
        }
    }
}