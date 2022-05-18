package project.content;

import project.core.Content.*;
import project.world.ship.*;

public class Statuses implements ContentList{
    public StatusEffect spawned, burning;

    @Override
    public void load(){
        spawned = new StatusEffect();
    }
}
