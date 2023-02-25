package games.project.koala_rock.Sound;

import games.project.koala_rock.RessourcesAccess;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.util.Objects;

public class Son {
    private static Media gameMusic;
    private static Media jumpMusic;
    private static Media pointMusic;
    private static MediaPlayer mediaPlayer;

    public static void playMusic() {
        try {
            gameMusic = new Media(Objects.requireNonNull(String.valueOf(RessourcesAccess.class.getResource("son/fond.wav"))));
            mediaPlayer = new MediaPlayer(gameMusic);
            if (gameMusic != null) {
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.1);
                mediaPlayer.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void jump() {
        try {
            jumpMusic = new Media(Objects.requireNonNull(String.valueOf(RessourcesAccess.class.getResource("son/jump.wav"))));
            mediaPlayer = new MediaPlayer(jumpMusic);
            if (jumpMusic != null) {
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setCycleCount(1);
                mediaPlayer.setVolume(0.1);
                mediaPlayer.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void point() {
        try {
            pointMusic = new Media(Objects.requireNonNull(String.valueOf(RessourcesAccess.class.getResource("son/point.wav"))));
            mediaPlayer = new MediaPlayer(pointMusic);
            if (pointMusic != null) {
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setCycleCount(1);
                mediaPlayer.setVolume(0.1);
                mediaPlayer.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
