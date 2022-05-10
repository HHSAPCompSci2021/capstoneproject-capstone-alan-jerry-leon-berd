package project.world.ship.weapons;

import project.content.*;
import project.world.bullets.Bullet.*;

import static project.Vars.*;

/** Stores stats for a weapon which propels the player forward and drops mines behind it. */
public class ThrusterWeapon extends Weapon{
    public ThrusterWeapon(){
        super();

        shots = 10;
        spread = 0;
        reload = 1;
        velRand = 0.3f;
        inaccuracy = 30;
        recoil = 10;
        bullet = Bullets.mine;
    }

    @Override
    public ThrusterWeaponInstance create(){
        return new ThrusterWeaponInstance(this);
    }

    /** Represents an instance of a weapon which propels the player forward and drops mines behind it. */
    public class ThrusterWeaponInstance extends WeaponInstance{
        public ThrusterWeaponInstance(ThrusterWeapon type){
            super(type);
        }

        @Override
        public void shoot(){
            for(int i = 0;i < shots + rules.shotProjectiles(world.player.team) - 1;i++){
                BulletEntity b = def(bullet.create());
                b.pos.set(world.player.pos);
                b.rotation += spread * (i - (shots - 1) / 2f) + 180;
                world.bullets.add(b);
            }

            world.player.thrust(recoil * rules.weaponRecoil(world.player.team));
        }
    }
}
