package project.core;

import project.core.Content.*;

import static project.Vars.*;

public class Sounds implements ContentList{
    public Sounds(){
    }

    public void load(){
        layer.addSoundEffects(new String[]{"title1.mp3"});
        layer.playSoundEffect(0);
    }
}
