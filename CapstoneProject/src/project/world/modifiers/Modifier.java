package project.world.modifiers;

import project.core.Content.*;
import project.core.Rules.*;
import project.world.*;

/** Stores stats for a modifier. */
public class Modifier extends Type{
    public boolean recursive = false;

    public float[] multiplier = new float[Rule.all.length];

    public Modifier set(Rule rule, float value){
        multiplier[rule.id()] = value;
        return this;
    }

    @Override
    public ContentType type(){
        return ContentType.modifier;
    }

    @Override
    public ModEntry create(){
        return new ModEntry(this);
    }

    /** Represents and simulates an instance of a modifier. */
    public class ModEntry extends Instance{
        public ModEntry(Modifier type){
            super(type);
        }

        public void apply(){
        }

        public void shoot(){
        }

        public void update(){
        }

        @Override
        public Modifier type(){
            return (Modifier)type;
        }
    }
}
