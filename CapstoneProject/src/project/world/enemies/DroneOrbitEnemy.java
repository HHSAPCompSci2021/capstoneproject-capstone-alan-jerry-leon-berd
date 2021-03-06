package project.world.enemies;

import project.*;
import project.world.enemies.DroneOrbitEnemy.OrbitDrone.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores stats for an enemy with drones orbiting around it. */
public class DroneOrbitEnemy extends MultiEnemy{
    protected int drones = 8;
    protected float droneSpace = 50;
    protected float orbitSpeed = 2;

    protected float kiteDistance = 200;
    protected boolean spacedShooting = false;

    protected EnemyPart drone;

    public DroneOrbitEnemy(){
        super();

        sprite.set("host-body-p");
        accel = 0.2f;
        color = new Color(80, 170, 255);
        size = 24;
    }

    @Override
    public void init(){
        for(int i = 0;i < drones;i++) pieces.add(drone);

        super.init();
    }

    @Override
    public DroneOrbitEntity create(){
        return new DroneOrbitEntity(this);
    }

    /** Stores stats for the drones that rotate around the enemy. */
    public static class OrbitDrone extends EnemyPart{
        public OrbitDrone(){
            super();

            sprite.set("host-drone-p1");
            health = 10;
            size = 8;
            mass = 0.1f;
            reload = 0.5f;
        }

        @Override
        public OrbitDroneEntity create(){
            return new OrbitDroneEntity(this);
        }

        /** Represents and simulates the drones that rotate around the enemy. */
        public class OrbitDroneEntity extends EnemyPartEntity{
            public OrbitDroneEntity(OrbitDrone type){
                super(type);
            }

            @Override
            public void glow(){
            }

            @Override
            public void update(){
                rotate(Tmp.v1.set(world.player.pos).sub(pos).ang());

                reloadt += reload();
                if(reloadt >= 60){
                    reloadt = 0;
                    shoot(0);
                }
            }

            @Override
            public void remove(){
                super.remove();
            }
        }
    }

    /** Represents and simulates an enemy with drones orbiting around it. */
    public class DroneOrbitEntity extends MultiEnemyEntity{
        /** The current orbit angle of the drones orbiting around it. */
        public float orbit = 0;

        public DroneOrbitEntity(DroneOrbitEnemy type){
            super(type);
            deathSound = "field_explosion.mp3";
        }

        @Override
        public void init(){
            super.init();

            for(int i = 0;i < drones;i++){
                OrbitDroneEntity drone = (OrbitDroneEntity)parts.get(i);
                drone.vel.set(0, 0);
                drone.pos.set(Tmp.v1.setr(orbit + 360f / drones * i, droneSpace).add(pos));
                if(spacedShooting) drone.reloadt = 60 * ((float)i / drones);
            }
        }

        @Override
        public void update(){
            super.update();
            orbit += orbitSpeed * delta;

            for(int i = 0;i < drones;i++){
                OrbitDroneEntity drone = (OrbitDroneEntity)parts.get(i);
                if(drone.keep()){
                    drone.vel.set(0, 0);
                    drone.pos.set(Tmp.v1.setr(orbit + 360f / drones * i, droneSpace).add(pos));
                }
            }

            rotate(Tmp.v1.set(world.player.pos).sub(pos).ang());
            if(dst(world.player.pos, pos) > kiteDistance) thrust();
        }
    }
}
