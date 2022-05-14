package project.content;

import project.core.Content.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.bullets.*;
import project.world.enemies.*;

/** Contains and loads all the enemy types in the game. */
public class Enemies implements ContentList{
    public static EnemyVersions host, gyrogun;

    @Override
    public void load(){
        host = new EnemyVersions(){{
            common = new DroneOrbitEnemy(){{
                drone = new OrbitDrone(){{
                    bullet = new Bullet(){{
                        damage = 5f;
                    }};
                }};
            }};
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
                    reload = 0.25f;
                    spacedShooting = true;
                    sprite = new Sprite(SpritePath.enemies, "host-drone-3");
                    bullet = new LaserBullet(){{
                        rotate = 2f;
                        damage = 5;
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
                shootRotation = 0.6f;
                shootInterval = 5;
                sprite = new Sprite(SpritePath.enemies, "gyrogun-3");
                size = 12;
            }};
        }};
    }
}
