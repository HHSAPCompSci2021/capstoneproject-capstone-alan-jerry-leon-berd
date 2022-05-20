package project.world.enemies;

import project.*;
import project.core.*;
import project.world.enemies.DroneOrbitEnemy.OrbitDrone.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores stats for an enemy with drones orbiting around it. */
public class DroneOrbitEnemy extends MultiEnemy{
    public int drones = 8;
    public float droneSpace = 30;
    public float orbitSpeed = 2;

    public float kiteDistance = 200;
    public boolean spacedShooting = false;

    public EnemyPart drone;

    public DroneOrbitEnemy(){
        super();

        sprite.set("host-body");
        accel = 0.2f;
        color = new Color(80, 170, 255);
        size = 13;
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

    /** Represents and simulates an enemy with drones orbiting around it. */
    public class DroneOrbitEntity extends MultiEnemyEntity{
        /** The current orbit angle of the drones orbiting around it. */
        public float orbit = 0;

        public DroneOrbitEntity(DroneOrbitEnemy type){
            super(type);
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

        @Override
        public void remove(){
            super.remove();

            Sounds.playSound("fuel_explosion.mp3");
        }
    }

    /** Stores stats for the drones that rotate around the enemy. */
    public class OrbitDrone extends EnemyPart{
        public OrbitDrone(){
            super();

            sprite.set("host-drone-1");
            health = 10;
            size = 6;
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
}
