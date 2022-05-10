package project.world.enemies.ai;

import project.*;
import project.world.enemies.Enemy.*;

import static project.Vars.*;

/** Implements an AI that makes an enemy target and shoot the player. */
public class ShootAI extends EnemyAI{
    public ShootAI(){
    }

    public void update(EnemyEntity e){
        e.rotate(Tmp.v1.set(world.player.pos).sub(e.pos).ang(), e.type().rotate);
        e.shoot();
    }
}
