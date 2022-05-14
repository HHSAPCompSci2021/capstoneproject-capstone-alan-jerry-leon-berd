package project.content;

import project.core.Content.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.bullets.*;
import project.world.ship.*;
import project.world.ship.weapons.*;

/** Contains anf laods all gears in the game. Might be better if this was split into 3 classes. */
public class Gear implements ContentList{
    public static Hull normal;

    public static Shield shield;

    public static Weapon blaster, salvo, flak;

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
        flak = new Weapon(){{
            reload = 1.5f;
            shots = 15;
            spread = 4;
            inaccuracy = 5;
            velRand = 0.7f;
            lifeRand = 0.3f;
            manual = true;

            bullet = new Bullet(){{
                sprite = new Sprite(SpritePath.bullets, "flak");
                trailDuration = 10;
                trailSize = 2;
                speed = 30;
                lifetime = 7;
            }};
        }};
    }
}
