package project.world.bullets;

import project.game.*;
import project.graphics.*;
import project.world.ship.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class GrenadeBullet extends Bullet{
    public float drag = 0.025f;

    public GrenadeBullet(){
        super();

        sprite.set("grenade");

        size = 4;
        trailDuration = 5;
        trailSize = 8;
        speed = 10;
        lifetime = 120;
        damage = 100;
        splashDamage = 10f;
        splashRadius = 100;
    }

    public GrenadeBulletEntity create(){
        return new GrenadeBulletEntity(this);
    }

    /** Represents and simulates a laser bullet. */
    public class GrenadeBulletEntity extends BulletEntity{
        public GrenadeBulletEntity(GrenadeBullet type){
            super(type);
        }

        @Override
        public void hit(Ship s){
            collided.add(origin);

            canvas.shake(rt2(blastRadius()));

            world.ships.query(pos.x, pos.y, blastRadius() + maxEntitySize, e -> {
                if(e.team != team && dst(e, pos) < e.size() + blastRadius()){
                    if(e.team != Team.player) world.player.lastHit = e;
                    e.damage(blastDamage() * (1f - dst(e, pos) / (e.size() + blastRadius())));
                }
            });

            Effects.shockwave.at(pos.x, pos.y, e -> e.color(0, color()).set(3, blastRadius() / 2).set(4, 3).lifetime(rt2(blastRadius()) * 2.5f));
            Effects.explosion.at(pos.x, pos.y, e -> e.color(0, color()).set(3, blastRadius() / 2));
        }

        @Override
        public void update(){
            super.update();

            speed *= 1f - drag;
            if(life > lifetime) hit(null);
        }

        @Override
        public boolean keep(){
            return world.bounds.contains(pos) && collided.size <= 0;
        }
    }
}
