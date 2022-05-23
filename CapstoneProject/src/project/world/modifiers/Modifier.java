package project.world.modifiers;

import gameutils.struct.*;
import project.core.Content.*;
import project.core.Rules.*;
import project.game.*;
import project.graphics.*;
import project.world.*;

import static project.Vars.*;

/** Stores stats for a modifier. */
public class Modifier extends Type{
    protected UpgradeSprite sprite = new UpgradeSprite();
    protected String name;
    protected String tag;
    protected Seq<String> pros, cons;

    protected float[] mult = new float[Rule.all.length];
    protected float[] add = new float[Rule.all.length];

    /**
     * Creates a new modifier with the specified name
     * @param name the name
     */
    public Modifier(String name){
        super();

        this.name = name;

        tag = "MOD";
        pros = new Seq<>();
        cons = new Seq<>();

        sprite.set("mod-" + name);
    }

    protected void addPro(String point){
        pros.add(" > " + point.toUpperCase());
    }

    protected void addCon(String point){
        cons.add(" > " + point.toUpperCase());
    }

    @Override
    public void init(){
        for(int i = 0;i < Rule.all.length;i++){
            if(mult[i] > 0) addPro("+" + (int)(mult[i] * 100) + "% " + Rule.all[i].name);
            if(mult[i] < 0) addCon("-" + (int)(-mult[i] * 100) + "% " + Rule.all[i].name);

            if(add[i] > 0) addPro("+" + (int)add[i] + " " + Rule.all[i].name);
            if(add[i] < 0) addCon("-" + (int)-add[i] + " " + Rule.all[i].name);
        }

        super.init();
    }

    /**
     * Returns the sprite of this modifier.
     * @return the sprite
     */
    public Sprite sprite(){
        return sprite;
    }

    /**
     * Returns the name of this modifier.
     * @return the name
     */
    public String name(){
        return name;
    }

    /**
     * Returns the tag of this modifier.
     * @return the tag
     */
    public String tag(){
        return tag;
    }

    /**
     * Returns the pros of this modifier.
     * @return the pros
     */
    public Seq<String> pros(){
        return pros;
    }

    /**
     * Returns the cons of this modifier.
     * @return the cons
     */
    public Seq<String> cons(){
        return cons;
    }

    /**
     * Returns the multiplier of a certain rule in this modifier.
     * @param rule the rule
     * @return the value
     */
    public float mult(Rule rule){
        return mult[rule.id()];
    }

    /**
     * Returns the additive of a certain rule in this modifier.
     * @param rule the rule
     * @return the value
     */
    public float add(Rule rule){
        return add[rule.id()];
    }

    /**
     * Sets the multiplier of a certain rule of this modifier
     * @param rule the rule
     * @param value the value
     * @return itself, for chaining
     */
    @Deprecated
    public Modifier mult(Rule rule, float value){
        mult[rule.id()] = value;
        return this;
    }

    /**
     * Sets the additive of a certain rule of this modifier
     * @param rule the rule
     * @param value the value
     * @return itself, for chaining
     */
    @Deprecated
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
        /**
         * Create a new instance of a modifier with the specified type
         * @param type the type
         */
        public ModInstance(Modifier type){
            super(type);
        }

        protected Player player(){
            return world.player;
        }

        @Override
        public Modifier type(){
            return (Modifier)type;
        }
    }

    protected class UpgradeSprite extends Sprite{
        public UpgradeSprite set(String name){
            super.set(SpritePath.upgrades, name);
            return this;
        }
    }
}
