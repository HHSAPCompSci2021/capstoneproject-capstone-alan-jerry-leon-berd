package project.world.enemies;

import project.world.enemies.MultiEnemy.*;

import java.awt.*;

/** Stores stats for a part of an enemy. */
public class EnemyPart extends Enemy{
    public EnemyPart(){
        super();
    }

    @Override
    public EnemyPartEntity create(){
        return new EnemyPartEntity(this);
    }

    /** Represents and simulates a part of an Enemy. */
    public class EnemyPartEntity extends EnemyEntity{
        protected MultiEnemyEntity parent;

        public EnemyPartEntity(EnemyPart type){
            super(type);
        }

        @Override
        public void apply(float x, float y){
            parent.apply(x, y);
        }

        @Override
        public Color color(){
            return parent.color();
        }

        public EnemyPartEntity parent(MultiEnemyEntity parent){
            this.parent = parent;
            return this;
        }

        @Override
        public void init(){
            super.init();

            team = parent.team;
        }

        @Override
        public void update(){
            collided.add(parent);
            for(EnemyPartEntity part : parent.parts) collided.add(part);
            super.update();
        }

        @Override
        public boolean keep(){
            return super.keep() && parent.keep();
        }
    }
}