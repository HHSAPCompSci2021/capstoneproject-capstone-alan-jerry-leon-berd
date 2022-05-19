package project.world.ship;

import project.core.Content.*;
import project.world.*;

public class StatusEffect extends Type{
    public float damage;

    @Override
    public ContentType type(){
        return ContentType.status;
    }

    @Override
    public StatusEntry create(){
        return new StatusEntry(this);
    }

    public class StatusEntry extends Instance{
        public Ship ship;

        public float life, lifetime;

        public StatusEntry(StatusEffect type){
            super(type);
        }

        public StatusEntry ship(Ship ship){
            this.ship = ship;
            return this;
        }

        public void update(){
            life ++;
            ship.damage(damage);
        }

        public boolean keep(){
            return life < lifetime;
        }

        public StatusEffect type(){
            return (StatusEffect)type;
        }
    }
}
