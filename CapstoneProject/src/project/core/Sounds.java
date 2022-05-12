package project.core;

import gameutils.struct.*;
import jay.jaysound.*;
import project.core.Content.*;

public class Sounds implements ContentList{
    public JayLayer layer;

    public static Seq<SoundEffect> effects = new Seq<>();
    public static Seq<Music> songs = new Seq<>();

    public static SoundEffect test;
    public static Music music;

    public void load(){
//		test = new SoundEffect("bruh");
        music = new Music("Chicken.mp3");
        layer = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);
//		layer.addPlayList();
//		layer.addSongs(0,songs);
//		String[] tmp = new String[effects.size];
//		for(int i = 0;i < effects.size;i++) tmp[i] = effects.get(i).name;
//		layer.addSoundEffects(tmp);

//		tmp = new String[songs.size];
//		for(int i = 0;i < songs.size;i++) tmp[i] = songs.get(i).name;
        layer.addPlayList();
        layer.addSongs(0, new String[]{"Chicken.mp3", "Chicken2.mp3", "Chicken3.mp3"});
//		layer.nextSong();
        layer.changePlayList(0);
        layer.nextSong();
//		layer.changePlayList(0);
//		layer.addJayLayerListener(this);
    }


    public class SoundEffect{
        public String name;
        public int id;

        public SoundEffect(String name){
            id = effects.size;
            effects.add(this);
            this.name = name;
        }

        public void play(){
            layer.playSoundEffect(id);
        }
    }

    public class Music{
        public String name;
        public int id;

        public Music(String name){
            id = songs.size;
            songs.add(this);
            this.name = name;
        }
    }
}
