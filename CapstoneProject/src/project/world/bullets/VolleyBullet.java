package project.world.bullets;

import project.graphics.*;

import java.awt.*;

/** Represents a volley of one type of bullet. */
public class VolleyBullet extends Bullet{
    public BulletSprite flipSprite = new BulletSprite();

    public VolleyBullet(){
        super();

        sprite.set("blaster-1");
        flipSprite.set("blaster-2");
        speed = 15;
        size = 3.5f;
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
                flipSprite.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, origin.color());
                flipSprite.drawc(pos.x, pos.y, size() * 10, size() * 10, rotation + 90, Color.white, 200);
            }else super.draw();
        }
    }
}
