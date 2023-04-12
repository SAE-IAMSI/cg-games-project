package games.project.motron;

import games.project.modules.parametres.Parametres;
import games.project.motron.controller.ControllerFXML;
import games.project.motron.metier.manager.ScoreManagerMotron;
import games.project.motron.metier.manager.ScorePartieManager;
import games.project.motron.view.VueJeu;
import games.project.motron.view.VueMoto;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class Motron extends Application {

    private Timeline timeline;
    private KeyFrame keyFrame;
    private final int keyFrameMillis = 90;
    private Scene scenemenu;
    private VueJeu jeu;
    private VueMoto j1;
    private VueMoto j2;
    private int numManche = 1;

    public static void main(String[] args) {
        Application.launch(Parametres.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Motron.class.getResource("jeu.fxml"));
        scenemenu = new Scene(fxmlLoader.load());
        ControllerFXML controllerFXML = fxmlLoader.getController();

        primaryStage.setTitle("MOTRON");
        primaryStage.setScene(scenemenu);
        primaryStage.show();
        controllerFXML.getMediaPlayerMenu().play();

        keyFrame = new KeyFrame(Duration.millis(keyFrameMillis), e -> {
            if (controllerFXML.getComptePane().isVisible()) {
                controllerFXML.afficherButtonsStats();
            }

            if (controllerFXML.getJeuPane().isVisible()) {
                if (controllerFXML.getJeuPane().isVisible() && controllerFXML.isNewJeu()) {

                    controllerFXML.setNewJeu(false);
                    jeu = controllerFXML.getJeu();
                    jeu.jouer(controllerFXML.getxJ1(), controllerFXML.getyJ1(), controllerFXML.getxJ2(), controllerFXML.getyJ2());
                    j1 = jeu.getJ1();
                    j2 = jeu.getJ2();

                    controllerFXML.getArenePane().getChildren().add(jeu.getPane());
                    if (j1.getNbMorts() >= 3 || j2.getNbMorts() >= 3) {
                        controllerFXML.getMort1J1().setVisible(false);
                        controllerFXML.getMort2J1().setVisible(false);
                        controllerFXML.getMort3J1().setVisible(false);
                        controllerFXML.getMort1J2().setVisible(false);
                        controllerFXML.getMort2J2().setVisible(false);
                        controllerFXML.getMort3J2().setVisible(false);
                        j1.setNbMorts(0);
                        j2.setNbMorts(0);
                    }
                }

                if (controllerFXML.getJeuPane().isVisible() && !controllerFXML.getPausePane().isVisible() && !controllerFXML.getGameOverPane().isVisible() && !controllerFXML.getDecomptePane().isVisible()) {
                    jeu.setTouche(scenemenu);
                    j1.move(jeu);
                    j2.move(jeu);
                    controllerFXML.getScoreJ1().setText(String.valueOf(j1.getScore().getScore()));
                    controllerFXML.getScoreJ2().setText(String.valueOf(j2.getScore().getScore()));
                }

                if (jeu.isPauseOn() && !controllerFXML.getDecomptePane().isVisible()) {
                    controllerFXML.getPausePane().setVisible(true);
                } else {
                    if (controllerFXML.getPausePane().isVisible()) {
                        controllerFXML.getPausePane().setVisible(false);
                    }
                }

                if ((j1.isGameOver() || j2.isGameOver()) && !controllerFXML.getMenuPane().isVisible()) {

                    if (j1.getNbMorts() == 1) {
                        controllerFXML.getMort1J1().setVisible(true);
                    }
                    if (j1.getNbMorts() == 2) {
                        controllerFXML.getMort2J1().setVisible(true);

                    }
                    if (j1.getNbMorts() == 3) {
                        controllerFXML.getMort3J1().setVisible(true);

                    }
                    if (j2.getNbMorts() == 1) {
                        controllerFXML.getMort1J2().setVisible(true);

                    }
                    if (j2.getNbMorts() == 2) {
                        controllerFXML.getMort2J2().setVisible(true);

                    }
                    if (j2.getNbMorts() == 3) {
                        controllerFXML.getMort3J2().setVisible(true);

                    }
                    if (j1.isGameOver()) {
                        controllerFXML.getGifBoomJ1().setVisible(true);
                        j1.getSprite().getSkinMotoView().setVisible(false);
                        controllerFXML.getGifBoomJ1().setX(j1.getMotoX() + 110);
                        controllerFXML.getGifBoomJ1().setY(j1.getMotoY() + 40);
                    }
                    if (j2.isGameOver()) {
                        controllerFXML.getGifBoomJ2().setVisible(true);
                        j2.getSprite().getSkinMotoView().setVisible(false);
                        controllerFXML.getGifBoomJ2().setX(j2.getMotoX() + 110);
                        controllerFXML.getGifBoomJ2().setY(j2.getMotoY() + 40);
                    }
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> {
                        String rj1 = "";
                        String rj2 = "";
                        jeu.setGameOver(false);
                        controllerFXML.getGifBoomJ1().setVisible(false);
                        controllerFXML.getGifBoomJ2().setVisible(false);
                        j1.getSprite().getSkinMotoView().setVisible(true);
                        j2.getSprite().getSkinMotoView().setVisible(true);
                        if (j1.getNbMorts() < 3 && j2.getNbMorts() < 3) {
                            numManche++;
                            jeu.setGameOver(false);
                            controllerFXML.getGifBoomJ1().setVisible(false);
                            controllerFXML.getGifBoomJ2().setVisible(false);
                            j1.getSprite().getSkinMotoView().setVisible(true);
                            j2.getSprite().getSkinMotoView().setVisible(true);
                            controllerFXML.getRound().setText("ROUND " + numManche);
                            controllerFXML.relanceManche();
                        } else {
                            if (j1.getNbMorts() < 3) {
                                j1.getScore().incrementScore(10000);
                            }
                            if (j2.getNbMorts() < 3) {
                                j2.getScore().incrementScore(10000);
                            }
                            controllerFXML.getGameOverPane().setVisible(true);
                            if (j1.getScore().getScore() == j2.getScore().getScore()) {
                                rj1 = "E";
                                rj2 = "E";
                                controllerFXML.getLabelFin().setText("Égalité");
                            } else if (j1.getScore().getScore() < j2.getScore().getScore()) {
                                if (j2.getNbMorts() == 0) {
                                    j2.getScore().incrementScore(20000);
                                }
                                rj2 = "V";
                                rj1 = "D";
                                controllerFXML.getLabelFin().setText((j2.getNomJoueur() + " a gagné !"));
                            } else {
                                if (j1.getNbMorts() == 0) {
                                    j1.getScore().incrementScore(20000);
                                }
                                rj2 = "D";
                                rj1 = "V";
                                controllerFXML.getLabelFin().setText((j1.getNomJoueur() + " a gagné !"));
                            }
                            numManche = 1;
                            if (j1.isConnecter()) {
                                ScoreManagerMotron.getInstance().createScore(j1.getScore().getScore(), j1.getNomJoueur(), "TRON");
                                int codeScore = ScoreManagerMotron.getInstance().getMaxId();
                                ScorePartieManager.getInstance().createScorePartie(codeScore, j2.getNbMorts(), j1.getNbMorts(), j1.getNbBlocParcouru(), rj1);
                                j1.setNbBlocParcouru(0);
                            }
                            if (j2.isConnecter()) {
                                ScoreManagerMotron.getInstance().createScore(j2.getScore().getScore(), j2.getNomJoueur(), "TRON");
                                int codeScore = ScoreManagerMotron.getInstance().getMaxId();
                                ScorePartieManager.getInstance().createScorePartie(codeScore, j1.getNbMorts(), j2.getNbMorts(), j2.getNbBlocParcouru(), rj2);
                                j2.setNbBlocParcouru(0);
                            }
                            controllerFXML.getScoreGOJ1().setText(String.valueOf(j1.getScore().getScore()));
                            controllerFXML.getScoreGOJ2().setText(String.valueOf(j2.getScore().getScore()));
                        }
                    });
                    pause.play();
                    j1.setGameOver(false);
                    j2.setGameOver(false);
                }
            }
        });
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
