package project.world.enemies;

import project.*;
import project.content.*;
import project.core.*;
import project.core.Content.*;
import project.core.Events.Event;
import project.game.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.*;
import project.world.bullets.*;
import project.world.bullets.Bullet.*;
import project.world.ship.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Stores stats for an enemy. */
public class Enemy extends Type implements ShipType{
    public EnemySprite glow;
    public EnemySprite sprite = new EnemySprite();
    public Color color = Color.white;

    public float health = 100;
    public float accel = 0.2f;
    public float rotate = 10;
    public float mass = 0;
    public float size = 10;
    public float ram = 1;
    public float drag = 1;

    public float reload = 1;

    public Bullet bullet = new Bullet();

    @Override
    public void init(){
        if(mass == 0) mass = size * size / 100f;
        if(glow != null) glow.set(SpritePath.none, sprite.path.substring(0, sprite.path.length() - 4) + "-glow");

        super.init();
    }

    @Override
    public ContentType type(){
        return ContentType.enemy;
    }

    @Override
    public EnemyEntity create(){
        return new EnemyEntity(this);
    }

    @Override
    public float accel(){
        return accel;
    }

    @Override
    public float rotate(){
        return rotate;
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
    public float health(){
        return health;
    }

    @Override
    public float ram(){
        return ram;
    }

    @Override
    public float drag(){
        return drag;
    }

    /** Represents and simulates an enemy. */
    public class EnemyEntity extends Ship{
        /** Stores the reloadTimer, which is incremented every frame and stores when the enemy should shoot. */
        public float reloadt;

        public EnemyEntity(Enemy type){
            super(type);
            entry(Statuses.spawned);
        }

        /** Returns the real reload speed of this enemy. */
        public float reload(){
            return (reload + rules.add(weaponReload, team)) * rules.mult(weaponReload, team) * delta;
        }

        @Override
        public Color color(){
            return color;
        }

        @Override
        public Sprite sprite(){
            return sprite;
        }

        public BulletEntity shoot(float offset){
            if(!world.bounds.contains(pos)) return null;

            BulletEntity b = bullet.create();
            b.pos.set(pos);
            b.team = team;
            b.rotation = rotation + offset;
            b.origin = this;
            world.bullets.add(b);
            return b;
        }

        @Override
        public void wrap(){
            if(!spawned()) super.wrap();
        }

        @Override
        public void update(){
            super.update();

            apply(Tmp.v1.set(vel).scl(-0.02f));

            if(spawned()) apply(Tmp.v1.set(world.bounds.center()).sub(pos).nor().scl(accel()));
        }

        @Override
        public void glow(){
            super.glow();

            if(glow != null) glow.drawc(pos.x, pos.y, size() * 5, size() * 5, rotation, Color.white);
        }

        @Override
        public void draw(){
            super.draw();

            sprite.drawc(pos.x + 5, pos.y + 5, size() * 5, size() * 5, rotation, Color.black, 50);
            sprite.drawc(pos.x, pos.y, size() * 5, size() * 5, rotation, Color.white);
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

            Sounds.playSound("fuel_explosion.mp3");
        }

        @Override
        public Enemy type(){
            return (Enemy)type;
        }

        @Override
        public boolean keep(){
            return !(life <= 0) && (spawned() || world.bounds.contains(pos));
        }
    }

    public static class EnemySprite extends Sprite{
        public EnemySprite set(String name){
            super.set(SpritePath.enemies, name);
            return this;
        }
    }
}
