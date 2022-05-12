package project.core;

import project.core.Content.*;

import static project.Vars.*;

public class Music implements ContentList{

    public Music(){
    }

    public void load(){
        layer.addPlayList();
        layer.addSongs(0, new String[]{"game1.mp3"});
        layer.changePlayList(0);
        layer.nextSong();
    }
}
