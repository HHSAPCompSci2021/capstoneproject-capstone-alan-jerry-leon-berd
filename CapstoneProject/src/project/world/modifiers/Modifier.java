package project.world.modifiers;

import project.core.Content.*;
import project.core.Rules.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.*;

/** Stores stats for a modifier. */
public class Modifier extends Type{
    public Sprite sprite;
    public String name;

    public boolean recursive = false;

    public float[] mult = new float[Rule.all.length];
    public float[] add = new float[Rule.all.length];

    public Modifier(String name){
        super();
        this.name = name;
    }

    @Override
    public void init(){
        if(sprite == null) sprite = new Sprite(SpritePath.upgrades, "mod-" + name);

        super.init();
    }

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
    public ModInstance create(){
        return new ModInstance(this);
    }

    /** Represents and simulates an instance of a modifier. */
    public class ModInstance extends Instance{
        public ModInstance(Modifier type){
            super(type);
        }

        @Override
        public Modifier type(){
            return (Modifier)type;
        }
    }
}
