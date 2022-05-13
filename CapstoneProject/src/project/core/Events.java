package project.core;

import gameutils.func.*;
import gameutils.struct.*;

/** Processes and stores runnables when an event is called. */
public class Events{
    public Seq<Cons<Event>>[] on;
    public Set<Cons<Event>>[] set; //(Hash) Sets, to prevent duplicates (Especially when init is called multiple times to reset the game)

    public Events(){
    }

    public void init(){
        on = new Seq[Event.all.length];
        set = new Set[Event.all.length];
        for(int i = 0;i < on.length;i++){
            on[i] = new Seq<>();
            set[i] = new Set<>();
        }
    }

    /** Add a runnable to the specified event. */
    public void on(Event event, Cons<Event> cons){
        if(set[event.id()].contains(cons)) return;
        on[event.id()].add(cons);
        set[event.id()].add(cons);
    }

    /** Throw an event, and run all related runnables. */
    public void call(Event event){
        for(Cons<Event> cons : on[event.id()]) cons.get(event);
    }

    /** Acs as a trigger id, which is thrown whenever the corresponding event happens. */
    public enum Event{
        modChange,
        expGain,
        levelUp,

        playerDamage,
        enemyDestroyed;

        public static Event[] all = values();

        public int id(){
            return ordinal();
        }
    }
}
