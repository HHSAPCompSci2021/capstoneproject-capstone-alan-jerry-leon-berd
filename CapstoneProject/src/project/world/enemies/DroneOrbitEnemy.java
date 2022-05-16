package project.world.enemies;

import project.*;
import project.content.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.bullets.*;
import project.world.enemies.DroneOrbitEnemy.OrbitDrone.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores stats for an enemy with drones orbiting around it. */
public class DroneOrbitEnemy extends MultiEnemy{
    public int drones = 8;
    public float droneSpace = 30;
    public float orbitSpeed = 2;

    public float kiteDistance = 100;
    public boolean spacedShooting = false;

    public EnemyPart drone;

    public DroneOrbitEnemy(){
        super();

        color = new Color(80, 170, 255);
        size = 13;
    }

    @Override
    public void init(){
        for(int i = 0;i < drones;i++) pieces.add(drone);

        if(sprite == null) sprite = new Sprite(SpritePath.enemies, "host-body");

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
    }

    /** Stores stats for the drones that rotate around the enemy. */
    public class OrbitDrone extends EnemyPart{
        public OrbitDrone(){
            super();

            health = 5;
            size = 6;
            mass = 0.1f;
            reload = 0.5f;
        }

        @Override
        public void init(){
            super.init();

            if(sprite == null) sprite = new Sprite(SpritePath.enemies, "host-drone-1");
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
                canvas.tint(255, 255, 255);
                sprite.drawc(pos.x, pos.y, size() * 5, size() * 5, rotation);
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
        }
    }
}
