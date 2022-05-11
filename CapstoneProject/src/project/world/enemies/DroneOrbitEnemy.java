package project.world.enemies;

import project.*;
import project.content.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.enemies.DroneOrbitEnemy.OrbitDrone.*;
import project.world.enemies.ai.*;

import java.awt.*;

/** Stores stats for an enemy with drones orbiting around it. */
public class DroneOrbitEnemy extends MultiEnemy{
    public int drones = 8;
    public float droneSpace = 35;
    public float orbitSpeed = 2;

    public EnemyPart drone = new OrbitDrone();

    public DroneOrbitEnemy(){
        super();
        sprite = new Sprite(SpritePath.enemies, "host-body");
        color = new Color(120, 120, 255);
        reload = 0;
        size = 17;
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
            }
        }

        @Override
        public void update(){
            super.update();
            orbit += orbitSpeed;

            for(int i = 0;i < drones;i++){
                OrbitDroneEntity drone = (OrbitDroneEntity)parts.get(i);
                if(drone.keep()){
                    drone.vel.set(0, 0);
                    drone.pos.set(Tmp.v1.setr(orbit + 360f / drones * i, droneSpace).add(pos));
                }
            }
        }
    }

    /** Stores stats for the drones that rotate around the enemy. */
    public class OrbitDrone extends EnemyPart{
        public OrbitDrone(){
            super();
            health = 5;
            size = 6;
            mass = 0.1f;
            reload = 0.5f;

            bullet = Bullets.small;

            ai = new ShootAI();
            sprite = new Sprite(SpritePath.enemies, "host-drone-1");
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
            public void draw(){
                sprite.drawc(pos.x, pos.y, size() * 5, size() * 5, rotation);
            }
        }
    }
}
