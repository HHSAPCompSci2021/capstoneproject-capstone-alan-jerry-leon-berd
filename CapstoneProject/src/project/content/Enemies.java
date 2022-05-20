package project.content;

import project.core.Content.*;
import project.world.bullets.*;
import project.world.enemies.*;

/** Contains and loads all the enemy types in the game. */
public class Enemies implements ContentList{
    public static EnemyVersions host, gyrogun, juggernaut, dodger, tracer;

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
                    health = 25;
                    reload = 1;
                    sprite.set("host-drone-2");
                    bullet = new Bullet(){{
                        damage = 10f;
                        speed = 15;
                        size = 3;
                    }};
                }};
                size = 15;
                droneSpace = 35;
            }};
            champion = new DroneOrbitEnemy(){{
                health = 200;
                drone = new OrbitDrone(){{
                    health = 50;
                    reload = 0.5f;
                    rotate = 5f;
                    spacedShooting = true;
                    sprite.set("host-drone-3");
                    bullet = new LanceBullet(){{
                        size = 5;
                        lifetime = 50;
                        damage = 1;
                        damageInterval = 5;
                        speed = 75;
                    }};
                }};
                size = 17;
                droneSpace = 40;
                kiteDistance = 50;
            }};
        }};

        gyrogun = new EnemyVersions(){{
            common = new SprayerEnemy();
            elite = new SprayerEnemy(){{
                bullets = 4;
                reload = 0.3f;
                sprite.set("gyrogun-2");
                size = 12;
                bullet = new Bullet(){{
                    damage = 15;
                    size = 2;
                    speed = 5;
                }};
            }};
            champion = new SprayerEnemy(){{
                bullets = 8;
                reload = 0.5f;
                rotate = 0.6f;
                shootInterval = 5;
                sprite.set("gyrogun-3");
                size = 14;
                bullet = new Bullet(){{
                    damage = 15;
                    size = 3f;
                    speed = 5;
                }};
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
                    health = 40;
                    bullet = new MissileBullet(){{
                        damage = 5;
                        size = 3;
                        speed = 3;
                        accel = 0.07f;
                        homingPower = 0.1f;
                        homingRange = 2000;
                        lifetime = 3 * 60f;
                    }};
                }};
                thruster = new RammingThruster(){{
                    health = 40;
                    offset.set(0, 39);
                    size = 18;
                    ramPower = 70;
                }};
            }};
            elite = new RammingEnemy();
            champion = new RammingEnemy();
        }};
        
        tracer = new EnemyVersions() {{
        	common = new BomberEnemy();
            elite = new BomberEnemy(){{
                sprite.set("tracer-2");
                health = 75;
                size = 15;
                reload = 1.5f;
                shootInterval = 7;
                accel = 0.7f;
                bullet = new GrenadeBullet(){{
                    size = 4;
                    damage = 45;
                    splashRadius = 50;
                    speed = 4;
                }};
            }};
            champion = new BomberEnemy(){{
                sprite.set("tracer-3");
                health = 175;
                size = 20;
                reload = 1.2f;
                shootInterval = 5;
                accel = 1f;
                waveFrequency = 5f;
                waveAmplitude = 3;
                bullet = new MissileBullet(){{
                    speed = 3;
                    damage = 5;
                    accel = 0.2f;
                    homingPower = 0.04f;
                    homingRange = 2000;
                    lifetime = 3 * 60f;
                }};
            }};
        }};
        
//        dodger = new EnemyVersions(){{
//            common = new TeleportingEnemy();
//        }};
    }
}