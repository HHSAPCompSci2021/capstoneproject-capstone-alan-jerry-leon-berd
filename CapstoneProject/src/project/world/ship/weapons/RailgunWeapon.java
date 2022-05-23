package project.world.ship.weapons;

import project.*;
import project.graphics.*;
import project.world.bullets.Bullet.*;

import java.awt.*;

import static project.Vars.*;

/** Stores stats for a railgun weapon. */
public class RailgunWeapon extends Weapon{
    /**
     * Create a railgun weapon with the specified name
     * @param name the name
     */
    public RailgunWeapon(String name){
        super(name);

        manual = true;
        reload = 1;
        recoil = 2;
    }

    @Override
    public RailgunWeaponInstance create(){
        return new RailgunWeaponInstance(this);
    }

    protected class RailgunWeaponInstance extends WeaponInstance{
        public RailgunWeaponInstance(Weapon type){
            super(type);
        }

        @Override
        public void shoot(){
            super.shoot();

            Effects.shockwave.at(player().hull.shootPos().x, player().hull.shootPos().y, e -> e.color(0, player().color()).set(3, 40).lifetime(20));
        }
    }
}
