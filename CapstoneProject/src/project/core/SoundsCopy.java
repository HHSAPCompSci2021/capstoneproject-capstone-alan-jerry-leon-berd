package project.core;

import sound.jaysound.*;

import java.util.*;


//LEON, PLEASE DO NOT DELETE :(
public class SoundsCopy{
    private static final HashMap<String, Integer> songMap = new HashMap<>();
    private static final HashMap<String, Integer> soundMap = new HashMap<>();
    private static int currentSong = 0;
    private static int soundIdx = 0;
    private static JayLayer songs;
    private static PriorityQueue<JayLayer> sounds;

    private static void addLayer(){
        JayLayer tmp = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);
        tmp.addSoundEffects(new String[]{"shockwave.mp3", "fuel_explosion.mp3", "field_explosion.mp3", "car_explosion.mp3", "fire_explosion.mp3", "laser_impact.mp3"});
        sounds.add(tmp);
    }

    public static void playSong(String name){
        if(songMap.get(name) == null){
            return;
        }

        int idx = songMap.get(name);
        while(currentSong != idx){
            songs.nextSong();
            currentSong = (currentSong + 1) % songMap.size();
        }
    }

    public static void playSound(String name){
        if(soundMap.get(name) == null){
            return;
        }

        soundIdx = soundMap.get(name);
        if(sounds.peek() == null) return; //Cuz warnings :(
        if(sounds.peek().isPlayingSoundEffect(soundIdx)) addLayer();
        if(sounds.peek() == null) return; //Cuz warnings :(
        sounds.peek().playSoundEffect(soundIdx);
    }

    public void init(){
        songs = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);
        songs.addPlayList();
        String[] songList = new String[]{"Neverend.mp3", "Slowburn.mp3", "Blueshift.mp3", "Rubidium.mp3", "Near_Miss.mp3", "Collider.mp3", "Superlumen.mp3", "Freefall.mp3", "Zeroed.mp3", "Singular.mp3", "Terminus.mp3", "Ares.mp3", "Seraphim.mp3", "Parsec.mp3", "Rainseed.mp3", "Low_Orbit.mp3", "Neverend_Waves.mp3", "Slowburn_Nova.mp3", "Rubidium_Core.mp3", "Collider_Synced.mp3", "Superlumen_Warped.mp3", "Blueshift_Centered.mp3", "Recursor.mp3", "Nograv.mp3", "Parallelism.mp3", "Parallelism_Freed.mp3", "Centerless.mp3", "Infinitum_Genesis.mp3", "Infinitum.mp3", "Eclipsed.mp3"};
        for(String song : songList){
            songMap.put(song, songMap.size());
        }
        songs.addSongs(0, songList);
        songs.changePlayList(0);
        songs.nextSong();
        playSong("Neverend.mp3");

        sounds = new PriorityQueue<>((o1, o2) -> {
            if(o1.isPlayingSoundEffect(soundIdx)){
                if(o2.isPlayingSoundEffect(soundIdx)){
                    return 0;
                }
                return 1;
            }
            if(o2.isPlayingSoundEffect(soundIdx)){
                return -1;
            }
            return 0;
        });
        String[] soundList = new String[]{"shockwave.mp3", "fuel_explosion.mp3", "field_explosion.mp3", "car_explosion.mp3", "fire_explosion.mp3", "laser_impact.mp3"};
        for(String sound : soundList){
            soundMap.put(sound, soundMap.size());
        }
        addLayer();
    }
}
