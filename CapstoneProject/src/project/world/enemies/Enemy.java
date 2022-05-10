package project.world.enemies;

import project.content.*;
import project.core.Content.*;
import project.core.*;
import project.graphics.*;
import project.world.*;
import project.world.bullets.*;
import project.world.bullets.Bullet.*;
import project.world.enemies.ai.*;
import project.world.ship.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores stats for an enemy. */
public class Enemy extends Type{
    public Sprite sprite;
    public Color color = Color.white;

    public float accel = 0.1f;
    public float rotate = 5;

    public float mass = 1;
    public float size = 10;
    public float health = 100;
    public float reload = 1;

    public Bullet bullet = Bullets.normal;

    public EnemyAI ai = new KiteAI();

    @Override
    public ContentType type(){
        return ContentType.enemy;
    }

    @Override
    public EnemyEntity create(){
        return new EnemyEntity(this);
    }

    /** Represents and simulates an enemy. */
    public class EnemyEntity extends Ship{
        public float reloadt;

        public EnemyEntity(Enemy type){
            super(type);
        }

        @Override
        public void init(){
            life = health;
        }

        @Override
        public float mass(){
            return mass;
        }

        @Override
        public float size(){
            return size;
        }

        @Override
        public Color color(){
            return color;
        }

        public void shoot(){
            reloadt += reload * rules.weaponReload(team);

            if(reloadt >= 60){
                reloadt %= 60;

                BulletEntity b = bullet.create();
                b.pos.set(pos);
                b.team = team;
                b.rotation = rotation;
                b.origin = this;
                world.bullets.add(b);
            }
        }

        @Override
        public void update(){
            super.update();

            if(ai != null) ai.update(this);

            apply(tmp.set(vel).scl(-0.02f));
        }

        @Override
        public void remove(){
            super.remove();

            for(int i = 0;i < size / 10f;i++){
                Experience exp = new Experience().amount(size);
                exp.pos.set(pos);
                exp.vel.setr(random(0, 360), random(0, rt2(size)));
                world.experience.add(exp);
            }
        }

        @Override
        public Enemy type(){
            return (Enemy)type;
        }

        @Override
        public boolean keep(){
            return !(life <= 0) && world.bounds.contains(pos);
        }
    }
}
