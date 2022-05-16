package project.core;

import gameutils.struct.*;
import jay.jaysound.*;

/**Stores all the sounds in the game.*/
public class Sounds {
    public static JayLayer layer = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);
    public Seq<Sound> effects = new Seq<>();
    public Seq<Sound> songs = new Seq<>();

    public static SoundEffect shockwave, fuel_explosion, field_explosion, car_explosion, fire_explosion, laser_impact;
    public static Song Neverend, Slowburn, Blueshift, Rubidium, Near_Miss, Collider, Superlumen, Freefall, Zeroed, Singular, Terminus, Ares, Seraphim, Parsec, Rainseed, Low_Orbit, Neverend_Waves, Slowburn_Nova, Rubidium_Core, Collider_Synced, Superlumen_Warped, Blueshift_Centered, Recursor, Nograv, Parallelism, Parallelism_Freed, Centerless, Infinitum_Genesis, Infinitum, Eclipsed;

    public void init() {
        {
            shockwave = new SoundEffect("shockwave");
            fuel_explosion = new SoundEffect("fuel_explosion");
            field_explosion = new SoundEffect("field_explosion");
            car_explosion = new SoundEffect("car_explosion");
            fire_explosion = new SoundEffect("fire_explosion");
            laser_impact = new SoundEffect("laser_impact");
        }
        layer.addSoundEffects(list(effects));

        {
            Neverend = new Song("Neverend");
            Slowburn = new Song("Slowburn");
            Blueshift = new Song("Blueshift");
            Rubidium = new Song("Rubidium");
            Near_Miss = new Song("Near_Miss");
            Collider = new Song("Collider");
            Superlumen = new Song("Superlumen");
            Freefall = new Song("Freefall");
            Zeroed = new Song("Zeroed");
            Singular = new Song("Singular");
            Terminus = new Song("Terminus");
            Ares = new Song("Ares");
            Seraphim = new Song("Seraphim");
            Parsec = new Song("Parsec");
            Rainseed = new Song("Rainseed");
            Low_Orbit = new Song("Low_Orbit");
            Neverend_Waves = new Song("Neverend_Waves");
            Slowburn_Nova = new Song("Slowburn_Nova");
            Rubidium_Core = new Song("Rubidium_Core");
            Collider_Synced = new Song("Collider_Synced");
            Superlumen_Warped = new Song("Superlumen_Warped");
            Blueshift_Centered = new Song("Blueshift_Centered");
            Recursor = new Song("Recursor");
            Nograv = new Song("Nograv");
            Parallelism = new Song("Parallelism");
            Parallelism_Freed = new Song("Parallelism_Freed");
            Centerless = new Song("Centerless");
            Infinitum_Genesis = new Song("Infinitum_Genesis");
            Infinitum = new Song("Infinitum");
            Eclipsed = new Song("Eclipsed");
        }
        layer.addPlayList();
        layer.addSongs(0, list(songs));
        layer.changePlayList(0);
        layer.nextSong();

        shockwave.play();
    }

    public String[] list(Seq<Sound> seq) {
        String[] all = new String[seq.size];
        for (int i = 0; i < seq.size; i++) all[i] = seq.get(i).name;
        return all;
    }

    public static class Sound {
        public String name;
        public int id;

        public Sound(String name) {
            this.name = name + ".mp3";
            id = seq().size;
            seq().add(this);
        }

        public Seq<Sound> seq() {
            return null;
        }
    }

    public class SoundEffect extends Sound {
        public SoundEffect(String name) {
            super(name);
        }

        @Override
        public Seq<Sound> seq() {
            return effects;
        }

        public void play() {
            layer.playSoundEffect(id);
        }
    }

    public class Song extends Sound {
        public Song(String name) {
            super(name);
            id = songs.size;
        }

        @Override
        public Seq<Sound> seq() {
            return songs;
        }
    }
}
