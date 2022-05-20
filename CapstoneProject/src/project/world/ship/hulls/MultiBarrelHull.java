package project.world.ship.hulls;

import gameutils.math.*;
import gameutils.struct.*;
import project.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class MultiBarrelHull extends Hull{
    public Seq<Barrel> barrels = new Seq<>();

    public MultiBarrelHull(String name){
        super(name);
    }

    @Override
    public void init(){
        barrels.add(new Barrel(shootPos.x, shootPos.y, 0));

        super.init();
    }

    @Override
    public MultiHullInstance create(){
        return new MultiHullInstance(this);
    }

    public class MultiHullInstance extends HullInstance{
        public int next = 0;

        public MultiHullInstance(MultiBarrelHull type){
            super(type);
        }

        @Override
        public Vec2 shootPos(){
            return Tmp.v1.set(barrels.get(next).pos).rot(world.player.rotation).add(world.player.pos);
        }

        @Override
        public float shootRot(){
            return barrels.get(next).offset;
        }

        @Override
        public void shot(){
            next = (next + randInt(1, 2)) % barrels.size;
        }
    }

    public class Barrel{
        public Vec2 pos;
        public float offset;

        public Barrel(float x, float y, float offset){
            pos = new Vec2(x, y);
            this.offset= offset;
        }
    }
}
