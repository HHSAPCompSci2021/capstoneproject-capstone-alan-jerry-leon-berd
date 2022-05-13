package project.content;

import project.core.Content.*;
import project.world.bullets.*;

/** Contains a list of all bullet types in the game. */
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
