package project.content;

import project.core.Content.*;
import project.world.modifiers.*;

import static project.core.Rules.Rule.*;

/** Contains and loads all modifiers in the game. */
public class Modifiers implements ContentList{
    public static Modifier overdrive, bluntedBullets, piercingHull, shotgunShells, doubleShot, deadlyCartridges, largerExplosives;

    @Override
    public void load(){
        overdrive = new Modifier(){{
            mult(weaponReload, 1f);
            mult(bulletDamage, -0.5f);
            mult(bulletSpeed, -0.1f);
        }};
        bluntedBullets = new Modifier(){{
            mult(bulletKnockback, 10f);
            mult(weaponReload, -0.5f);
        }};
        piercingHull = new Modifier(){{
            mult(ramDamage, 10f);
        }};
        shotgunShells = new Modifier(){{
            mult(bulletDamage, -0.2f);
            mult(weaponReload, -0.5f);
            add(shotProjectiles, 2);
        }};
        doubleShot = new Modifier(){{
            add(weaponCharges, 1);
        }};
        deadlyCartridges = new Modifier(){{
            add(splashRadius, 100);
            add(splashDamage, 100);
        }};
        largerExplosives = new Modifier(){{
            mult(splashRadius, 2f);
            mult(bulletDamage, -0.8f);
            mult(splashDamage, 3f);
        }};
    }
}
