package project.world.ship;

import project.core.Content.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.*;
import project.world.modifiers.*;

import java.awt.*;

import static gameutils.util.Mathf.*;

/** Stores stats for a shield. */
public class Shield extends Modifier{
    public Color color = new Color(120, 120, 255);
//    public Color color = new Color(255, 120, 120);

    public float max = 100;
    public float regen = 0.25f;

    public Shield(String name){
        super(name);
    }

    @Override
    public void init(){
        if(sprite == null) sprite = new Sprite(SpritePath.upgrades, "shield-" + name);

        super.init();
    }

    @Override
    public ContentType type(){
        return ContentType.shield;
    }

    @Override
    public ShieldInstance create(){
        return new ShieldInstance(this);
    }

    /** Represents an instance of a shield. */
    public class ShieldInstance extends ModInstance{
        /** Stores the current health in the shield. */
        public float value, hue;

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

            color = Color.getHSBColor(hue, 1f, 1f); //RGB PLAYER RGB PLAYER
            hue += 0.01f;
        }

        @Override
        public Shield type(){
            return (Shield)type;
        }
    }
}