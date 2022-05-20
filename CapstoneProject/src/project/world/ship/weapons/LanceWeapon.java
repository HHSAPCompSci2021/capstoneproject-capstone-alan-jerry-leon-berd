package project.world.ship.weapons;

import project.content.*;
import project.core.Input.*;
import project.world.bullets.LanceBullet.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class LanceWeapon extends Weapon{
    public LanceWeapon(String name){
        super(name);

        reload = 0.7f;
    }

    @Override
    public LanceWeaponInstance create(){
        return new LanceWeaponInstance(this);
    }

    /** Represents an instance of a weapon. */
    public class LanceWeaponInstance extends WeaponInstance{
        public LanceBulletEntity current;

        public LanceWeaponInstance(LanceWeapon type){
            super(type);
        }

        @Override
        public void update(){
            if(current == null){
                reloadt = min(reloadt + reload(), 60 * charges());
                if(input.pressed(KeyBind.shoot) && reloadt >= 60){
                    reloadt -= 60;
                    current = (LanceBulletEntity)def(bullet.create());
                    world.bullets.add(current);
                    world.player.entry(Statuses.slow, bullet.lifetime);
                }
            }else if(!current.keep()) current = null;
        }
    }
}
