package project.world.ship;

import project.world.*;

public class StatusEffect extends Type{
    public float damage;

    @Override
    public StatusEntry create(){
        return new StatusEntry(this);
    }

    public class StatusEntry extends Instance{
        public float life, lifetime;

        public StatusEntry(StatusEffect type){
            super(type);
        }

        public void update(Ship s){
            life ++;
            s.damage(damage);
        }

        public boolean keep(){
            return life < lifetime;
        }

        public StatusEffect type(){
            return (StatusEffect)type;
        }
    }
}
