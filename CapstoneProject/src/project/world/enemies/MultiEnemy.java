package project.world.enemies;

import gameutils.struct.*;
import project.world.enemies.EnemyPart.*;

import static project.Vars.*;

/** Stores stats for an enemy with multiple parts. */
public class MultiEnemy extends Enemy{
    public Seq<EnemyPart> pieces = new Seq<>();

    @Override
    public void init(){
        for(EnemyPart part : pieces) mass += part.mass;
        super.init();
    }

    @Override
    public MultiEnemyEntity create(){
        return new MultiEnemyEntity(this);
    }

    /** Represents and simulates an enemy with multiple parts. */
    public class MultiEnemyEntity extends EnemyEntity{
        public Seq<EnemyPartEntity> parts = new Seq<>();

        public MultiEnemyEntity(MultiEnemy type){
            super(type);
        }

        @Override
        public void init(){
            super.init();

            System.out.println("here");

            for(EnemyPart part : pieces){
                EnemyPartEntity e = part.create().parent(this);
                e.justSpawned = justSpawned;
                parts.add(e);
                world.ships.add(e);
            }
        }

        @Override
        public void update(){
            for(EnemyPartEntity part : parts) collided.add(part);
            super.update();
        }
    }
}
