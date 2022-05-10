package project.world.enemies.ai;

import project.world.enemies.Enemy.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Implements an AI that makes an enemy "kite" the player. */
public class KiteAI extends ShootAI{
    public float kiteDistance = 150;

    public KiteAI(){
    }

    public KiteAI(float distance){
        kiteDistance = distance;
    }

    public void update(EnemyEntity e){
        super.update(e);
        if(dst(world.player.pos, e.pos) > kiteDistance) e.thrust(e.type().accel);
    }
}
