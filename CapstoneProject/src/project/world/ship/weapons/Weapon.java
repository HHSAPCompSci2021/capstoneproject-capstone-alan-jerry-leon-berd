package project.world.ship.weapons;

import project.*;
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
    public int charges = 1;
    public int shots = 1;
    public float spread = 10;
    public float reload = 10;
    public float velRand = 0;
    public float inaccuracy = 0;
    public float recoil = 0.1f;
    public float lifeRand = 0;

    public boolean manual;

    public Bullet bullet;

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

        /** Returns the ratio of current reload to max reload. */
        public float fin(){
            return reloadt / 60 / charges();
        }

        /** Returns the charges this weapon can store. */
        public int charges(){
            return charges + rules.weaponChargesAdd(world.player.team);
        }

        /** Returns the amount of projectiles this weapon shoots. */
        public int projectiles(){
            return shots + rules.shotProjectilesAdd(world.player.team);
        }

        /** Returns the recoil this weapon has. */
        public float recoil(){
            return recoil * rules.weaponRecoilMult(world.player.team);
        }

        public float reload(){
            return reload * rules.weaponReloadMult(world.player.team);
        }

        /** Updates this weapon. */
        public void update(){
            reloadt = min(reloadt + reload(), 60 * charges());

            if(input.pressed(KeyBind.shoot) && reloadt >= 60){
                if(manual) input.consume(KeyBind.shoot);
                reloadt -= 60;
                shoot();
            }
        }

        /** Sets the defaults of the specified bullet shot by this weapon. */
        public BulletEntity def(BulletEntity b){
            b.pos.set(world.player.hull.shootPos());
            b.team = world.player.team;
            b.rotation = world.player.rotation + random(-inaccuracy, inaccuracy);
            b.speed *= random(1f - velRand, 1f);
            b.life = b.type().lifetime * random(0, lifeRand);
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
                Effects.gunfire.at(Tmp.v1.x, Tmp.v1.y, e -> e.color(0, world.player.color()).parent(world.player));
                world.player.apply(Tmp.v1.set(-recoil(), 0).rot(world.player.rotation));
            }
        }

        @Override
        public Weapon type(){
            return (Weapon)type;
        }
    }
}