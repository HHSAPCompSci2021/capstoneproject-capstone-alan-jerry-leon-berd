package project.world.enemies;

import project.*;
import project.content.*;
import project.core.Content.*;
import project.core.Events.*;
import project.core.Events.*;
import project.game.*;
import project.graphics.*;
import project.world.*;
import project.world.bullets.*;
import project.world.bullets.Bullet.*;
import project.world.ship.*;

import java.awt.Color;

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
        /** Stores the reloadTimer, which is incremented every frame and stores when the enemy should shoot. */
        public float reloadt;

        public boolean justSpawned; //TODO: Turn this into a status effect

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
        public float accel(){
            return accel * rules.engineAcceleration(team);
        }

        @Override
        public float rotate(){
            return rotate * rules.rotateSpeed(team);
        }

        /** Returns the real reload speed of this enemy. */
        public float reload(){
            return reload * rules.weaponReload(team);
        }

        @Override
        public Color color(){
            return color;
        }

        @Override
        public Sprite sprite(){
            return sprite;
        }

        public void shoot(float offset){
            BulletEntity b = bullet.create();
            b.pos.set(pos);
            b.team = team;
            b.rotation = rotation + offset;
            b.origin = this;
            world.bullets.add(b);
        }

        @Override
        public void update(){
            super.update();

            apply(Tmp.v1.set(vel).scl(-0.02f));

            if(justSpawned){
                if(!world.bounds.contains(pos)) apply(Tmp.v1.set(world.bounds.center()).sub(pos).nor().scl(accel()));
                else justSpawned = false;
            }
        }

        @Override
        public void draw(){
            super.draw();

            canvas.tint(255, 255, 255);
            sprite.drawc(pos.x, pos.y, size() * 5, size() * 5, rotation);
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

            events.call(Event.enemyDestroyed);
        }

        @Override
        public Enemy type(){
            return (Enemy)type;
        }

        @Override
        public boolean keep(){
            return !(life <= 0) && (justSpawned || world.bounds.contains(pos));
        }
    }

}
