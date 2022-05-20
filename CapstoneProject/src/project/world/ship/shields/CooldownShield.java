package project.world.ship.shields;

import static project.Vars.*;

public class CooldownShield extends Shield{
    public float cooldown = 10f;

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

    public class CooldownShieldInstance extends ShieldInstance{
        public float cooldownt;

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

        public void effect(){
        }

        @Override
        public void update(){
            super.update();

            cooldownt += delta;
        }
    }
}
