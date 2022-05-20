package project.world.ship.weapons;

import project.*;
import project.core.Content.*;
import project.core.Input.*;
import project.game.*;
import project.graphics.*;
import project.world.bullets.*;
import project.world.bullets.Bullet.*;
import project.world.modifiers.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Stores stats for a weapon. */
public class Weapon extends Modifier{
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

    public Weapon(String name){
        super(name);

        tag = "WEAPON";

        sprite.set("weapon-" + name);
    }

    @Override
    public void init(){
        super.init();

        if(charges != 1) addPro("Base charges: " + charges);
        if(shots != 1) addPro("Base projectiles: " + shots);
        addPro("Rate of fire: " + reload + "/sec");
        addPro("Damage: " + bullet.damage);
    }

    @Override
    public ContentType type(){
        return ContentType.weapon;
    }

    @Override
    public WeaponInstance create(){
        return new WeaponInstance(this);
    }

    /** Represents an instance of a weapon. */
    public class WeaponInstance extends ModInstance{
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
            return (int)((charges + rules.add(weaponCharges, Team.player)) * rules.mult(weaponCharges, Team.player));
        }

        /** Returns the amount of projectiles this weapon shoots. */
        public int projectiles(){
            return (int)((shots + rules.add(shotProjectiles, Team.player)) * rules.mult(shotProjectiles, Team.player));
        }

        /** Returns the recoil this weapon has. */
        public float recoil(){
            return (recoil + rules.add(weaponRecoil, Team.player)) * rules.mult(weaponRecoil, Team.player);
        }

        public float reload(){
            return (reload + rules.add(weaponReload, Team.player)) * rules.mult(weaponReload, Team.player);
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
            b.pos.set(player().hull.shootPos());
            b.team = Team.player;
            b.rotation = player().rotation + random(-inaccuracy, inaccuracy);
            b.speed *= random(1f - velRand, 1f);
            b.life = b.bullet.lifetime * random(0, lifeRand);
            b.origin = player();
            return b;
        }

        /** Creates bullets based on the weapon type. */
        public void shoot(){
            for(int i = 0;i < projectiles();i++){
                BulletEntity b = def(bullet.create());
                b.rotation += spread * (i - (shots - 1) / 2f);
                world.bullets.add(b);
                Tmp.v1.set(player().hull.shootPos()).sub(player().pos);
                Effects.gunfire.at(Tmp.v1.x, Tmp.v1.y, e -> e.color(0, player().color()).parent(player()));
            }

            player().apply(Tmp.v1.set(-recoil(), 0).rot(player().rotation));
        }

        @Override
        public Weapon type(){
            return (Weapon)type;
        }
    }
}