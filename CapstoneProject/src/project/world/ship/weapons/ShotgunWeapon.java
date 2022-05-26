package project.world.ship.weapons;

/** Stores stats for a shotgun weapon. */
public class ShotgunWeapon extends Weapon{
    protected int shotMult = 2;

    /**
     * Create a shotgun weapon with the specified name
     * @param name the name
     */
    public ShotgunWeapon(String name){
        super(name);

        reload = 1.5f;
        shots = 8;
        spread = 4;
        inaccuracy = 5;
        velRand = 0.7f;
        lifeRand = 0.3f;
        manual = true;
    }

    @Override
    public ShotgunWeaponInstance create(){
        return new ShotgunWeaponInstance(this);
    }

    protected class ShotgunWeaponInstance extends WeaponInstance{
        public ShotgunWeaponInstance(ShotgunWeapon type){
            super(type);
        }

        @Override
        public int projectiles(){
            return super.projectiles() * shotMult;
        }
    }
}
