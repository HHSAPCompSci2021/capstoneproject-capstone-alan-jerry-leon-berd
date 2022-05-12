package project.core;

import jay.jaysound.*;
import project.core.Content.*;

public class Sounds implements ContentList{
    public JayLayer layer;

    public void load(){
//        layer = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);
//        layer.addPlayList();
//        layer.addSongs(0, new String[]{"Chicken.mp3"});
//        layer.addSoundEffects(new String[]{"Chicken.mp3"});
//        layer.changePlayList(0);
//        layer.nextSong();
    }

    public Sounds() {
        layer = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);
        layer.addPlayList();
        layer.addSongs(0, new String[]{"title1.mp3"});
        layer.addSoundEffects(new String[]{"title1.mp3"});
        layer.changePlayList(0);
//        layer.playSoundEffect(0);
        layer.nextSong();
    }
}
