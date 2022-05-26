package project.world.enemies;

import gameutils.struct.*;

/** Stores different versions of an enemy. */
public class EnemyVersions{
    public static Seq<EnemyVersions> all = new Seq<>();

    /** The common version of this enemy. */
    public Enemy common;
    /** The elite version of this enemy. */
    public Enemy elite;
    /** The champion version of this enemy. */
    public Enemy champion;

    /** Create a new enemy version storage. */
    public EnemyVersions(){
        all.add(this);
    }
}
