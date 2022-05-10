package project.content;

import project.core.Content.*;
import project.world.ship.*;
import project.world.ship.weapons.*;

/** Contains a list of all gears in the game. */
public class Gear implements ContentList{
    public static Hull normal;

    public static Shield shield;

    public static Weapon bullet, thruster, laser, railgun;

    @Override
    public void load(){
        normal = new Hull();
        shield = new Shield();
        bullet = new VolleyWeapon();
        thruster = new ThrusterWeapon();
        laser = new Weapon(){{
            reload = 2;
            recoil = 0;
            inaccuracy = 1;
            bullet = Bullets.laser;
        }};
        railgun = new Weapon(){{
            reload = 1.3f;
            recoil = 2;
            inaccuracy = 0.5f;
            bullet = Bullets.railgun;
        }};
    }
}
