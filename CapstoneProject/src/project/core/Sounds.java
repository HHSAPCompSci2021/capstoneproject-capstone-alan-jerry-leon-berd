package project.core;

import sound.jaysound.*;

import java.util.*;

public class Sounds{
    private static final HashMap<String, Integer> songMap = new HashMap<>();
    private static final HashSet<String> soundSet = new HashSet<>();
    private static int currentSong = 0;
    private static JayLayer songs;

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
        if(!soundSet.contains(name))
            return;

        JayLayer tmp = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);
        tmp.addSoundEffects(new String[]{name});
        tmp.playSoundEffect(0);
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
        playSong("Slowburn.mp3");

        soundSet.addAll(Arrays.asList("shockwave.mp3", "fuel_explosion.mp3", "field_explosion.mp3", "car_explosion.mp3", "fire_explosion.mp3", "laser_impact.mp3"));
    }
}
