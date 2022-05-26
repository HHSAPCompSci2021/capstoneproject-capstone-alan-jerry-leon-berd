package project.world.ship;

public interface ShipType{
    /**
     * Returns the acceleration of this ship.
     * @return the stat value
     */
    public float accel();

    /**
     * Returns the rotate speed of this ship.
     * @return the stat value
     */
    public float rotate();

    /**
     * Returns the mass of this ship.
     * @return the stat value
     */
    public float mass();

    /**
     * Returns the size of this ship.
     * @return the stat value
     */
    public float size();

    /**
     * Returns the max health of this ship.
     * @return the stat value
     */
    public float health();

    /**
     * Returns the ram damage of this ship.
     * @return the stat value
     */
    public float ram();

    /**
     * Returns the drag on this ship.
     * @return the stat value
     */
    public float drag();
}
