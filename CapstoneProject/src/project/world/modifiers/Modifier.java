package project.world.modifiers;

import project.core.Content.*;
import project.core.Rules.*;
import project.world.*;

/** Stores stats for a modifier. */
public class Modifier extends Type{
    public boolean recursive = false;

    public float[] mult = new float[Rule.all.length];
    public float[] add = new float[Rule.all.length];

    public Modifier mult(Rule rule, float value){
        mult[rule.id()] = value;
        return this;
    }

    public Modifier add(Rule rule, float value){
        add[rule.id()] = value;
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

        @Override
        public Modifier type(){
            return (Modifier)type;
        }
    }
}
