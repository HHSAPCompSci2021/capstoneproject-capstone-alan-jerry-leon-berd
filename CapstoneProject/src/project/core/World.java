package project.core;

import gameutils.math.*;
import project.content.*;
import project.graphics.Effects.Effect.*;
import project.world.bullets.Bullet.*;
import project.world.enemies.Enemy.*;
import project.world.ship.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores all entities and simulates the game. */
public class World{
    public Entities<BulletEntity> bullets;
    public Entities<Ship> ships;
    public Entities<Experience> experience;
    public Entities<EffectEntity> effects;

    public Player player;

    public Range2 bounds;

    public World(){
    }

    public void init(){
        bounds = new Range2(0, 0, width, height).expand(20);

        bullets = new Entities<>();
        ships = new Entities<>().tree();
        experience = new Entities<>();
        effects = new Entities<>();

        player = new Player();
//        player.weapon = Gear.thruster.create();
        player.pos.set(bounds.center());
//        player.addMod(Modifiers.shotgunShells);
//        player.addMod(Modifiers.doubleShot);
        player.init();
        ships.add(player);

        for(int i = 0;i < 1;i++){
            EnemyEntity e = Enemies.orbiting.create();
            e.pos.set(random(0, width), random(0, height));
            ships.add(e);
        }
    }

    public void update(){
        experience.update();
        effects.update();
        bullets.update();
        ships.update();
    }

    public void draw(){
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
