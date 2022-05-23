package project.world.bullets;

import project.graphics.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

/** Contains presets for a flak bullet. */
public class FlakBullet extends Bullet{
    public FlakBullet(){
        super();

        sprite.set("flak");

        trailDuration = 10;
        trailSize = 3;
        speed = 30;
        lifetime = 8;
        damage = 30;
    }

    public FlakBulletEntity create(){
        return new FlakBulletEntity(this);
    }

    /** Represents and simulates a flak bullet. */
    public class FlakBulletEntity extends BulletEntity{
        public FlakBulletEntity(FlakBullet type){
            super(type);
        }

        @Override
        public float damage(){
            return super.damage() * fin();
        }
    }
}
