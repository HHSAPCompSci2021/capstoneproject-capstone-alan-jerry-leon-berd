package project.core;

import sound.jaysound.JayLayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Sounds {
    private static final HashMap<String, Integer> songMap = new HashMap<>();
    private static final HashMap<String, SoundPlayer> soundMap = new HashMap<>();
    private static int currentSong = 0;
    private static JayLayer songs;

    public static void playSong(String name) {
        if (songMap.get(name) == null) return;

//        int idx = songMap.get(name);
//        while (currentSong != idx) {
//            songs.nextSong();
//            currentSong = (currentSong + 1) % songMap.size();
//        }
    }

    public static void playSound(String name) {
//        if (soundMap.get(name) == null) return;
//        soundMap.get(name).play();
    }

    public void init() {
//        songs = new JayLayer("assets/audio/music/", "assets/audio/effects/", false);
//        songs.addPlayList();
//        String[] songList = new String[]{"Neverend.mp3", "Slowburn.mp3", "Blueshift.mp3", "Rubidium.mp3", "Near_Miss.mp3", "Collider.mp3", "Superlumen.mp3", "Freefall.mp3", "Zeroed.mp3", "Singular.mp3"};
//        for (String song : songList) {
//            songMap.put(song, songMap.size());
//        }
//        songs.addSongs(0, songList);
//        songs.changePlayList(0);
//        songs.nextSong();
//        playSong("Neverend.mp3");
//
//        String[] soundList = new String[]{"fuel_explosion.wav", "field_explosion.wav", "car_explosion.wav", "laser_impact.wav", "mine_explosion.wav"};
//        for (String sound : soundList) {
//            soundMap.put(sound, new SoundPlayer("assets/audio/effects/" + sound));
//        }
    }

    public static class SoundPlayer implements Runnable {
        private static final int delta = 500;
        private final byte[] audioBytes;
        private SourceDataLine line = null;
        private int numBytes;
        private long lastPlayTime;

        public SoundPlayer(String fileName) {
            File soundFile = new File(fileName);
            AudioInputStream audioInputStream = null;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
                System.err.println(fileName.split("\\.")[1] + " is unsupported");
                System.exit(1);
            } catch (IOException e) {
                System.err.println("An IOException occurred. Most likely, " + fileName + " was not found");
                System.exit(1);
            }

            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            try {
                line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(audioFormat);
            } catch (LineUnavailableException ex) {
                System.err.println("Audio line unavailable");
                System.exit(1);
            }

            line.start();

            audioBytes = new byte[(int) soundFile.length()];

            try {
                numBytes = audioInputStream.read(audioBytes, 0, audioBytes.length);
            } catch (IOException ex) {
                System.err.println("Cannot read " + fileName);
                System.exit(1);
            }
        }

        public void run() {
            line.write(audioBytes, 0, numBytes);
        }

        public void play() {
            if (System.currentTimeMillis() < lastPlayTime + delta) return;
            lastPlayTime = System.currentTimeMillis();

            line.flush();
            new Thread(this).start();
        }
    }
}
