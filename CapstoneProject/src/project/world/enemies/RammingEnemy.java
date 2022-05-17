package project.world.enemies;

import project.*;
import project.core.*;
import project.graphics.*;
import project.graphics.Sprite.*;

import java.awt.*;

import static project.Vars.*;

public class RammingEnemy extends Enemy{
    public float ramDuration = 150;
    public float ramInterval = 3;

    public RammingEnemy(){
        super();

        accel = 2;
        color = new Color(50,110,255);
        health = 100;
        reload = 0.25f;
        size = 11;
    }

    public void init(){
        if(sprite == null) sprite = new Sprite(SpritePath.enemies, "juggernaut"); //TODO alan make a sprite

        super.init();
    }

    public RammingEnemyEntity create(){
        return new RammingEnemyEntity(this);
    }

    public class RammingEnemyEntity extends EnemyEntity{
        public RammingEnemyEntity(RammingEnemy type){
            super(type);
        }

        @Override
        public void update(){
            super.update();

            reloadt += reload();

            rotate(Tmp.v1.set(world.player.pos).sub(pos).ang());
            if(reloadt >= 60){
                if((reloadt - 60) % ramInterval < reload()){
                    thrust();
                }
            }
            if(reloadt >= 60 + ramDuration * reload()) reloadt = 0;

        }

        @Override
        public void remove(){
            super.remove();

            Sounds.playSound("car_explosion.mp3");
        }
    }
}