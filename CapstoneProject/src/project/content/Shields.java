package project.content;

import project.core.Content.*;
import project.world.ship.shields.*;

/** Contains and loads all the shield types in the game. */
public class Shields implements ContentList{
    public static Shield standard, reflect, shockwave, warp, temporal;

    @Override
    public void load(){
        standard = new Shield("standard");
        reflect = new ReflectShield("reflect"){{
            addPro("Reflects enemy projectiles when shield is damaged");
            addPro("Projectiles are reflected with massive increased damage and speed");
            addPro("There is a cooldown between each reflection");
        }};
        shockwave = new ShockwaveShield("shockwave"){{
            addPro("Destroys projectiles and damages enemies when shield is broken");
        }};
        warp = new WarpShield("warp"){{
            addPro("Warps the player to another location when shield is damaged");
            addPro("There is a cooldown between each teleportation");
        }};
        temporal = new TemporalShield("temporal"){{
            addPro("Slows time whenever you are damaged");
        }};
    }
}
