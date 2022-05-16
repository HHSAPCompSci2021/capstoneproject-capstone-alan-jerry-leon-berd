package project.world.ship.weapons;

import project.*;
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
    public float inaccuracy = 0;
    public float recoil = 0.1f;

    public Bullet bullet = new MissileBullet();;

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
        /** Stores the reloadTimer, which is incremented every frame and stores when the enemy should shoot. */
        public float reloadt;

        public WeaponInstance(Weapon type){
            super(type);
        }

        /** Returns the charges this weapon can store. */
        public int charges(){
            return rules.weaponCharges(world.player.team);
        }

        /** Returns the amount of projectiles this weapon shoots. */
        public int projectiles(){
            return shots + rules.shotProjectiles(world.player.team) - 1;
        }

        /** Returns the recoil this weapon has. */
        public float recoil(){
            return recoil * rules.weaponRecoil(world.player.team);
        }

        /** Updates this weapon. */
        public void update(){
            reloadt = min(reloadt + reload * rules.weaponReload(world.player.team), 60 * charges());

            if(input.pressed(KeyBind.shoot) && reloadt >= 60){
                reloadt -= 60;
                shoot();
            }
        }

        /** Sets the defaults of the specified bullet shot by this weapon. */
        public BulletEntity def(BulletEntity b){
            b.pos.set(world.player.hull.shootPos());
            b.team = world.player.team;
            b.rotation = world.player.rotation + random(-inaccuracy, inaccuracy);
            b.speed *= random(velRand, 1f);
            b.origin = world.player;
            return b;
        }

        /** Creates bullets based on the weapon type. */
        public void shoot(){
            for(int i = 0;i < projectiles();i++){
                BulletEntity b = def(bullet.create());
                b.rotation += spread * (i - (shots - 1) / 2f);
                world.bullets.add(b);
                Tmp.v1.set(world.player.hull.shootPos()).sub(world.player.pos);
                Effects.gunfire.at(Tmp.v1.x, Tmp.v1.y, e -> e.color(0, world.player.color()).scale(1.2f).parent(world.player));
            }

            world.player.apply(Tmp.v1.set(-recoil(), 0).rot(world.player.rotation));
        }

        @Override
        public Weapon type(){
            return (Weapon)type;
        }
    }
}