package project.content;

import project.core.Content.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.bullets.*;
import project.world.enemies.*;

/** Contains a list of all enemy types in the game. */
public class Enemies implements ContentList{
    public static EnemyVersions host, gyrogun;

    @Override
    public void load(){
        host = new EnemyVersions(){{
            common = new DroneOrbitEnemy();
            elite = new DroneOrbitEnemy(){{
                spacedShooting = true;
                drone = new OrbitDrone(){{
                    reload = 1;
                    sprite = new Sprite(SpritePath.enemies, "host-drone-2");
                }};
                size = 15;
            }};
            champion = new DroneOrbitEnemy(){{
                drone = new OrbitDrone(){{
                    reload = 0.3f;
                    spacedShooting = true;
                    sprite = new Sprite(SpritePath.enemies, "host-drone-3");
                    bullet = new LaserBullet(){{  //TODO: This is completely broken
                        rotate = 0.75f;
                        damage = 10;
                    }};
                }};
                size = 17;
            }};
        }};

        gyrogun = new EnemyVersions(){{
            common = new SprayerEnemy();
            elite = new SprayerEnemy(){{
                bullets = 4;
                reload = 0.3f;
                sprite = new Sprite(SpritePath.enemies, "gyrogun-2");
                size = 10;
            }};
            champion = new SprayerEnemy(){{
                bullets = 8;
                reload = 0.5f;
                sprite = new Sprite(SpritePath.enemies, "gyrogun-3");
                size = 12;
            }};
        }};
    }
}
