package project.core;

import sound.jaysound.*;

import java.util.*;

import static project.Vars.*;

public class Sounds{
    private static final HashMap<String, Integer> songMap = new HashMap<>();
    private static final HashMap<String, Integer> soundMap = new HashMap<>();
    private static int currentSong;
    private static JayLayer layer;

    /**
     * Play the specified song
     * @param name the name of the song
     */
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

    /** Stop the current song. */
    public static void stopSong(){
        layer.stopSong();
    }

    /** Resume the current song. */
    public static void resumeSong(){
        layer.nextSong();
    }

    /**
     * Play the specified sound
     * @param name the name of the sound
     */
    public static void playSound(String name){
        if(soundMap.get(name) == null){
            return;
        }

        layer.playSoundEffect(soundMap.get(name));
    }

    /** Initializes all sounds. */
    public void init(){
        layer = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);

        layer.addPlayList();
        String[] songList = new String[]{"Neverend.mp3", "Slowburn.mp3", "Blueshift.mp3", "Rubidium.mp3", "Near_Miss.mp3", "Collider.mp3", "Superlumen.mp3", "Freefall.mp3", "Zeroed.mp3", "Singular.mp3"};
        for(String song : songList){
            songMap.put(song, songMap.size());
        }
        layer.addSongs(0, songList);
        layer.changePlayList(0);
        if(music){
            layer.nextSong();
            playSong("Neverend.mp3");
        }

        String[] soundList = new String[]{"car_explosion.mp3", "ComputerSFX.mp3", "digital_slow_down.mp3", "digital_slow_down_2.mp3", "digital_speed_up.mp3", "digital_speed_up_2.mp3", "field_explosion.mp3", "fireworks_burst.mp3", "fuel_explosion.mp3", "laser_impact.mp3", "mine_explosion.mp3"};
        for(String sound : soundList){
            soundMap.put(sound, soundMap.size());
        }
        layer.addSoundEffects(soundList);
    }
}
