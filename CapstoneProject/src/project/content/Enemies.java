package project.content;

import project.core.Content.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.bullets.*;
import project.world.enemies.*;

/** Contains and loads all the enemy types in the game. */
public class Enemies implements ContentList{
    public static EnemyVersions host, gyrogun, rammer;

    @Override
    public void load(){
        host = new EnemyVersions(){{
            common = new DroneOrbitEnemy(){{
                drone = new OrbitDrone(){{
                    bullet = new Bullet(){{
                        damage = 5f;
                        size = 2;
                    }};
                }};
            }};
            elite = new DroneOrbitEnemy(){{
                spacedShooting = true;
                drone = new OrbitDrone(){{
                    reload = 1;
                    sprite = new Sprite(SpritePath.enemies, "host-drone-2");
                    bullet = new Bullet(){{
                        damage = 10f;
                        speed = 10;
                        size = 3;
                    }};
                }};
                size = 15;
                droneSpace = 35;
            }};
            champion = new DroneOrbitEnemy(){{
                drone = new OrbitDrone(){{
                    reload = 0.5f;
                    spacedShooting = true;
                    sprite = new Sprite(SpritePath.enemies, "host-drone-3");
                    bullet = new LaserBullet(){{
                        rotate = 2f;
                        damage = 10;
                    }};
                }};
                size = 17;
                droneSpace = 40;
                kiteDistance = 300;
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
                rotate = 0.6f;
                shootInterval = 5;
                sprite = new Sprite(SpritePath.enemies, "gyrogun-3");
                size = 12;
            }};
        }};

        rammer = new EnemyVersions(){{
            common = new RammingEnemy();
//            elite = new RammingEnemy(){{
//                reload = 0.5f;
//                size = 13;
//                health = 125;
//            }};
//            champion = new RammingEnemy(){{
//                reload = 0.75f;
//                size = 15;
//                health = 150;
//            }};
        }};
    }
}
