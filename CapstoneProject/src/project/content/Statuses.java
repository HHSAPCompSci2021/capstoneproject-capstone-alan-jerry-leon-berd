package project.content;

import project.core.Content.*;
import project.core.Rules.*;
import project.world.ship.*;

import static java.lang.Math.*;
import static project.Vars.*;

public class Statuses implements ContentList{
    public static StatusEffect spawned, slow, burning, vulnerable;

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
        slow = new StatusEffect(){{
            mult[Rule.enginePower.id()] = -0.3f;
            mult[Rule.rotateSpeed.id()] = -0.7f;
        }};
        vulnerable = new StatusEffect(){
            {
                mult[Rule.vulnerability.id()] = 0.15f;
            }

            class VulnerableEntry extends StatusEntry{
                public VulnerableEntry(StatusEffect type){
                    super(type);
                }

                public void update(){
                    super.update();

                    for(StatusEntry entry : ship.statuses){
                        if(entry instanceof VulnerableEntry) life = min(life, entry.life);
                    }
                }
            }
        };
    }
}
