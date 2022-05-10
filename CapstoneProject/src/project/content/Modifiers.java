package project.content;

import project.core.Content.*;
import project.world.modifiers.*;

import static project.core.Rules.Rule.*;

/** Contains a list of all modifiers in the game. */
public class Modifiers implements ContentList{
    public static Modifier overdrive, bluntedBullets, piercingHull, shotgunShells, doubleShot;

    @Override
    public void load(){
        overdrive = new Modifier(){{
            set(weaponReload, 1f);
            set(bulletDamage, -0.5f);
            set(bulletSpeed, -0.1f);
        }};
        bluntedBullets = new Modifier(){{
            set(bulletKnockback, 10f);
            set(weaponReload, -0.9f);
        }};
        piercingHull = new Modifier(){{
            set(ramDamage, 10f);
        }};
        shotgunShells = new Modifier(){{
            set(bulletDamage, -0.2f);
            set(weaponReload, -0.5f);
            set(shotProjectiles, 1);
        }};
        doubleShot = new Modifier(){{
            set(weaponCharges, 1);
        }};
    }
}
