package project.content;

import project.core.Content.*;
import project.world.bullets.*;

/** Stores the default bullets. */
public class Bullets implements ContentList{
    public static Bullet bullet, volley, missile, laser;

    @Override
    public void load(){
        bullet = new Bullet();
        volley = new VolleyBullet();
        missile = new MissileBullet();
        laser = new LaserBullet();
    }
}
