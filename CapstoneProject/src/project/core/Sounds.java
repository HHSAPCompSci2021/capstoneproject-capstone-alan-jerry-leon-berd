package project.core;

import sound.jaysound.*;

import java.util.*;

public class Sounds{
    static HashMap<String, Integer> songMap = new HashMap<>();
    static HashMap<String, Integer> soundMap = new HashMap<>();
    static int currentSong = 0;
    private static JayLayer songs;
//    private static PriorityQueue<JayLayer> sounds;

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

//        sounds = new PriorityQueue<>((o1, o2) -> {
//            if(o1.isPlaying()){
//                if(o2.isPlaying()){
//                    return 0;
//                }
//                return -1;
//            }
//            if(o2.isPlaying()){
//                return 1;
//            }
//            return 0;
//        });
        String[] soundList = new String[]{"shockwave.mp3", "fuel_explosion.mp3", "field_explosion.mp3", "car_explosion.mp3", "fire_explosion.mp3", "laser_impact.mp3"};
        for(String sound : soundList){
            soundMap.put(sound, soundMap.size());
        }
//        addSounds();
//        playSounds("shockwave.mp3");
//        playSounds("shockwave.mp3");
//        playSounds("shockwave.mp3");
    }

//    private static void addSounds(){
//        JayLayer tmp = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);
//        tmp.addSoundEffects(new String[]{"shockwave.mp3", "fuel_explosion.mp3", "field_explosion.mp3", "car_explosion.mp3", "fire_explosion.mp3", "laser_impact.mp3"});
//        sounds.add(tmp);
//    }

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

    public static void playSounds(String name){
//        if(soundMap.get(name) == null){
//            return;
//        }
//
        int idx = soundMap.get(name);
//        if(sounds.peek() == null){
//            return;
//        }
//        if(sounds.peek().isPlaying()){
//            addSounds();
//        }
//        if(sounds.peek() == null){
//            return;
//        }
//        sounds.peek().playSoundEffect(idx);



        JayLayer tmp = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);
        tmp.addSoundEffects(new String[]{"shockwave.mp3", "fuel_explosion.mp3", "field_explosion.mp3", "car_explosion.mp3", "fire_explosion.mp3", "laser_impact.mp3"});
        tmp.playSoundEffect(idx);
    }
}
