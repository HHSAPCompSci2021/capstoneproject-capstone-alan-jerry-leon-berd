package project.content;

import project.core.Content.*;
import project.world.ship.*;

import static project.Vars.*;

public class Statuses implements ContentList{
    public static StatusEffect spawned, burning;

    @Override
    public void load(){
        spawned = new StatusEffect(){
            @Override
            public SpawnedEntry create(){
                return new SpawnedEntry(this);
            }

            class SpawnedEntry extends StatusEntry{
                public SpawnedEntry(StatusEffect type){
                    super(type);
                }

                @Override
                public boolean keep(){
                    return !world.bounds.contains(ship);
                }
            }
        };
    }
}
