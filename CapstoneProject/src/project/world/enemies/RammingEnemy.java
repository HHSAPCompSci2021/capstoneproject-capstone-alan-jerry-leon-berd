package project.world.enemies;

import gameutils.math.*;
import project.*;
import project.core.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.bullets.*;
import project.world.bullets.Bullet.*;
import project.world.enemies.EnemyPart.*;
import project.world.enemies.RammingEnemy.RammingSide.*;
import project.world.enemies.RammingEnemy.RammingThruster.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class RammingEnemy extends MultiEnemy{
    public RammingSide side1, side2;
    public RammingThruster thruster;

    public RammingEnemy(){
        super();

        accel = 0.5f;
        rotate = 0.5f;
        color = new Color(255, 0, 120);
        health = 1000;
        size = 50;
        ram = 2;
    }

    public void init(){
        if(sprite == null) sprite = new Sprite(SpritePath.enemies, "juggernaut");
        if(side1 == null) side1 = new RammingSide();
        if(side2 == null) side2 = new RammingSide(){{
            sprite = new Sprite(SpritePath.enemies, "juggernaut-side-2");
            offset = offset.scl(-1f, 1);
            barrel = 120;
        }};
        if(thruster == null) thruster = new RammingThruster();

        pieces.addAll(side1, side2, thruster);

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
            for(EnemyPartEntity p : parts) if(p.keep()){
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
            side2().pos.set(Tmp.v1.set(side2().type().offset).rot(rotation + 90).add(pos));
            thruster().pos.set(Tmp.v1.set(thruster().type().offset).rot(rotation + 90).add(pos));
        }

        @Override
        public void init(){
            super.init();

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
        public Vec2 offset = new Vec2(-22, 42);

        public int charges = 5;
        public float barrel = 240;
        public float inacurracy = 30;

        public float velRand = 0f;

        public RammingSide(){
            super();

            reload = 1.5f;

            size = 15;
        }

        @Override
        public void init(){
            if(sprite == null) sprite = new Sprite(SpritePath.enemies, "juggernaut-side-1");

            super.init();
        }

        @Override
        public RammingSideEntity create(){
            return new RammingSideEntity(this);
        }

        public class RammingSideEntity extends EnemyPartEntity{
            public boolean shooting;

            public RammingSideEntity(RammingSide type){
                super(type);
            }

            @Override
            public void update(){
                super.update();

                rotation = parent.rotation;

                reloadt += reload();
                if(reloadt >= 60 * charges) shooting = true;

                if(shooting){
                    reloadt -= 60;
                    BulletEntity b = shoot(barrel + random(-inacurracy, inacurracy));
                    if(b != null) b.speed *= random(velRand, 1f);
                    if(reloadt < 60) shooting = false;
                }
            }

            @Override
            public void glow(){
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

        public float ramPower = 100;

        public RammingThruster(){
            super();

            reload = 0.1f;
            size = 17;
        }

        @Override
        public void init(){
            if(sprite == null) sprite = new Sprite(SpritePath.enemies, "juggernaut-thruster");

            super.init();
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

    public class RammingProtector extends Enemy{

    }
}