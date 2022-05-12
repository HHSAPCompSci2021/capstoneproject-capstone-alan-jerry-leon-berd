package project.world.ship;

import project.core.Content.*;
import project.world.*;

import java.awt.*;

import static gameutils.util.Mathf.*;

/** Stores stats for a shield. */
public class Shield extends Type{
    //    public Color color = new Color(120, 150, 255);
    public Color color = new Color(255, 120, 120);

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
        public float value;

        public ShieldInstance(Shield type){
            super(type);
            value = max;
        }

        public float fin(){
            return value / type().max;
        }

        public void update(){
            value = min(max, value + regen);
        }

        @Override
        public Shield type(){
            return (Shield)type;
        }
    }
}