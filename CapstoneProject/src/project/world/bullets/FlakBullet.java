package project.world.bullets;

import project.graphics.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

public class FlakBullet extends Bullet{
    public FlakBullet(){
        super();

        sprite.set("flak");

        trailDuration = 10;
        trailSize = 2;
        speed = 30;
        lifetime = 8;
        damage = 30;
    }

    public FlakBulletEntity create(){
        return new FlakBulletEntity(this);
    }

    /** Represents and simulates a laser bullet. */
    public class FlakBulletEntity extends BulletEntity{
        public FlakBulletEntity(FlakBullet type){
            super(type);
        }

        public float damage(){
            return super.damage() * fin();
        }
    }
}
