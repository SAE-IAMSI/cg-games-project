package games.project.motron;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;


public class TRON extends Application {

    private Timeline timeline;

    private boolean time;

    private KeyFrame keyFrame;

    private int keyFrameMillis = 90;

    private Scene sceneGO;

    private Scene scenejeu;

    private Scene scenemenu;

    private boolean booljeu = true;

    private boolean utile = true;

    private boolean boolgameover = true;
    private Jeu jeu;
    private Moto j1;
    private Moto j2;
    private MediaPlayer startSound;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Menu menu = new Menu();
        scenemenu = new Scene(menu);
        scenemenu.setFill(Color.BLACK);
//        String musicMenu = "src/main/resources/music/GetLucky8bit.mp3";
//        Media soundMenu = new Media(new File(musicMenu).toURI().toString());
//        mediaPlayerMenu = new MediaPlayer(soundMenu);

//        String musicJeu = "src/main/resources/music/HBFS8bit.mp3";
//        Media soundJeu = new Media(new File(musicJeu).toURI().toString());
//        mediaPlayerJeu = new MediaPlayer(soundJeu);
        //menu afficher
        primaryStage.setScene(scenemenu);
        primaryStage.show();
        menu.getMediaPlayerMenu().play();
        time = true;

        //récupere les motos pour les moves ..


        keyFrame = new KeyFrame(Duration.millis(keyFrameMillis), e ->{
            if(!menu.isMenuOn()) {
                if (!primaryStage.getScene().equals(scenejeu) && booljeu) { // si la scène ne contient pas le jeu && le jeu doit être montré
                    booljeu = false;
                    jeu = menu.creerJeu();
                    jeu.jouer();
                    j1 = jeu.getJ1();
                    j2 = jeu.getJ2();
                    scenejeu = new Scene(jeu);
                    scenejeu.setFill(Color.BLACK);
//                    String linkSoundStart = "src/main/resources/music/StartSound.mp3";
//                    Media soundStart = new Media(new File(linkSoundStart).toURI().toString());
//                    startSound = new MediaPlayer(soundStart);
                    //startSound.seek(startSound.getStartTime());
                    menu.getStartSound().play();
                    transition(menu, 1, 0);
                    Thread t = new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            Platform.runLater(() -> {
                                transition(jeu, 0, 1);
                                transition(menu, 0, 1);
                                primaryStage.setScene(scenejeu);
                                primaryStage.show();
                                time = false;
                            });
                        } catch (InterruptedException exc) {
                            exc.printStackTrace();
                        }
                    });
                    t.setDaemon(true); // thread will not stop application exit
                    t.start();
                }

                if (!time) {     // Pour n'exécuter qu'une fois le décompte
                    menu.getMediaPlayerMenu().stop();
                    menu.getMediaPlayerJeu().play();
                    menu.getStartSound().stop();
                    j1.getSprite().getSkinMotoView().setRotate(90);
                    j2.getSprite().getSkinMotoView().setRotate(-90);
                    time = true;
                    jeu.decompte();
                }

                if (Objects.equals(jeu.getTimer().getText(), "")) {
                    jeu.setTouche(scenejeu);
                    j1.move(jeu);
                    j2.move(jeu);
                }
                if ((j1.isGameOver() || j2.isGameOver()) && !jeu.getGameOver().isR() && boolgameover && !jeu.isAnimationMort()) { //si 1 des 2 joueurs meurt && le jeu n'est pas déjà en gameover && il y doit y avoir gameover
                    if(j1.getNbMorts() < 3 && j2.getNbMorts() < 3 ){

                        if(!jeu.isAnimationMort()&&utile){
                            jeu.animationMort(jeu.getMotoMorte());
                            utile = false;
                            jeu.setAnimationMort(true);
                            j1.setStop(true);
                            j2.setStop(true);
                        }



                        if(!jeu.isAnimationMort()){
                            System.out.println("RELANCE");
                            j1.setStop(false);
                            j2.setStop(false);
                            relancer(primaryStage);
                            utile=true;
                            jeu.setAnimationMort(false);
                        }
                    }
                    else{
                        System.out.println("CAS 2");
                        if(!jeu.isAnimationMort()&&utile){
                            jeu.animationMort(jeu.getMotoMorte());
                            utile = false;
                            jeu.setAnimationMort(true);
                            j1.setStop(true);
                            j2.setStop(true);
                        }
                        if(!jeu.isAnimationMort()) {
                            j1.setNbMorts(0);
                            j2.setNbMorts(0);
                            utile=true;
                            boolgameover = false;
                            GridPane go = jeu.getGameOver();
                            sceneGO = new Scene(go);
                            primaryStage.setScene(sceneGO);
                        }
                    }
                }

                if (jeu.getGameOver() != null) {
                    if (jeu.getGameOver().isR()) { // si le bouton relance est pressé
                        j1.setScore(0);
                        j2.setScore(0);
                        jeu.resetScore();
                        relancer(primaryStage);
                    }
                    if (jeu.getGameOver().isRetourmenu()) {
                        primaryStage.setScene(scenemenu);
                        menu.setMenuOn(true);
                        menu.getMediaPlayerJeu().stop();
                        menu.getMediaPlayerMenu().play();

                        booljeu = true;
                        boolgameover = true;
                        jeu.getGameOver().setRetourmenu(false);
                    }
                }
            }

        });

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void relancer(Stage primaryStage){

        j1.setGameOver(false);
        j2.setGameOver(false);
        sceneGO = null;
        jeu.jouer();
        jeu.getGameOver().setR(false);
        jeu.setTouche(scenejeu);
        primaryStage.setScene(scenejeu);
        jeu.decompte();
        boolgameover = true;
    }

    public void transition(Node pane, int from, int to){
        FadeTransition fade = new FadeTransition(Duration.seconds(2), pane);
        fade.setFromValue(from);
        fade.setToValue(to);
        fade.play();
    }
}
