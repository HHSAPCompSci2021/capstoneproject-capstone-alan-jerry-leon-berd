package project.world.bullets;

import gameutils.struct.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.*;
import project.world.ship.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Represents a volley of one type of bullet. */
public class VolleyBullet extends Bullet{
    public Sprite sprite2;

    public VolleyBullet(){
        super();

        sprite = new BulletSprite("blaster-1");
        sprite2 = new BulletSprite("blaster-2");
        speed = 15;
        size = 3;
    }

    @Override
    public VolleyBulletEntity create(){
        return new VolleyBulletEntity(this);
    }

    public class VolleyBulletEntity extends BulletEntity{
        /** Whether the alternate sprite for this bullet should be drawn. */
        public boolean flip;

        public VolleyBulletEntity(VolleyBullet type){
            super(type);
        }

        @Override
        public void draw(){
            if(flip){
                Effects.glow.drawc(pos.x, pos.y, size() * 15, size() * 15, origin.color(), 30);
                sprite2.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, origin.color());
                sprite2.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, Color.white, 200);
            }else{
                super.draw();
            }
        }
    }
}
