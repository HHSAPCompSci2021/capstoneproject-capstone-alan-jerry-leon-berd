package project.world.ship.weapons;

import project.*;
import project.graphics.*;
import project.world.bullets.Bullet.*;
import project.world.bullets.VolleyBullet.*;

import static project.Vars.*;

/** Stores stats for a volley weapon. */
public class VolleyWeapon extends Weapon{
    /**
     * Create a volley weapon with the specified name
     * @param name the name
     */
    public VolleyWeapon(String name){
        super(name);
        spread = 15;
    }

    @Override
    public VolleyWeaponInstance create(){
        return new VolleyWeaponInstance(this);
    }

    protected class VolleyWeaponInstance extends WeaponInstance{
        public VolleyWeaponInstance(VolleyWeapon type){
            super(type);
        }

        @Override
        public int projectiles(){
            return super.projectiles() * 2;
        }

        @Override
        public void shoot(){
            for(int i = 0;i < projectiles();i++){
                BulletEntity b = def(bullet.create());
                float x = (i - projectiles() / 2f + 0.5f) * spread();
                b.pos.set(player().hull.shootPos()).add(Tmp.v1.set(0, x).rot(player().rotation()));
                Tmp.v1.set(b.pos).sub(player().pos);
                Effects.gunfire.at(Tmp.v1.x, Tmp.v1.y, e -> e.color(0, player().color()).parent(player()));
                world.bullets.add(b);
                player().apply(Tmp.v1.set(-recoil(), 0).rot(player().rotation() + player().hull.shootRot()));
                player().hull.shot();

                if(b instanceof VolleyBulletEntity && i >= projectiles() / 2) ((VolleyBulletEntity)b).flip = true;
            }
        }
    }
}
