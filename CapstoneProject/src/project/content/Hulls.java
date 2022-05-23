package project.content;

import project.core.Content.*;
import project.world.ship.*;
import project.world.ship.hulls.*;

import static project.core.Rules.Rule.*;

/** Contains and loads all the hull types in the game. */
public class Hulls implements ContentList{
    public static Hull standard, hullbreaker, courser, battery, firefly, research, assault;

    @Override
    public void load(){
        standard = new Hull("standard");
        hullbreaker = new Hull("hullbreaker"){{
            health = 175;
            size = 12;
            accel = 0.35f;
            mass = 1.7f;
            ram = 20;
            drag = 0.5f;
            regen = 10;
            rotate = 15;
            mult(ramResistance, 2.5f);
            mult(maxShields, -0.3f);
            mult(weaponReload, -0.3f);
        }};
        courser = new Hull("courser"){{
            health = 75;
            size = 15;
            mass = 1.2f;
            drag = 0.7f;
            rotate = 5;
            add(shotProjectiles, 3);
            mult(maxShields, -0.3f);
            mult(weaponReload, -0.5f);
            mult(shotSpread, -0.5f);
            mult(globalDamage, -0.25f);
            shootPos.set(30, 0);
            thrusters.clear();
            thrusters.add(new Thruster(0, 2, 6f, 25f, 0));
        }};
        battery = new MultiBarrelHull("battery"){{
            addCon("Projectiles are divided between side cannons");
            health = 225;
            size = 17;
            accel = 0.3f;
            mass = 2.2f;
            drag = 1.25f;
            rotate = 4;
            barrels.addAll(new Barrel(10, 20, 90), new Barrel(10, -20, 270), new Barrel(-10, 20, 90), new Barrel(-10, -20, 270));
            add(shotProjectiles, 2);
            mult(maxShields, 0.5f);
            mult(globalDamage, 0.25f);
            mult(shotSpread, -1f);
            thrusters.clear();
            thrusters.add(new Thruster(0, 10, 4f, 25f, 0));
        }};
        firefly = new Hull("firefly"){{
            addCon("Bullets are shot backwards");
            health = 125;
            accel = 0.3f;
            size = 14;
            mass = 1.4f;
            shootRot = 180;
            rotate = 12;
            mult(weaponRecoil, 3.5f);
            mult(weaponReload, -0.2f);
            mult(globalDamage, 1.2f);
            mult(bulletSpeed, -0.5f);
            thrusters.clear();
            thrusters.add(new Thruster(0, 0, 6f, 25f, 0));
        }};
        research = new Hull("research"){{
            health = 80;
            size = 13;
            mult(maxShields, -0.2f);
            mult(expGain, 0.25f);
            thrusters.clear();
            thrusters.add(new Thruster(0, 0, 6f, 25f, 0));
        }};
        assault = new Hull("assault"){{
            health = 35;
            accel = 0.35f;
            rotate = 7;
            size = 12;
            mult(maxShields, 0.55f);
            mult(weaponReload, -0.4f);
            mult(globalDamage, 0.8f);
            thrusters.clear();
            thrusters.add(new Thruster(0, 8, 6f, 25f, 0));
        }};
    }
}
