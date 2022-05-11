package project.world.ship.weapons;

import project.content.*;
import project.core.Content.*;
import project.core.Input.*;
import project.graphics.*;
import project.world.*;
import project.world.bullets.*;
import project.world.bullets.Bullet.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores stats for a weapon. */
public class Weapon extends Type{
    public int shots = 1;
    public float spread = 10;
    public float reload = 10;
    public float velRand = 1;
    public float inaccuracy = 5;
    public float recoil = 0.1f;

    public Bullet bullet = Bullets.normal;

    @Override
    public ContentType type(){
        return ContentType.weapon;
    }

    @Override
    public WeaponInstance create(){
        return new WeaponInstance(this);
    }

    /** Represents an instance of a weapon. */
    public class WeaponInstance extends Instance{
        public float reloadt;

        public WeaponInstance(Weapon type){
            super(type);
        }

        public void update(){
            reloadt = min(reloadt + reload * rules.weaponReload(world.player.team), 60 * rules.weaponCharges(world.player.team));

            if(input.pressed(KeyBind.shoot) && reloadt >= 60){
                reloadt -= 60;
                shoot();
            }
        }

        public BulletEntity def(BulletEntity b){
            b.pos.set(world.player.hull.shootPos());
            b.team = world.player.team;
            b.rotation = world.player.rotation + random(-inaccuracy, inaccuracy);
            b.speed *= random(velRand, 1f);
            b.origin = world.player;
            return b;
        }

        public void shoot(){
            for(int i = 0;i < shots + rules.shotProjectiles(world.player.team) - 1;i++){
                BulletEntity b = def(bullet.create());
                b.rotation += spread * (i - (shots - 1) / 2f);
                world.bullets.add(b);
                Effects.gunfire.at(world.player.hull.shootPos().x, world.player.hull.shootPos().y, e -> e.color(0, world.player.color()).scale(1.2f).parent(world.player));
            }

            world.player.thrust(-recoil * rules.weaponRecoil(world.player.team));
        }

        @Override
        public Weapon type(){
            return (Weapon)type;
        }
    }
}