package project.world.modifiers;

import gameutils.struct.*;
import project.core.Content.*;
import project.core.Rules.*;
import project.game.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.*;

import static project.Vars.*;

/** Stores stats for a modifier. */
public class Modifier extends Type{
    public Sprite sprite;
    public String name;
    public String tag;
    public Seq<String> pros, cons;

    public boolean recursive;

    public float[] mult = new float[Rule.all.length];
    public float[] add = new float[Rule.all.length];

    public Modifier(String name){
        super();
        this.name = name;
        tag = "MOD";
        pros = new Seq<>();
        cons = new Seq<>();
    }

    public void addPro(String point){
        pros.add(" > " + point.toUpperCase());
    }

    public void addCon(String point){
        cons.add(" > " + point.toUpperCase());
    }

    @Override
    public void init(){
        if(sprite == null) sprite = new Sprite(SpritePath.upgrades, "mod-" + name);

        for(int i = 0;i < Rule.all.length;i++){
            if(mult[i] > 0) addPro("+" + (int)(mult[i] * 100) + "% " + Rule.all[i].name);
            if(mult[i] < 0) addCon("-" + (int)(-mult[i] * 100) + "% " + Rule.all[i].name);

            if(add[i] > 0) addPro("+" + (int)add[i] + " " + Rule.all[i].name);
            if(add[i] < 0) addCon("-" + (int)-add[i] + " " + Rule.all[i].name);
        }

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
    public static class ModInstance extends Instance{
        public ModInstance(Modifier type){
            super(type);
        }

        public Player player(){
            return world.player;
        }

        @Override
        public Modifier type(){
            return (Modifier)type;
        }
    }
}
