package project.world.ship.weapons;

import project.*;
import project.graphics.*;
import project.world.bullets.Bullet.*;
import project.world.bullets.VolleyBullet.*;

import static project.Vars.*;

public class VolleyWeapon extends Weapon{
    public VolleyWeapon(String name){
        super(name);
        spread = 15;
        inaccuracy = 2;
    }

    @Override
    public VolleyWeaponInstance create(){
        return new VolleyWeaponInstance(this);
    }

    public class VolleyWeaponInstance extends WeaponInstance{
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
//                float x = ((float)i / (actual - 1) - 0.5f) * spread;
                float x = (i - projectiles() / 2f + 0.5f) * spread;
                b.pos.set(player().hull.shootPos()).add(Tmp.v1.set(0, x).rot(player().rotation));
                Effects.gunfire.at(Tmp.v1.x, Tmp.v1.y, e -> e.color(0, player().color()).parent(player()));
                world.bullets.add(b);
                if(b instanceof VolleyBulletEntity && i >= projectiles() / 2) ((VolleyBulletEntity)b).flip = true;
            }

            player().apply(Tmp.v1.set(-recoil(), 0).rot(player().rotation));
        }
    }
}
