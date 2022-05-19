package project.world.ship.weapons;

import project.core.Input.*;
import project.game.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

public class SalvoWeapon extends Weapon{
    public float chargeBonus = 0.001f;

    public SalvoWeapon(String name){
        super(name);
        shots = 4;
        reload = 1.7f;
        inaccuracy = 20;
        charges = 25;
    }

    @Override
    public SalvoWeaponInstance create(){
        return new SalvoWeaponInstance(this);
    }

    /** Represents an instance of a weapon which propels the player forward and drops mines behind it. */
    public class SalvoWeaponInstance extends WeaponInstance{
        public boolean shooting;

        public SalvoWeaponInstance(SalvoWeapon type){
            super(type);
        }

        @Override
        public int projectiles(){
            return 1;
        }

        @Override
        public float reload(){
            return (reload + rules.add(weaponReload, Team.player) + reloadt * chargeBonus) * rules.mult(weaponReload, Team.player) * delta * super.projectiles();
        }

        public void update(){
            reloadt = min(reloadt + reload(), 60 * charges());

            if(input.pressed(KeyBind.shoot) || shooting) {
                if(reloadt >= 60){
                    shooting = true;
                    reloadt -= 60;
                    shoot();
                }else shooting = false;
            }
        }
    }
}
