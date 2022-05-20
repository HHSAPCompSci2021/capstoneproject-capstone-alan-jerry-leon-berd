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
            addPro("A rapid barrage of bullets");
            addPro("Projectiles are doubled");
            bullet = new VolleyBullet();
        }};
        salvo = new SalvoWeapon("salvo"){{
            addPro("Seeking missiles that home in on enemies");
            addPro("Missiles stockpile at a rate scaling with shots and reload when not firing");
            addPro("Stockpile rate also scales with amount of missiles in stockpile");
            addPro("Firing releases the entire stockpile");
            bullet = new MissileBullet();
        }};
        flak = new ShotgunWeapon("flak"){{
            addPro("Deadly shrapnel that shred close-range targets");
            addPro("Shrapnel damage falls off over it's lifetime");
            addPro("Projectiles are doubled");
            bullet = new FlakBullet();
        }};
        burst = new ShotgunWeapon("burst"){{
            addPro("An array of outwards-fanning bullets");
            addPro("Projectiles explode on impact");
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
            addPro("A high velocity, high damage projectile");
            addPro("Damage scales with projectile speed");
            addPro("Bullet penetrates through destroyed targets");
            bullet = new RailgunBullet();
        }};
        grenade = new Weapon("grenade"){{
            addPro("An explosive, damaging grenade");
            addPro("Explodes on impact or after a delay");
            addPro("Charge up to 3 grenades when not firing");
            manual = true;
            reload = 1.7f;
            charges = 3;
            bullet = new GrenadeBullet();
        }};
        lance = new LanceWeapon("lance"){{
            addPro("A focused lance of energy which penetrates enemies");
            addPro("Damage scales with how amount of time it has been hitting the target");
            bullet = new LanceBullet();
        }};
    }
}
