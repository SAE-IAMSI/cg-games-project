package games.project.prehispong.sound;

import games.project.prehispong.MainPong;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Random;

public class GameSound {
    private final Media adventure;
    private final Media mainSong;
    private final Media win;
    private final Media camel;
    private final Media tcp;
    private final ArrayList<Media> playlist;

    private MediaPlayer mediaPlayer;

    private static GameSound instance;

    private GameSound() {
        adventure = new Media(MainPong.class.getResource("musique/adventure.mp3").toString());
        camel = new Media(MainPong.class.getResource("musique/camel.mp3").toString());
        win = new Media(MainPong.class.getResource("musique/win.mp3").toString());
        mainSong = new Media(MainPong.class.getResource("musique/s1.mp3").toString());
        tcp = new Media(MainPong.class.getResource("musique/tcp.mp3").toString());

        playlist = new ArrayList<>();
        playlist.add(mainSong);
        //playlist.add(camel);
        playlist.add(adventure);
    }

    public static GameSound getInstance() {
        if (instance == null) {
            instance = new GameSound();
        }
        return instance;
    }

    public void changeSound() {
        int a = new Random().nextInt(0, playlist.size());
        if (this.mediaPlayer != null) {
            if (mediaPlayer.getMedia() == playlist.get(a)) {
                this.mediaPlayer.stop();
                changeSound();
            } else {
                this.mediaPlayer.stop();
                this.mediaPlayer = new MediaPlayer(this.playlist.get(a));
                this.mediaPlayer.setVolume(0.1);
                this.mediaPlayer.setAutoPlay(true);
            }
        } else {
            this.mediaPlayer = new MediaPlayer(this.playlist.get(a));
            this.mediaPlayer.setVolume(0.1);
            this.mediaPlayer.setAutoPlay(true);
        }
    }

    public void playWin() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(win);
        this.mediaPlayer.setVolume(0.1);
        this.mediaPlayer.setAutoPlay(true);
    }

    public void playTcp() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(tcp);
        this.mediaPlayer.setVolume(0.1);
        this.mediaPlayer.setAutoPlay(true);
    }

    public void stop() {
        mediaPlayer.stop();
    }
}
