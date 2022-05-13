package project.world.ship;

import project.core.Content.*;
import project.world.*;

import java.awt.*;

import static gameutils.util.Mathf.*;

/** Stores stats for a shield. */
public class Shield extends Type{
    public Color color = new Color(120, 120, 255);
//    public Color color = new Color(255, 120, 120);

    public float max = 100;
    public float regen = 0.25f;

    @Override
    public ContentType type(){
        return ContentType.shield;
    }

    @Override
    public ShieldInstance create(){
        return new ShieldInstance(this);
    }

    /** Represents an instance of a shield. */
    public class ShieldInstance extends Instance{
        /** Stores the current health in the shield. */
        public float value;

        public ShieldInstance(Shield type){
            super(type);
            value = max;
        }

        /** Returns ratio of the shields current value to it's maximum value. */
        public float fin(){
            return value / type().max;
        }

        /** Updates this shield. */
        public void update(){
            value = min(max, value + regen);
        }

        @Override
        public Shield type(){
            return (Shield)type;
        }
    }
}