package project.content;

import project.core.Content.*;
import project.world.enemies.*;

/** Contains a list of all enemy types in the game. */
public class Enemies implements ContentList{
    public static Enemy basic, orbiting, rammer;

    @Override
    public void load(){
        basic = new Enemy();
        orbiting = new DroneOrbitEnemy();
        rammer = new Enemy(){
        };
    }
}
