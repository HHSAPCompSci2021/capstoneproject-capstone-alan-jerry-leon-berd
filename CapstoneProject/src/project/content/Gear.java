package project.content;

import project.core.Content.*;
import project.world.bullets.*;
import project.world.ship.*;
import project.world.ship.weapons.*;

/** Contains anf laods all gears in the game. Might be better if this was split into 3 classes. */
public class Gear implements ContentList{
    public static Hull normal;

    public static Shield shield;

    public static Weapon blaster, salvo;

    @Override
    public void load(){
        normal = new Hull();
        shield = new Shield();
        blaster = new VolleyWeapon(){{
            bullet = new VolleyBullet();
        }};
        salvo = new ChargeWeapon(){{
            reload = 2;
            inaccuracy = 20;
            charges = 25;
            bullet = new MissileBullet();
        }};
    }
}
