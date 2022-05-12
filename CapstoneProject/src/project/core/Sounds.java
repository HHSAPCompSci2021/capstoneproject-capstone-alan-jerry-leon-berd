package project.core;

import gameutils.struct.*;
import jay.jaysound.*;

public class Sounds{
    public JayLayer layer = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);

    public Seq<Sound> effects = new Seq<>();
    public Seq<Sound> songs = new Seq<>();

    public static SoundEffect title1;

    public static Song game1;

    public void init(){
        title1 = new SoundEffect("title1");
        game1 = new Song("game1");

        layer.addSoundEffects(list(effects));

        layer.addPlayList();
        layer.addSongs(0, list(songs));
        layer.changePlayList(0);
        layer.nextSong();

        title1.play();
    }

    public String[] list(Seq<Sound> seq){
        String[] all = new String[seq.size];
        for(int i = 0;i < seq.size;i++) all[i] = seq.get(i).name;
        return all;
    }

    public class Sound{
        public String name;
        public int id;

        public Sound(String name){
            this.name = name + ".mp3";
            id = seq().size;
            seq().add(this);
        }

        public Seq<Sound> seq(){
            return null;
        }
    }

    public class SoundEffect extends Sound{
        public SoundEffect(String name){
            super(name);
        }

        @Override
        public Seq<Sound> seq(){
            return effects;
        }

        public void play(){
            layer.playSoundEffect(id);
        }
    }

    public class Song extends Sound{
        public Song(String name){
            super(name);
            id = songs.size;
            songs.add(this);
        }

        @Override
        public Seq<Sound> seq(){
            return songs;
        }
    }
}
