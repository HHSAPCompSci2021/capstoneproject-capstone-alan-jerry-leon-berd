package project.world.ship.shields;

import static project.Vars.*;

/** Stores stats for a shield with a cooldown. */
public class CooldownShield extends Shield{
    protected float cooldown = 10f;

    /**
     * Create a cooldown shield with the specified name
     * @param name the name
     */
    public CooldownShield(String name){
        super(name);
    }

    @Override
    public void init(){
        addPro("Cooldown: " + cooldown + " sec");

        super.init();
    }

    @Override
    public CooldownShieldInstance create(){
        return new CooldownShieldInstance(this);
    }

    /** Represents a shield with a cooldown. */
    protected class CooldownShieldInstance extends ShieldInstance{
        protected float cooldownt;

        public CooldownShieldInstance(CooldownShield type){
            super(type);
        }

        @Override
        public void damage(float damage){
            super.damage(damage);

            if(cooldownt < cooldown * 60) return;
            cooldownt = 0;

            effect();
        }

        protected void effect(){
        }

        @Override
        public void update(){
            super.update();

            cooldownt += delta;
        }
    }
}
