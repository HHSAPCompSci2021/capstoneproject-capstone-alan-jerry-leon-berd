package project.content;

import project.core.Content.*;
import project.world.bullets.*;

/** Contains and loads most of the bullet types in the game. TODO: It might be better to remove this in favor of creating bullets whenever needed. */
public class Bullets implements ContentList{
    public static Bullet normal, volley, volleySmall, small, mine, laser, railgun;

    @Override
    public void load(){
        normal = new Bullet();
        volley = new VolleyBullet();
        mine = new MineBullet();
        laser = new LaserBullet();
        railgun = new RailgunBullet();
    }
}
