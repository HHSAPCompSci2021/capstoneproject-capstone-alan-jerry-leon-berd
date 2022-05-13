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
        public ChargeWeaponInstance(ChargeWeapon type){
            super(type);
        }

        @Override
        public float reload(){
            return super.reload() + reloadt * chargeBonus;
        }
    }
}
