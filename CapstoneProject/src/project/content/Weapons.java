package project.content;

import project.core.Content.*;
import project.world.bullets.*;
import project.world.ship.weapons.*;

/** Contains anf laods all gears in the game. Might be better if this was split into 3 classes. */
public class Weapons implements ContentList{
    public static Weapon blaster, salvo, flak, burst, railgun, grenade, lance;

    @Override
    public void load(){
        blaster = new VolleyWeapon("blaster"){{
            bullet = new VolleyBullet();
        }};
        salvo = new SalvoWeapon("salvo"){{
            bullet = new MissileBullet();
        }};
        flak = new ShotgunWeapon("flak"){{
            bullet = new FlakBullet();
        }};
        burst = new ShotgunWeapon("burst"){{
            manual = false;
            shots = 3;
            shotMult = 1;
            spread = 15;
            velRand = 0f;
            reload = 4;
            bullet = new Bullet(){{
                sprite.set("burst");
                speed = 9;
                damage = 20;
                splashDamage = 0.4f;
                splashRadius = 50;
                inaccuracy = 1;
            }};
        }};
        railgun = new RailgunWeapon("railgun"){{
            bullet = new RailgunBullet();
        }};
        grenade = new Weapon("grenade"){{
            manual = true;
            reload = 1.5f;
            charges = 3;
            bullet = new GrenadeBullet();
        }};
        lance = new LanceWeapon("lance"){{
            bullet = new LanceBullet();
        }};
    }
}
