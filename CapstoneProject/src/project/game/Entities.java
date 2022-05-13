package project.game;

import gameutils.func.*;
import gameutils.math.*;
import gameutils.struct.*;
import gameutils.struct.tree.*;
import project.*;
import project.world.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Stores a list of entities, along with a quadtree, if specified. */
public class Entities<T extends Entity>{
    public Seq<T> arr = new Seq<>();

    public Seq<T> entities;
    public Seq<T> buffer;
    public QuadTree<T> tree;

    public Entities(){
        entities = new Seq<>();
        buffer = new Seq<>();
    }

    public Entities tree(){
        tree = new QuadTree<>(world.bounds.cpy().expand(universalSpeedLimit * 4));
        return this;
    }

    public void add(T entity){
        entity.init();
        entities.add(entity);
    }

    public void each(Cons<T> cons){
        for(T e : entities) if(e.keep()) cons.get(e);
    }

    public void range(Range2 range, Cons<T> cons){
        if(tree == null) return;
        arr.clear();
        tree.findAll(arr, range);
        for(T e : arr) if(e.keep()) cons.get(e);
    }

    public void query(float x, float y, float r, Cons<T> cons){
        if(tree == null) return;
        range(Tmp.r1.set(x, y, 0, 0).expand(r), e -> {
            if(dst(x, y, e.x(), e.y()) < r) cons.get(e);
        });
    }

    public void raycast(float x, float y, float r, float ang, float len, Cons2<T, Vec2> cons){
        Vec2 pos = new Vec2();
        for(float i = 0;i < len + raycastLength;i += raycastLength){
            pos.set(i, 0f).rot(ang).add(x, y);
            query(pos.x, pos.y, r, t -> cons.get(t, pos));
        }
    }

    public void update(){
        if(tree != null){
            tree.clear();
            for(T e : entities) tree.add(e);
        }

        buffer.clear();
        for(T e : entities){
            if(e.keep()){
                buffer.add(e);
                e.update();
            }else e.remove();
        }
        entities.clear();
        entities.addAll(buffer);
    }

    public void draw(){
        for(T e : entities) if(e.keep()) e.draw();
    }

    public void hitbox(){
        canvas.noFill();
        canvas.stroke(150, 150, 150, 100);
        canvas.strokeWeight(2);
        for(T e : entities) if(e.keep()) canvas.ellipse(e.x(), e.y(), e.size() * 2);
    }
}
