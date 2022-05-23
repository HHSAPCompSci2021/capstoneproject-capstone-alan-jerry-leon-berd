package project.world.ship;

import project.core.Content.*;
import project.core.Rules.*;
import project.world.*;

import static project.Vars.*;

/** Represents a status effect on a ship. */
public class StatusEffect extends Type{
    protected float damage;

    protected float[] mult = new float[Rule.all.length], add = new float[Rule.all.length];

    @Override
    public ContentType type(){
        return ContentType.status;
    }

    @Override
    public StatusEntry create(){
        return new StatusEntry(this);
    }

    /** Represents an instance of a status effect on a ship. */
    public class StatusEntry extends Instance{
        protected Ship ship;

        protected float life, lifetime;

        /**
         * Creates a status effect with the specified type.
         * @param type the type
         */
        public StatusEntry(StatusEffect type){
            super(type);
        }

        /**
         * Returns the life (time since added in frames) of this status effect.
         * @return the life
         */
        public float life(){
            return life;
        }

        /**
         * Set the ship this status is on
         * @param ship the ship
         * @return itself, for chaining
         */
        public StatusEntry ship(Ship ship){
            this.ship = ship;
            return this;
        }

        /** Updates this status effect instance. */
        public void update(){
            life += delta;
            ship.damage(damage);
        }

        /**
         * Returns whether this status effect has "expired" or not.
         * @return whether this status effect has "expired" or not
         */
        public boolean keep(){
            return life < lifetime;
        }

        @Override
        public StatusEffect type(){
            return (StatusEffect)type;
        }
    }
}
