package project.core;

import gameutils.math.*;
import project.*;
import project.content.*;
import project.core.Events.*;
import project.game.*;
import project.graphics.Effects.Effect.*;
import project.world.bullets.Bullet.*;
import project.world.enemies.Enemy.*;
import project.world.ship.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores all entities and simulates the game. */
public class World{
    /** Stores all entities, in seperate lists. */
    public Entities<BulletEntity> bullets;
    public Entities<Ship> ships;
    public Entities<Experience> experience;
    public Entities<EffectEntity> effects;

    public Player player;
    public Waves waves;

    public Range2 bounds;

    public World(){
    }

    /** Initializes the world. */
    public void init(){
        bounds = new Range2(0, 0, width, height).expand(20);

        bullets = new Entities<>();
        ships = new Entities<>().tree();
        experience = new Entities<>();
        effects = new Entities<>();

        player = new Player();
//        player.weapon = Weapons.lance.create();
        player.pos.set(bounds.center());
        events.call(Event.modChange);
        player.init();
        ships.add(player);

        waves = new Waves();
        waves.spawnWave();

        events.on(Event.enemyDestroyed, event -> {
            boolean spawnNext = true;

            for(Ship ship : ships.all()){
                if(ship.keep() && ship.team != Team.player){
                    spawnNext = false;
                    break;
                }
            }

            if(spawnNext) waves.spawnWave();
        });
    }

    /** Simulates the world. */
    public void update(){
        experience.update();
        effects.update();
        bullets.update();
        ships.update();
    }

    /** Draws the world. */
    public void draw(){
        bullets.glow();
        ships.glow();

        experience.draw();
        effects.draw();
        bullets.draw();
        ships.draw();

        if(debug){
            bullets.hitbox();
            ships.hitbox();
            experience.hitbox();
        }
    }
}
