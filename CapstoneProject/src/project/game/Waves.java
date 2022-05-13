package project.game;

import gameutils.struct.*;
import project.*;
import project.content.*;
import project.core.Content.*;
import project.world.*;
import project.world.enemies.*;
import project.world.enemies.Enemy.*;
import project.world.enemies.EnemyPart.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class Waves{
    public int wave = 0;

    public void spawnWave(){
        wave++;

        Seq<Enemy> possible = new Seq<>();
        for(Type e : content.map[ContentType.enemy.id()]){
            if(!(e instanceof EnemyPart)) possible.add((Enemy)e);
        }
        int enemies = randInt(wave / 10, wave / 10 + 3) + 1;
        for(int i = 0;i < enemies;i++){
            Enemy chosen = possible.get(randInt(0, possible.size - 1));
            EnemyEntity e = chosen.create();
            e.team = Team.enemy;
            e.justSpawned = true;
            e.pos.set(Tmp.v1.set(random(0, width), random(0, height)).sub(world.bounds.center()).nor().scl(width).add(world.bounds.center()));
            System.out.println(e.pos);
            world.ships.add(e);
        }
    }
}
