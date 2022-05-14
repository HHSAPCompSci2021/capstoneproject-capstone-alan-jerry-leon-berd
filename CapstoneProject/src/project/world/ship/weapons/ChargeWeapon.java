package project.world.ship.weapons;

import project.core.Input.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class ChargeWeapon extends Weapon{
    public float chargeBonus = 0.01f;

    @Override
    public ChargeWeaponInstance create(){
        return new ChargeWeaponInstance(this);
    }

    /** Represents an instance of a weapon which propels the player forward and drops mines behind it. */
    public class ChargeWeaponInstance extends WeaponInstance{
        public boolean shooting;

        public ChargeWeaponInstance(ChargeWeapon type){
            super(type);
        }

        @Override
        public int charges(){
            return charges + rules.weaponChargesAdd(world.player.team) + super.projectiles() - 1;
        }

        @Override
        public int projectiles(){
            return 1;
        }

        @Override
        public float reload(){
            return (reload + reloadt * chargeBonus) * rules.weaponReloadMult(world.player.team);
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
