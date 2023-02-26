package games.project.factory_fall.parametres;

import games.project.factory_fall.FactoryFall;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Objects;

/**
 * La classe Musique est une classe dite statique qui permet de gérer la musique du jeu.
 * Elle est composée de deux musiques : une pour le menu (mainMenuMusic) et une pour le jeu (gameMusic).
 * La musique du jeu est jouée en boucle.
 */
public class Musique {
    private static Media mainMenuMusic;
    private static Media gameMusic;
    private static MediaPlayer musicPlayer;

    /**
     * Joue la musique du menu principal.
     */
    public static void playMusicMainMenu() {
        if (!Preferences.getInstance().getMusiqueMute()) {
            mainMenuMusic = new Media(Objects.requireNonNull(String.valueOf(FactoryFall.class.getResource("music/MainMenuMusic.mp3"))));
            musicPlayer = new MediaPlayer(mainMenuMusic);
            musicPlayer.setAutoPlay(true);
            musicPlayer.volumeProperty().setValue(0.1);
            musicPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    musicPlayer.seek(Duration.ZERO);
                }
            });
        }
    }

    /**
     * Arrête la musique du menu principal.
     */
    public static void stopMusicMainMenu() {
        musicPlayer.stop();
    }

    /**
     * Arrête la musique.
     */
    public static void btnMute() {
        musicPlayer.setMute(!musicPlayer.isMute());
        Preferences.getInstance().setMusiqueMute(musicPlayer.isMute());
    }

    /**
     * Joue la musique du jeu.
     */
    public static void playMusicGame() {
        if (!Preferences.getInstance().getMusiqueMute()) {
            gameMusic = new Media(Objects.requireNonNull(String.valueOf(FactoryFall.class.getResource("music/GameMusic.mp3"))));
            musicPlayer = new MediaPlayer(gameMusic);
            musicPlayer.setAutoPlay(true);
            musicPlayer.volumeProperty().setValue(0.1);
            musicPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    musicPlayer.seek(Duration.ZERO);
                }
            });
        }
    }

    /**
     * Arrête la musique du jeu en vérifiant que l'attribut la jouant n'est pas null.
     */
    public static void stopMusicGame() {
        if (musicPlayer != null) musicPlayer.stop();
    }
}
