package project.content;

import project.core.Content.*;
import project.world.bullets.*;
import project.world.ship.weapons.*;

/** Contains anf laods all gears in the game. Might be better if this was split into 3 classes. */
public class Weapons implements ContentList{
    public static Weapon blaster, salvo, flak;

    @Override
    public void load(){
        blaster = new VolleyWeapon("blaster"){{
            bullet = new Bullet();
        }};
        salvo = new SalvoWeapon("salvo"){{
            bullet = new MissileBullet();
        }};
        flak = new ShotgunWeapon("flak"){{
            bullet = new Bullet(){{
                sprite = new BulletSprite().set("flak");
                trailDuration = 10;
                trailSize = 2;
                speed = 30;
                lifetime = 7;
            }};
        }};
    }
}
