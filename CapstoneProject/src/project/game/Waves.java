package project.game;

import gameutils.struct.*;
import project.*;
import project.core.Content.*;
import project.world.*;
import project.world.enemies.*;
import project.world.enemies.Enemy.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores and simulates all waves in the game. */
public class Waves{
    protected int wave = 0;

    /**
     * Returns the wave number
     * @return the wave
     */
    public int wave(){
        return wave;
    }

    /** Spawn the next wave. */
    public void spawnWave(){
//        rules.rules[Team.enemy.id()][Rule.weaponReload.ordinal()] += 0.1f;
//        rules.rules[Team.enemy.id()][Rule.bulletDamage.ordinal()] += 0.5f;

        wave++;

        int enemies = randInt(wave / 25, wave / 25 + 3) + 1;
        for(int i = 0;i < enemies;i++){
            EnemyVersions chosen = EnemyVersions.all.get(randInt(0, EnemyVersions.all.size - 1));
            int version = (int)min(random(0, rt2(wave / 5f)), 2);
            EnemyEntity e = (version == 0 ? chosen.common : version == 1 ? chosen.elite : chosen.champion).create();
            e.team = Team.enemy;
            e.pos.set(Tmp.v1.set(random(0, width), random(0, height)).sub(world.bounds.center()).nor().scl(width).add(world.bounds.center()));
            world.ships.add(e);
        }
    }
}
