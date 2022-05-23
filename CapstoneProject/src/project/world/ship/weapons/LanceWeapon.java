package project.world.ship.weapons;

import project.content.*;
import project.core.Input.*;
import project.world.bullets.Bullet.*;
import project.world.bullets.LanceBullet.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores stats for a lance weapon. */
public class LanceWeapon extends Weapon{
    /**
     * Create a lance weapon with the specified name
     * @param name the name
     */
    public LanceWeapon(String name){
        super(name);

        reload = 0.7f;
        spread = 40;
    }

    @Override
    public LanceWeaponInstance create(){
        return new LanceWeaponInstance(this);
    }

    /** Represents an instance of a weapon. */
    protected class LanceWeaponInstance extends WeaponInstance{
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
                    for(int i = 0;i < projectiles();i++){
                        current = (LanceBulletEntity)def(bullet.create());
                        current.rotation += spread() * (i - (shots - 1) / 2f);
                        world.bullets.add(current);

                        player().hull.shot();
                    }
                }
            }else if(!current.keep()) current = null;
        }
    }
}
