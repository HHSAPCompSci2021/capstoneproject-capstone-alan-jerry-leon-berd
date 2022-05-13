package project.world.ship.weapons;

import project.*;
import project.content.*;
import project.graphics.*;
import project.world.bullets.*;
import project.world.bullets.Bullet.*;
import project.world.bullets.VolleyBullet.*;

import static project.Vars.*;

public class VolleyWeapon extends Weapon{
    public VolleyWeapon(){
        super();
        bullet = Bullets.volley;
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
            for(int i = 0;i < projectiles() * 2;i++){
                BulletEntity b = def(bullet.create());
//                float x = ((float)i / (actual - 1) - 0.5f) * spread;
                float x = (i - projectiles() + 0.5f) * spread;
                b.pos.set(world.player.hull.shootPos()).add(Tmp.v1.set(0, x).rot(world.player.rotation));
                Effects.gunfire.at(Tmp.v1.x, Tmp.v1.y, e -> e.color(0, world.player.color()).scale(1.3f).parent(world.player));
                world.bullets.add(b);
                if(b instanceof VolleyBulletEntity && i >= projectiles()) ((VolleyBulletEntity)b).flip = true;

                world.player.apply(Tmp.v1.set(-recoil(), 0).rot(world.player.rotation));
            }
        }
    }
}
