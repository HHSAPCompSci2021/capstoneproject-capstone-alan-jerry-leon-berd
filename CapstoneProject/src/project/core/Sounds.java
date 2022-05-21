package project.core;

import sound.jaysound.*;

import java.util.*;

public class Sounds{
    private static final HashMap<String, Integer> songMap = new HashMap<>();
    private static final HashMap<String, Integer> soundMap = new HashMap<>();
    private static int currentSong;
    private static JayLayer layer;

    public static void playSong(String name){
        if(songMap.get(name) == null){
            return;
        }

        int idx = songMap.get(name);
        while(currentSong != idx){
            layer.nextSong();
            currentSong = (currentSong + 1) % songMap.size();
        }
    }

    public static void playSound(String name){
        if(soundMap.get(name) == null){
            return;
        }

        layer.playSoundEffect(soundMap.get(name));
    }

    public void init(){
        layer = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);

        layer.addPlayList();
        String[] songList = new String[]{"Neverend.mp3", "Slowburn.mp3", "Blueshift.mp3", "Rubidium.mp3", "Near_Miss.mp3", "Collider.mp3", "Superlumen.mp3", "Freefall.mp3", "Zeroed.mp3", "Singular.mp3"};
        for(String song : songList){
            songMap.put(song, songMap.size());
        }
        layer.addSongs(0, songList);
        layer.changePlayList(0);
        layer.nextSong();
        playSong("Neverend.mp3");

        String[] soundList = new String[]{"fuel_explosion.mp3", "field_explosion.mp3", "car_explosion.mp3", "laser_impact.mp3", "mine_explosion.mp3"};
        for(String sound : soundList){
            soundMap.put(sound, soundMap.size());
        }
        layer.addSoundEffects(soundList);
    }
}
