package project.world.ship.weapons;

public class ShotgunWeapon extends Weapon{
    public int shotMult = 2;

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

    public class ShotgunWeaponInstance extends WeaponInstance{
        public ShotgunWeaponInstance(ShotgunWeapon type){
            super(type);
        }

        @Override
        public int projectiles(){
            return super.projectiles() * shotMult;
        }
    }
}
