package project.world.ship.weapons;

import project.*;
import project.graphics.*;
import project.world.bullets.*;
import project.world.bullets.Bullet.*;
import project.world.bullets.VolleyBullet.*;

import static project.Vars.*;

public class VolleyWeapon extends Weapon{
    public VolleyWeapon(){
        super();
        bullet = new VolleyBullet();
        spread = 10;
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
        public void shoot(){
            int actual = (shots + rules.shotProjectiles(world.player.team) - 1) * 2;
            for(int i = 0;i < actual;i++){
                BulletEntity b = def(bullet.create());
//                float x = ((float)i / (actual - 1) - 0.5f) * spread;
                float x = (i - actual / 2f + 0.5f) * spread;
                b.pos.set(world.player.hull.shootPos()).add(Tmp.v1.set(0, x).rot(world.player.rotation));
                Effects.gunfire.at(Tmp.v1.x, Tmp.v1.y, e -> e.color(0, world.player.color()).scale(1.3f).parent(world.player));
                world.bullets.add(b);
                if(b instanceof VolleyBulletEntity && i >= actual / 2) ((VolleyBulletEntity)b).flip = true;
            }

            world.player.thrust(-recoil * rules.weaponRecoil(world.player.team));
        }
    }
}
