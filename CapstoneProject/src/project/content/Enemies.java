package project.content;

import gameutils.math.*;
import project.core.Content.*;
import project.world.bullets.*;
import project.world.enemies.*;

/** Contains and loads all the enemy types in the game. */
public class Enemies implements ContentList{
    public static EnemyVersions host, gyrogun, juggernaut, tracer;

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
                    sprite.set("host-drone-2");
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
                    sprite.set("host-drone-3");
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
                sprite.set("gyrogun-2");
                size = 10;
            }};
            champion = new SprayerEnemy(){{
                bullets = 8;
                reload = 0.5f;
                rotate = 0.6f;
                shootInterval = 5;
                sprite.set("gyrogun-3");
                size = 12;
            }};
        }};

        juggernaut = new EnemyVersions(){{
            common = new RammingEnemy(){{
                sprite.set("juggernaut-1");
                size = 25;
                accel = 0.2f;
                rotate = 0.8f;
                health = 250;
                side = new RammingSide(){{
                    offset.set(-21, 33);
                    size = 13;
                    shootDuration = 10;
                    shootInterval = 2;
                    bullet = new MissileBullet(){{
                        damage = 2;
                        size = 3;
                        speed = 3;
                        accel = 0.07f;
                        homingPower = 0.1f;
                        homingRange = 2000;
                        lifetime = 3 * 60f;
                    }};
                }};
                thruster = new RammingThruster(){{
                    offset.set(0, 39);
                    size = 18;
                    ramPower = 40;
                }};
            }};
            elite = new RammingEnemy();
        }};
        
        tracer = new EnemyVersions() {{
        	common = new BomberEnemy();
        }};
    }
}
