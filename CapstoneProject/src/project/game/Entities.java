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
    private Seq<T> arr = new Seq<>();

    private Seq<T> entities;
    private Seq<T> buffer;
    private QuadTree<T> tree;

    public Entities(){
        entities = new Seq<>();
        buffer = new Seq<>();
    }

    /**
     * Creates a quadtree for this entity list.
     * @return itself, for chaining
     */
    public Entities tree(){
        tree = new QuadTree<>(world.bounds.cpy().expand(universalSpeedLimit * 5));
        return this;
    }

    /**
     * Returns a list of all the entities
     * @return the list
     */
    public Seq<T> all(){
        return entities;
    }

    /**
     * Add an entity to this list.
     * @param entity the specified entity
     */
    public void add(T entity){
        entity.init();
        entities.add(entity);
    }

    /**
     * Call the specified runnable for every entity in this list.
     * @param cons the specified runnable
     */
    public void each(Cons<T> cons){
        for(T e : entities) if(e.keep()) cons.get(e);
    }

    /**
     * Runs the specified runnable for every possible entity in the given range.
     * @param range the range
     * @param cons the runnable
     */
    public void range(Range2 range, Cons<T> cons){
        if(tree == null) return;
        arr.clear();
        tree.findAll(arr, range);
        for(T e : arr) if(e.keep()) cons.get(e);
    }

    /**
     * Runs the specified runnable for every possible entity in the circle defined with center at (x, y) and radius r.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param r the radius
     * @param cons the runnable
     */
    public void query(float x, float y, float r, Cons<T> cons){
        if(tree == null) return;
        range(Tmp.r1.set(x, y, 0, 0).expand(r), e -> {
            if(dst(x, y, e.x(), e.y()) < r) cons.get(e);
        });
    }

    /**
     * Runs the specified runnable for every possible entity in the ray starting at (x, y), with width r, angle ang, and length len.
     * @param x the x coordinate of the origin
     * @param y the y coordinate of the origin
     * @param r the width of the ray casted
     * @param ang the angle of the ray casted
     * @param len the length of the ray casted
     * @param cons the runnable that's run; has two parameters, entity and position.
     */
    public void raycast(float x, float y, float r, float ang, float len, Cons2<T, Vec2> cons){
        Vec2 pos = new Vec2();
        for(float i = 0;i < len + raycastLength;i += raycastLength){
            pos.set(i, 0f).rot(ang).add(x, y);
            query(pos.x, pos.y, r, t -> cons.get(t, pos));
        }
    }

    /** Updates every valid entity in this list. */
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

    /** Draws the glow of every valid entity in this list. */
    public void glow(){
        for(T e : entities) if(e.keep()) e.glow();
    }

    /** Draws every valid entity in this list. */
    public void draw(){
        for(T e : entities) if(e.keep()) e.draw();
    }

    /** Draws the hitbox of every valid entity in this list. */
    public void hitbox(){
        canvas.noFill();
        canvas.stroke(150, 150, 150, 100);
        canvas.strokeWeight(2);
        for(T e : entities) if(e.keep()) canvas.ellipse(e.x(), e.y(), e.size() * 2);
    }
}
