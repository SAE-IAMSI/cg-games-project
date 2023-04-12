package games.project.motron.controller;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Player;
import games.project.metier.manager.PlayerManager;
import games.project.motron.Motron;
import games.project.motron.metier.entite.Stat;
import games.project.motron.metier.manager.PlayerManagerMotron;
import games.project.motron.metier.manager.ScoreManagerMotron;
import games.project.motron.metier.manager.ScorePartieManager;
import games.project.motron.stockage.Security;
import games.project.motron.view.*;
import games.project.stockage.Session;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static javafx.scene.input.KeyCode.*;

public class ControllerFXML implements Initializable {

    private VueArene vueArene;
    private final VueMatriceArene matriceArene = new VueMatriceArene();
    //    private boolean menuOn = true;
    private final VueMoto j1;
    private final VueMoto j2;
    private final ArrayList<KeyCode> keyList = new ArrayList<>();
    private final MediaPlayer startSound;
    private final MediaPlayer mediaPlayerMenu;
    private final MediaPlayer mediaPlayerJeu;
    private final MediaPlayer notificationSound;
    private final MediaPlayer positiveNotificationSound;
    private final Player player1;
    private final Player player2;
    private final boolean relance = false;
    private VueJeu jeu;
    private boolean newJeu = true;
    private int numSprite = 0;

    @FXML
    private ImageView background;
    @FXML
    private ImageView carteBackground;
    @FXML
    private ImageView jeuBackground;
    @FXML
    private Pane menuPane;
    @FXML
    private Pane choixCartePane;
    @FXML
    private Pane jeuPane;
    @FXML
    private Pane arenePane;
    @FXML
    private Pane pausePane;
    @FXML
    private Pane classementPane;
    @FXML
    private Pane persoPane;
    @FXML
    private Pane paramPane;
    @FXML
    private Pane comptePane;
    @FXML
    private Pane connexionJ1Pane;
    @FXML
    private Pane connexionJ2Pane;
    @FXML
    private Pane creationComptePane;
    @FXML
    private Pane gameOverPane;
    @FXML
    private Label scoreGOJ1;
    @FXML
    private Label scoreGOJ2;
    @FXML
    private Label notificationLabel;
    @FXML
    private Pane decomptePane;

    //    Menu

    @FXML
    private Label idTotalScoreJ1;
    @FXML
    private Label idNbCasesJ1;
    @FXML
    private Label idKillJ1;
    @FXML
    private Label idMortJ1;
    @FXML
    private Label idRatioJ1;
    @FXML
    private Label idVictoireJ1;
    @FXML
    private Label idDefaiteJ1;
    @FXML
    private Label idEgaliteJ1;
    @FXML
    private Label idTotalScoreJ2;
    @FXML
    private Label idNbCasesJ2;
    @FXML
    private Label idKillJ2;
    @FXML
    private Label idMortJ2;
    @FXML
    private Label idRatioJ2;
    @FXML
    private Label idVictoireJ2;
    @FXML
    private Label idDefaiteJ2;
    @FXML
    private Label idEgaliteJ2;
    @FXML
    private Label idRGPD;

    @FXML
    protected void lanceJouer() {
        menuPane.setVisible(false);
        background.setVisible(false);
        carteBackground.setVisible(true);
        choixCartePane.setVisible(true);
        choixCartePane.setDisable(false);
    }

    @FXML
    protected void lanceClassement() {
        menuPane.setVisible(false);
        classementPane.setVisible(true);
        creerClassement();
    }

    @FXML
    protected void lancePerso() {
        menuPane.setVisible(false);
        persoPane.setVisible(true);
        imageMotoJ1.setImage(j1.getSprite().getSkinMoto());
        imageMotoJ2.setImage(j2.getSprite().getSkinMoto());
    }

    @FXML
    protected void lanceParam() {
        menuPane.setVisible(false);
        paramPane.setVisible(true);
    }

    @FXML
    Button bouttonStatsJ1;
    @FXML
    Button bouttonStatsJ2;

    @FXML
    protected void lanceCompte() {
        menuPane.setVisible(false);
        comptePane.setVisible(true);
    }

    @FXML
    protected void lanceQuitter() {
        Platform.exit();
    }
//    ------------------------------------------------------------------------------------------------------------------

    //    Sélection de cartes (alias: choixCartePane)
    @FXML
    protected void retourCarte() {
        choixCartePane.setVisible(false);
        carteBackground.setVisible(false);
        background.setVisible(true);
        menuPane.setVisible(true);
    }

    private int xJ1;
    private int yJ1;
    private int xJ2;
    private int yJ2;

    @FXML
    protected void selectCarte1() {
        choixCartePane.setDisable(true);
        transitionJeu(carteBackground, choixCartePane, 1, 0);
        startSound.seek(Duration.millis(0));
        startSound.play();
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(() -> {
                    choixCartePane.setVisible(false);
                    carteBackground.setVisible(false);
                    transitionJeu(jeuBackground, jeuPane, 0, 1);
                    transitionJeu(carteBackground, choixCartePane, 0, 1);
                    jeuBackground.setVisible(true);
                    jeuPane.setVisible(true);
                    mediaPlayerMenu.stop();
                    mediaPlayerJeu.play();
                    xJ1 = 9 * 20;
                    yJ1 = 16 * 20;
                    xJ2 = 40 * 20;
                    yJ2 = 16 * 20;
                    vueArene = new VueArene(matriceArene.getArene1());
                    jeu = creerJeu();
                    decompte();
                });
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        });
        t.setDaemon(true); // thread will not stop application exit
        t.start();
    }

    @FXML
    protected void selectCarte2() {
        choixCartePane.setDisable(true);
        transitionJeu(carteBackground, choixCartePane, 1, 0);
        startSound.seek(Duration.millis(0));
        startSound.play();
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(() -> {
                    choixCartePane.setVisible(false);
                    carteBackground.setVisible(false);
                    transitionJeu(jeuBackground, jeuPane, 0, 1);
                    transitionJeu(carteBackground, choixCartePane, 0, 1);
                    jeuBackground.setVisible(true);
                    jeuPane.setVisible(true);
                    mediaPlayerMenu.stop();
                    mediaPlayerJeu.play();
                    xJ1 = 15 * 20;
                    yJ1 = 16 * 20;
                    xJ2 = 34 * 20;
                    yJ2 = 16 * 20;
                    vueArene = new VueArene(matriceArene.getArene2());
                    jeu = creerJeu();
                    decompte();
                });
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        });
        t.setDaemon(true); // thread will not stop application exit
        t.start();
    }

    @FXML
    protected void selectCarte3() {
        choixCartePane.setDisable(true);
        transitionJeu(carteBackground, choixCartePane, 1, 0);
        startSound.seek(Duration.millis(0));
        startSound.play();
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(() -> {
                    choixCartePane.setVisible(false);
                    carteBackground.setVisible(false);
                    transitionJeu(jeuBackground, jeuPane, 0, 1);
                    transitionJeu(carteBackground, choixCartePane, 0, 1);
                    jeuBackground.setVisible(true);
                    jeuPane.setVisible(true);
                    mediaPlayerMenu.stop();
                    mediaPlayerJeu.play();
                    xJ1 = 11 * 20;
                    yJ1 = 16 * 20;
                    xJ2 = 38 * 20;
                    yJ2 = 16 * 20;
                    vueArene = new VueArene(matriceArene.getArene3());
                    jeu = creerJeu();
                    decompte();
                });
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        });
        t.setDaemon(true); // thread will not stop application exit
        t.start();
    }

    @FXML
    protected void selectCarte4() {
        choixCartePane.setDisable(true);
        transitionJeu(carteBackground, choixCartePane, 1, 0);
        startSound.seek(Duration.millis(0));
        startSound.play();
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(() -> {
                    choixCartePane.setVisible(false);
                    carteBackground.setVisible(false);
                    transitionJeu(jeuBackground, jeuPane, 0, 1);
                    transitionJeu(carteBackground, choixCartePane, 0, 1);
                    jeuBackground.setVisible(true);
                    jeuPane.setVisible(true);
                    mediaPlayerMenu.stop();
                    mediaPlayerJeu.play();
                    xJ1 = 5 * 20;
                    yJ1 = 16 * 20;
                    xJ2 = 44 * 20;
                    yJ2 = 16 * 20;
                    vueArene = new VueArene(matriceArene.getArene4());
                    jeu = creerJeu();
                    decompte();
                });
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    @FXML
    protected void selectCarte5() {
        choixCartePane.setDisable(true);
        transitionJeu(carteBackground, choixCartePane, 1, 0);
        startSound.seek(Duration.millis(0));
        startSound.play();
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(() -> {
                    choixCartePane.setVisible(false);
                    carteBackground.setVisible(false);
                    transitionJeu(jeuBackground, jeuPane, 0, 1);
                    transitionJeu(carteBackground, choixCartePane, 0, 1);
                    jeuBackground.setVisible(true);
                    jeuPane.setVisible(true);
                    mediaPlayerMenu.stop();
                    mediaPlayerJeu.play();
                    xJ1 = 11 * 20;
                    yJ1 = 16 * 20;
                    xJ2 = 38 * 20;
                    yJ2 = 16 * 20;
                    vueArene = new VueArene(matriceArene.getArene5());
                    jeu = creerJeu();
                    decompte();
                });
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        });
        t.setDaemon(true); // thread will not stop application exit
        t.start();
    }

    public void transitionJeu(Node background, Node pane, int from, int to) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), pane);
        FadeTransition fade2 = new FadeTransition(Duration.seconds(2), background);
        fade.setFromValue(from);
        fade.setToValue(to);
        fade2.setFromValue(from);
        fade2.setToValue(to);
        fade.play();
        fade2.play();
    }
//    ------------------------------------------------------------------------------------------------------------------

    //    Jeu
    @FXML
    private Label round;
    @FXML
    private Label labelJ1;
    @FXML
    private Label scoreJ1;
    @FXML
    private ImageView vie1J1;
    @FXML
    private ImageView vie2J1;
    @FXML
    private ImageView vie3J1;
    @FXML
    private Label labelJ2;
    @FXML
    private Label scoreJ2;
    @FXML
    private ImageView vie1J2;
    @FXML
    private ImageView vie2J2;
    @FXML
    private ImageView vie3J2;
    @FXML
    private ImageView mort1J1;
    @FXML
    private ImageView mort2J1;
    @FXML
    private ImageView mort3J1;
    @FXML
    private ImageView mort1J2;
    @FXML
    private ImageView mort2J2;
    @FXML
    private ImageView mort3J2;
    @FXML
    private Pane RGPDPane;
    @FXML
    private Label decompteNumber;

    public void decompte() {
        decomptePane.setVisible(true);
        String[] s = {"3", "2", "1", "Go!"};
        Thread t = new Thread(() -> {
            try {
                for (int i = 0; i < 4; i++) {
                    String text = s[i];
                    Platform.runLater(() -> decompteNumber.setText(text));
                    if (i != 3) {
                        Thread.sleep(500);
                    }
                    Thread.sleep(500);
                }
                Platform.runLater(() -> decompteNumber.setText(""));
                decomptePane.setVisible(false);
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
    }
//    ------------------------------------------------------------------------------------------------------------------

    //    Pause
    @FXML
    protected void retourPause() {
        pausePane.setVisible(false);
        jeu.setPauseOn(false);
    }
//    ------------------------------------------------------------------------------------------------------------------

    //    GameOver
    @FXML
    protected void menuPrincipal() {
        scoreJ1.setText("0");
        scoreJ2.setText("0");
        round.setText("ROUND 1");
        jeuBackground.setVisible(false);
        gameOverPane.setVisible(false);
        menuPane.setVisible(true);
        background.setVisible(true);
        jeuPane.setVisible(false);
        mediaPlayerJeu.stop();
        mediaPlayerMenu.play();
        newJeu = true;
        j1.setScore(0);
        j2.setScore(0);
    }

    @FXML
    private ImageView gifBoomJ1;
    @FXML
    private ImageView gifBoomJ2;

    @FXML
    protected void relanceGO() {
        gameOverPane.setVisible(false);
        newJeu = true;
        j1.setScore(0);
        j2.setScore(0);
        scoreJ1.setText("0");
        scoreJ2.setText("0");
        round.setText("ROUND 1");
        decompte();
    }

    public void relanceManche() {
        newJeu = true;
        decompte();
    }

    @FXML
    private Label labelFin;
//    ------------------------------------------------------------------------------------------------------------------

    //    Classement
    @FXML
    private Label textTempL1;
    @FXML
    private Label textTempL2;
    @FXML
    private Label textTempL3;
    @FXML
    private Label textTempL4;
    @FXML
    private Label textTempL5;
    @FXML
    private Label textTempL6;
    @FXML
    private Label textTempL7;
    @FXML
    private Label textTempL8;
    @FXML
    private Label textTempL9;
    @FXML
    private Label textTempL10;
    @FXML
    private Label textTempS1;
    @FXML
    private Label textTempS2;
    @FXML
    private Label textTempS3;
    @FXML
    private Label textTempS4;
    @FXML
    private Label textTempS5;
    @FXML
    private Label textTempS6;
    @FXML
    private Label textTempS7;
    @FXML
    private Label textTempS8;
    @FXML
    private Label textTempS9;
    @FXML
    private Label textTempS10;
    @FXML
    private Label textTempD1;
    @FXML
    private Label textTempD2;
    @FXML
    private Label textTempD3;
    @FXML
    private Label textTempD4;
    @FXML
    private Label textTempD5;
    @FXML
    private Label textTempD6;
    @FXML
    private Label textTempD7;
    @FXML
    private Label textTempD8;
    @FXML
    private Label textTempD9;
    @FXML
    private Label textTempD10;

    @FXML
    protected void retourClassement() {
        classementPane.setVisible(false);
        menuPane.setVisible(true);
    }

    public void creerClassement() {
        List<Player> meilleurs = PlayerManagerMotron.getInstance().getMeilleurs();
        ArrayList<Label> listGridJoueur = new ArrayList<>();
        listGridJoueur.add(textTempL1);
        listGridJoueur.add(textTempL2);
        listGridJoueur.add(textTempL3);
        listGridJoueur.add(textTempL4);
        listGridJoueur.add(textTempL5);
        listGridJoueur.add(textTempL6);
        listGridJoueur.add(textTempL7);
        listGridJoueur.add(textTempL8);
        listGridJoueur.add(textTempL9);
        listGridJoueur.add(textTempL10);
        ArrayList<Label> listGridScore = new ArrayList<>();
        listGridScore.add(textTempS1);
        listGridScore.add(textTempS2);
        listGridScore.add(textTempS3);
        listGridScore.add(textTempS4);
        listGridScore.add(textTempS5);
        listGridScore.add(textTempS6);
        listGridScore.add(textTempS7);
        listGridScore.add(textTempS8);
        listGridScore.add(textTempS9);
        listGridScore.add(textTempS10);
        ArrayList<Label> listGridDep = new ArrayList<>();
        listGridDep.add(textTempD1);
        listGridDep.add(textTempD2);
        listGridDep.add(textTempD3);
        listGridDep.add(textTempD4);
        listGridDep.add(textTempD5);
        listGridDep.add(textTempD6);
        listGridDep.add(textTempD7);
        listGridDep.add(textTempD8);
        listGridDep.add(textTempD9);
        listGridDep.add(textTempD10);
        for (int i = 0; i < meilleurs.size(); i++) {
            modifierLabelGrid(listGridJoueur.get(i), listGridScore.get(i), listGridDep.get(i), i, meilleurs);
        }
    }

    public void modifierLabelGrid(Label labelj, Label labels, Label labeld, int i, List<Player> meilleurs) {
        String s = meilleurs.get(i).getName();
        labelj.setText(s);
        labels.setText(String.valueOf(meilleurs.get(i).getScore().getScore()));
        labeld.setText(PlayerManagerMotron.getInstance().getDepartementByLogin(s));
    }


//    ------------------------------------------------------------------------------------------------------------------

    //    Personnaliser (alias: perso)
    @FXML
    protected void retourPerso() {
        persoPane.setVisible(false);
        menuPane.setVisible(true);
        if (imageMotoJ1.getOpacity() != 1) {
            imageMotoJ1.setImage(j1.getSprite().getSkinMotoView().getImage());
            imageMotoJ1.setOpacity(1);
            conditionSkinJ1.setVisible(false);
            nomSkinJ1.setText(j1.getSprite().getNom());
        }
        if (imageMotoJ2.getOpacity() != 1) {
            imageMotoJ2.setImage(j2.getSprite().getSkinMotoView().getImage());
            imageMotoJ2.setOpacity(1);
            conditionSkinJ2.setVisible(false);
            nomSkinJ2.setText(j2.getSprite().getNom());
        }
    }

    @FXML
    private Label conditionSkinJ1;
    @FXML
    private Label conditionSkinJ2;
    @FXML
    private ImageView imageMotoJ1;
    @FXML
    private ImageView imageMotoJ2;
    @FXML
    private Label nomSkinJ1;
    @FXML
    private Label nomSkinJ2;

    @FXML
    protected void changeSkinGaucheJ1() {
        changeSkinGauche(j1);
    }

    @FXML
    protected void changeSkinGaucheJ2() {
        changeSkinGauche(j2);
    }

    @FXML
    protected void changeSkinDroiteJ1() {
        changeSkinDroite(j1);
    }

    @FXML
    protected void changeSkinDroiteJ2() {
        changeSkinDroite(j2);
    }

    protected void changeSkinGauche(VueMoto j) {
        ImageView imageMoto;
        Label conditionSkin;
        Label nomSkin;
        if (j == j1) {
            imageMoto = imageMotoJ1;
            conditionSkin = conditionSkinJ1;
            nomSkin = nomSkinJ1;
        } else {
            imageMoto = imageMotoJ2;
            conditionSkin = conditionSkinJ2;
            nomSkin = nomSkinJ2;
        }
        LinkedHashMap<VueSprite, Color> sprites = VueSprite.genererSprites();
        HashMap<VueSprite, Integer> skinsBloques = new HashMap<>();
        if (j.isConnecter()) {
            skinsBloques = recompenseMoto(j, sprites);
        }
        ArrayList<VueSprite> listSprites = new ArrayList<>(sprites.keySet());
        VueSprite sprite;
        if (numSprite == 0) {
            numSprite = listSprites.size() - 1;
            sprite = listSprites.get(numSprite);
        } else {
            numSprite--;
            sprite = listSprites.get(numSprite);
        }
        imageMoto.setImage(sprite.getSkinMotoView().getImage());
        imageMoto.setOpacity(sprite.getSkinMotoView().getOpacity());
        if (imageMoto.getOpacity() != 1) {
            for (VueSprite s : skinsBloques.keySet()) {
                if (s.getNom().equals(sprite.getNom())) {
                    conditionSkin.setText("Jouer encore " + (skinsBloques.get(s) - PlayerManagerMotron.getInstance().getNbPartie(j.getNomJoueur())) + " fois");
                    conditionSkin.setVisible(true);
                    nomSkin.setText(sprite.getNom());
                }
            }
        } else {
            conditionSkin.setVisible(false);
            j.getSprite().setSkinMoto(sprite.getSkinMoto(), sprites.get(sprite));
            j.getSprite().setNom(sprite.getNom());
            nomSkin.setText(j.getSprite().getNom());
        }
    }

    protected void changeSkinDroite(VueMoto j) {
        ImageView imageMoto;
        Label conditionSkin;
        Label nomSkin;
        if (j == j1) {
            imageMoto = imageMotoJ1;
            conditionSkin = conditionSkinJ1;
            nomSkin = nomSkinJ1;
        } else {
            imageMoto = imageMotoJ2;
            conditionSkin = conditionSkinJ2;
            nomSkin = nomSkinJ2;
        }
        LinkedHashMap<VueSprite, Color> sprites = VueSprite.genererSprites();
        HashMap<VueSprite, Integer> skinsBloques = new HashMap<>();
        if (j.isConnecter()) {
            skinsBloques = recompenseMoto(j, sprites);
        }
        ArrayList<VueSprite> listSprites = new ArrayList<>(sprites.keySet());
        VueSprite sprite;
        if (numSprite == listSprites.size() - 1) {
            numSprite = 0;
            sprite = listSprites.get(numSprite);
        } else {
            numSprite++;
            sprite = listSprites.get(numSprite);
        }
        imageMoto.setImage(sprite.getSkinMotoView().getImage());
        imageMoto.setOpacity(sprite.getSkinMotoView().getOpacity());
        if (imageMoto.getOpacity() != 1) {
            for (VueSprite s : skinsBloques.keySet()) {
                if (s.getNom().equals(sprite.getNom())) {
                    conditionSkin.setText("Jouer encore " + (skinsBloques.get(s) - PlayerManagerMotron.getInstance().getNbPartie(j.getNomJoueur())) + " fois");
                    conditionSkin.setVisible(true);
                    nomSkin.setText(sprite.getNom());
                }
            }
        } else {
            conditionSkin.setVisible(false);
            j.getSprite().setSkinMoto(sprite.getSkinMoto(), sprites.get(sprite));
            j.getSprite().setNom(sprite.getNom());
            nomSkin.setText(j.getSprite().getNom());
        }
    }

    public HashMap<VueSprite, Integer> recompenseMoto(VueMoto j, LinkedHashMap<VueSprite, Color> recompenses) {
        HashMap<VueSprite, Integer> skinsBloques = new LinkedHashMap<>();
        VueSprite sprite10 = new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/MotoJet.png")), 125, 125, true, true), Color.rgb(236, 86, 0), "Moto-Jet");
        recompenses.put(sprite10, sprite10.getTrailColor());
        VueSprite sprite20 = new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/MotoReacteur.png")), 125, 125, true, true), Color.rgb(22, 255, 237), "Moto à réacteur");
        recompenses.put(sprite20, sprite20.getTrailColor());
        VueSprite sprite50 = new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/SkinPremium1.png")), 125, 125, true, true), Color.rgb(34, 205, 0), "Voiture Premium");
        recompenses.put(sprite50, sprite50.getTrailColor());
        if (PlayerManagerMotron.getInstance().getNbPartie(j.getNomJoueur()) < 50) {
            sprite50.getSkinMotoView().setOpacity(0.25);
            skinsBloques.put(sprite50, 50);
            if (PlayerManagerMotron.getInstance().getNbPartie(j.getNomJoueur()) < 20) {
                sprite20.getSkinMotoView().setOpacity(0.25);
                skinsBloques.put(sprite20, 20);
                if (j.isConnecter() && PlayerManagerMotron.getInstance().getNbPartie(j.getNomJoueur()) < 10) {
                    sprite10.getSkinMotoView().setOpacity(0.25);
                    skinsBloques.put(sprite10, 10);
                }
            }
        }
        return skinsBloques;
    }

    @FXML
    private ImageView imgMap;

    @FXML
    protected void changeMapBlanc() {
        VueMur.setCouleurMurs(Color.ALICEBLUE);
        imgMap.setImage(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("cartesParCoul/aliceblue.png")), 200, 200, true, true));
    }

    @FXML
    protected void changeMapBleu() {
        VueMur.setCouleurMurs(Color.DARKBLUE);
        imgMap.setImage(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("cartesParCoul/bleu.png")), 200, 200, true, true));
    }

    @FXML
    protected void changeMapJaune() {
        VueMur.setCouleurMurs(Color.YELLOW);
        imgMap.setImage(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("cartesParCoul/jaune.png")), 200, 200, true, true));
    }

    @FXML
    protected void changeMapOrange() {
        VueMur.setCouleurMurs(Color.CHOCOLATE);
        imgMap.setImage(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("cartesParCoul/orange.png")), 200, 200, true, true));
    }
//    ------------------------------------------------------------------------------------------------------------------

    //    Paramètres (alias: param)
    @FXML
    protected void retourParam() {
        paramPane.setVisible(false);
        menuPane.setVisible(true);
    }

    @FXML
    private Button boutonHautJ1;
    @FXML
    private Button boutonGaucheJ1;
    @FXML
    private Button boutonBasJ1;
    @FXML
    private Button boutonDroiteJ1;
    @FXML
    private Label upJ1Game;
    @FXML
    private Label downJ1Game;
    @FXML
    private Label leftJ1Game;
    @FXML
    private Label rightJ1Game;

    @FXML
    protected void changeHautJ1() {
        changeTouche(boutonHautJ1, 0, upJ1Game);
    }

    @FXML
    protected void changeGaucheJ1() {
        changeTouche(boutonGaucheJ1, 1, leftJ1Game);
    }

    @FXML
    protected void changeBasJ1() {
        changeTouche(boutonBasJ1, 2, downJ1Game);
    }

    @FXML
    protected void changeDroiteJ1() {
        changeTouche(boutonDroiteJ1, 3, rightJ1Game);
    }

    @FXML
    private Button boutonHautJ2;
    @FXML
    private Button boutonGaucheJ2;
    @FXML
    private Button boutonBasJ2;
    @FXML
    private Button boutonDroiteJ2;
    @FXML
    private Label upJ2Game;
    @FXML
    private Label downJ2Game;
    @FXML
    private Label leftJ2Game;
    @FXML
    private Label rightJ2Game;

    @FXML
    protected void changeHautJ2() {
        changeTouche(boutonHautJ2, 4, upJ2Game);
    }

    @FXML
    protected void changeGaucheJ2() {
        changeTouche(boutonGaucheJ2, 5, leftJ2Game);
    }

    @FXML
    protected void changeBasJ2() {
        changeTouche(boutonBasJ2, 6, downJ2Game);
    }

    @FXML
    protected void changeDroiteJ2() {
        changeTouche(boutonDroiteJ2, 7, rightJ2Game);
    }

    public void changeTouche(Button bouton, int numTouche, Label toucheJeu) {
        EventHandler<KeyEvent> keyEvent = keyEvent1 -> {
            if (!keyList.contains(keyEvent1.getCode())) {
                bouton.setText(keyEvent1.getCode().getName());
                toucheJeu.setText(keyEvent1.getCode().getName());
                keyList.set(numTouche, keyEvent1.getCode());
                afficherNotification("success", "La touche " + bouton.getId() + " est maintenant défini sur " + keyEvent1.getCode());
            } else {
                afficherNotification("warning", "La touche " + keyEvent1.getCode() + " a déjà été assignée");
            }
        };
        paramPane.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent);
        paramPane.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent2 -> {
            paramPane.removeEventFilter(KeyEvent.KEY_PRESSED, keyEvent);
        });
    }

    @FXML
    private Slider sliderMusique;
    @FXML
    private Slider sliderBruitage;

    @FXML
    protected void changeVolumeMusique() {
        sliderMusique.valueProperty().addListener((observableValue, number, t1) -> {
            mediaPlayerMenu.setVolume(sliderMusique.getValue() / 500);
            mediaPlayerJeu.setVolume(sliderMusique.getValue() / 500);
        });
    }

    @FXML
    protected void changeVolumeBruitage() {
        sliderBruitage.valueProperty().addListener((observableValue, number, t1) -> {
            startSound.setVolume(sliderBruitage.getValue() / 500);
            j1.getDeathSound().setVolume(sliderBruitage.getValue() / 500);
            j2.getDeathSound().setVolume(sliderBruitage.getValue() / 500);
        });
    }
//    ------------------------------------------------------------------------------------------------------------------

    //    Compte
    @FXML
    private Label labelJ1Connecte;

    @FXML
    private Button bouttonConnecterJ1;

    @FXML
    private Button bouttonDeconnexionJ1;

    @FXML
    private Button bouttonConnecterJ2;

    @FXML
    private Button bouttonDeconnexionJ2;

    @FXML
    private Label labelJ2Connecte;


    @FXML
    protected void retourCompte() {
        comptePane.setVisible(false);
        menuPane.setVisible(true);
    }

    @FXML
    protected void connecterJ1() {
        comptePane.setVisible(false);
        connexionJ1Pane.setVisible(true);
    }

    @FXML
    protected void connecterJ2() {
        comptePane.setVisible(false);
        connexionJ2Pane.setVisible(true);
    }

    @FXML
    protected void creerCompte() {
        comptePane.setVisible(false);
        creationComptePane.setVisible(true);
    }

    @FXML


    public boolean connexionJoueur(Player player, String login, String password) {
        boolean jConnecte = false;
        AuthPlayer j = PlayerManagerMotron.getInstance().getPlayer(login);
        if (j != null)
            try {
                if (Objects.equals(j.getLogin(), login) && Security.checkPassword(password, j.getSalt(), j.getHashedPassword())) {
                    player.setName(login);
                    jConnecte = true;
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        return jConnecte;
    }

    //    Connexion J1
    @FXML
    private TextField textFieldConnexionJ1;

    @FXML
    private PasswordField passwordFieldConnexionJ1;

    public void resetConnexionJ1Pane() {
        textFieldConnexionJ1.clear();
        passwordFieldConnexionJ1.clear();
    }

    @FXML
    protected void EnterPressedJ1(KeyEvent event) {
        if (event.getCode() == ENTER) {
            connexionJ1();
        }
    }

    @FXML
    protected void connexionJ1() {
        if (!j1.isConnecter()) {
            if (connexionJoueur(player1, textFieldConnexionJ1.getText(), passwordFieldConnexionJ1.getText())) {
                if (player2.getName().equals(textFieldConnexionJ1.getText())) {
                    afficherNotification("warning", "Le joueur 1: " + player1.getName() + " est déjà connecté");
                } else {
                    afficherNotification("success", "Le joueur 1: " + player1.getName() + " s'est connecté");
                    labelJ1Connecte.setText(player1.getName());
                    labelJ1.setText(player1.getName());
                    bouttonConnecterJ1.setVisible(false);
                    bouttonDeconnexionJ1.setVisible(true);
                    connexionJ1Pane.setVisible(false);
                    comptePane.setVisible(true);
                    j1.setNomJoueur(player1.getName());
                    j1.setConnecter(true);
                    Session.getInstance().connect(player1.getName());
                    resetConnexionJ1Pane();
                }
            } else {
                afficherNotification("warning", "Identifiant ou mot de passe incorrect");

            }
        } else {
            afficherNotification("warning", "Le joueur 1: " + player1.getName() + " est deja connecte");
        }
    }

    @FXML
    protected void deconnexionJ1() {
        if (j1.isConnecter()) {
            player1.setName("J1");
            j1.setNomJoueur("Joueur 1");
            j1.setConnecter(false);
            Session.getInstance().disconnect();
            VueSprite spriteJ1 = new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/TronLegacyBleu.png")), 60, 40, true, true), Color.LIGHTBLUE, "Motron Bleu");
            numSprite = 0;
            j1.setSprite(spriteJ1);
            imageMotoJ1.setImage(spriteJ1.getSkinMoto());
            bouttonDeconnexionJ1.setVisible(false);
            bouttonConnecterJ1.setVisible(true);
            labelJ1Connecte.setText("Connexion J1...");
            labelJ1.setText("Joueur 1");
            afficherNotification("success", "Le joueur 1: " + player1.getName() + " a bien été déconnecté");
        } else {
            afficherNotification("warning", "Le joueur n'est pas connecté");
        }

    }

    @FXML
    protected void retourConnexionJ1() {
        connexionJ1Pane.setVisible(false);
        comptePane.setVisible(true);
        resetConnexionJ1Pane();
    }

    //    Connexion J2
    @FXML
    private TextField textFieldConnexionJ2;

    @FXML
    private PasswordField passwordFieldConnexionJ2;

    public void resetConnexionJ2Pane() {
        textFieldConnexionJ2.clear();
        passwordFieldConnexionJ2.clear();
    }

    @FXML
    protected void EnterPressedJ2(KeyEvent event) {
        if (event.getCode() == ENTER) {
            connexionJ2();
        }
    }

    @FXML
    protected void connexionJ2() {
        if (!j2.isConnecter()) {
            if (connexionJoueur(player2, textFieldConnexionJ2.getText(), passwordFieldConnexionJ2.getText())) {
                if (player1.getName().equals(textFieldConnexionJ2.getText())) {
                    afficherNotification("warning", "Le joueur 2: " + player2.getName() + " est déjà connecté");
                } else {
                    afficherNotification("success", "Le joueur 2: " + player2.getName() + " s'est connecté");
                    labelJ2Connecte.setText(player2.getName());
                    labelJ2.setText(player2.getName());
                    bouttonConnecterJ2.setVisible(false);
                    bouttonDeconnexionJ2.setVisible(true);
                    connexionJ2Pane.setVisible(false);
                    comptePane.setVisible(true);
                    j2.setNomJoueur(player2.getName());
                    j2.setConnecter(true);
                    resetConnexionJ2Pane();
                }
            } else {
                afficherNotification("warning", "Identifiant ou mot de passe incorrect");
            }
        } else {
            afficherNotification("warning", "Le joueur 2: " + player2.getName() + " est deja connecte");
        }
    }

    @FXML
    protected void deconnexionJ2() {
        if (j2.isConnecter()) {
            player2.setName("J2");
            j2.setNomJoueur("Joueur 2");
            j2.setConnecter(false);
            VueSprite spriteJ2 = new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/TronLegacyJaune.png")), 60, 40, true, true), Color.YELLOW, "Motron Jaune");
            numSprite = 1;
            j2.setSprite(spriteJ2);
            imageMotoJ2.setImage(spriteJ2.getSkinMoto());
            bouttonDeconnexionJ2.setVisible(false);
            bouttonConnecterJ2.setVisible(true);
            labelJ2Connecte.setText("Connexion J2...");
            labelJ2.setText("Joueur 2");
            afficherNotification("success", "le joueur 2: " + player2.getName() + " a bien été déconnecté");
        } else {
            afficherNotification("warning", "Le joueur n'est pas connecté");
        }

    }

    @FXML
    protected void retourConnexionJ2() {
        connexionJ2Pane.setVisible(false);
        comptePane.setVisible(true);
        resetConnexionJ2Pane();
    }

//    Création de compte

    private final String[] departements = {
            "01 - Ain", "02 - Aisne", "03 - Allier", "04 - Alpes-de-Haute-Provence", "05 - Hautes-Alpes", "06 - Alpes-Maritimes", "07 - Ardèche", "08 - Ardennes", "09 - Ariège", "10 - Aube", "11 - Aude", "12 - Aveyron", "13 - Bouches-du-Rhône", "14 - Calvados", "15 - Cantal", "16 - Charente", "17 - Charente-Maritime", "18 - Cher", "19 - Corrèze", "2A - Corse-du-Sud", "2B - Haute-Corse", "21 - Côte-d'Or", "22 - Côtes-d'Armor", "23 - Creuse", "24 - Dordogne", "25 - Doubs", "26 - Drôme", "27 - Eure", "28 - Eure-et-Loir", "29 - Finistère", "30 - Gard", "31 - Haute-Garonne", "32 - Gers", "33 - Gironde", "34 - Hérault", "35 - Ille-et-Vilaine", "36 - Indre", "37 - Indre-et-Loire", "38 - Isère", "39 - Jura", "40 - Landes", "41 - Loir-et-Cher", "42 - Loire", "43 - Haute-Loire", "44 - Loire-Atlantique", "45 - Loiret", "46 - Lot", "47 - Lot-et-Garonne", "48 - Lozère", "49 - Maine-et-Loire", "50 - Manche", "51 - Marne", "52 - Haute-Marne", "53 - Mayenne", "54 - Meurthe-et-Moselle", "55 - Meuse", "56 - Morbihan", "57 - Moselle", "58 - Nièvre", "59 - Nord", "60 - Oise", "61 - Orne", "62 - Pas-de-Calais", "63 - Puy-de-Dôme", "64 - Pyrénées-Atlantiques", "65 - Hautes-Pyrénées", "66 - Pyrénées-Orientales", "67 - Bas-Rhin", "68 - Haut-Rhin", "69 - Rhône", "70 - Haute-Saône", "71 - Saône-et-Loire", "72 - Sarthe", "73 - Savoie", "74 - Haute-Savoie", "75 - Paris", "76 - Seine-Maritime", "77 - Seine-et-Marne", "78 - Yvelines", "79 - Deux-Sèvres", "80 - Somme", "81 - Tarn", "82 - Tarn-et-Garonne", "83 - Var", "84 - Vaucluse", "85 - Vendée", "86 - Vienne", "87 - Haute-Vienne", "88 - Vosges", "89 - Yonne", "90 - Territoire de Belfort", "91 - Essonne", "92 - Hauts-de-Seine", "93 - Seine-Saint-Denis", "94 - Val-de-Marne", "95 - Val-d'Oise", "99 - Etranger", "971 - Guadeloupe", "972 - Martinique", "973 - Guyane", "974 - La Réunion", "975 - Mayotte"
    };

    @FXML
    private TextField loginCreerCompte;
    @FXML
    private PasswordField mdpCreerCompte;
    @FXML
    private PasswordField verifMdpCreerCompte;
    @FXML
    private ComboBox<String> comboBoxText = new ComboBox<>();
    @FXML
    private CheckBox checkboxRGPD;

    public void resetCreationPane() {
        loginCreerCompte.clear();
        mdpCreerCompte.clear();
        verifMdpCreerCompte.clear();
        comboBoxText.getSelectionModel().clearSelection();
        checkboxRGPD.setSelected(false);
    }

    @FXML
    protected void retourNouveauCompte() {
        creationComptePane.setVisible(false);
        comptePane.setVisible(true);
        resetCreationPane();
    }

    @FXML
    protected void creerCompteJoueur() {
        if (!loginCreerCompte.getText().equals("")) {
            if (!mdpCreerCompte.getText().equals("")) {
                if (mdpCreerCompte.getText().equals(verifMdpCreerCompte.getText())) {
                    if (mdpCreerCompte.getText().length() < 12) {
                        if (!comboBoxText.getSelectionModel().isEmpty()) {
                            if (PlayerManager.getInstance().getPlayer(loginCreerCompte.getText()) == null) {
                                if (checkboxRGPD.isSelected()) {
                                    PlayerManagerMotron.getInstance().createPlayer(loginCreerCompte.getText(), comboBoxText.getValue().substring(0, 3).strip(), mdpCreerCompte.getText(), false);
                                    afficherNotification("success", "Bienvenue dans nos rangs, " + loginCreerCompte.getText() + " !");
                                    creationComptePane.setVisible(false);
                                    comptePane.setVisible(true);
                                } else {
                                    afficherNotification("warning", "Vous devez lire et accepter la charte de confidentialité avant de continuer");
                                }
                            } else {
                                afficherNotification("warning", "Le compte " + loginCreerCompte.getText() + " existe deja");
                            }
                        } else {
                            afficherNotification("warning", "Veuillez choisir un département");
                        }
                    } else {
                        afficherNotification("warning", "Mot de passe trop long");
                    }
                } else {
                    afficherNotification("warning", "Les mots de passes ne sont pas identiques");
                }
            } else {
                afficherNotification("warning", "Le champ 'mot de passe' n'est pas rempli");
            }
        } else {
            afficherNotification("warning", "Le champ 'login' n'est pas rempli");
        }
    }

    @FXML
    protected void afficheRGPD() {
        idRGPD.setText("Conditions générales d’utilisation\n" +
                "En vigueur au 16/01/2023\n" +
                "Merci de bien vouloir lire les présentes conditions générales d’utilisation:\n\n" +
                "Les présentes conditions générales d'utilisation (dites « CGU ») ont pour objet l'encadrement juridique des modalités de mise à votre disposition par notre CG Games Project. Et de définir les conditions d’accès et d’utilisation des services par « l'Utilisateur ».\n" +
                "Cet accord utilisateur est un accord désignant CG Games Project (ci-après désigné « CG » et « nous ») et vous, que vous soyez un utilisateur enregistré ou que vous accédiez à nos services autrement. Nous nous réservons le droit de modifier les CGU à tout moment.\n\n" +
                "Article 1 : Mention légale\n" +
                "L’édition de CG Games Project est assurée par les équipes de CG dont le siège social est situé à l’IUT de Montpellier.\n" +
                "Adresse email : [adresse email de CG].\n\n" +
                "Article 2 : Collecte de données\n" +
                "Nous collectons des informations sur votre département.\n" +
                "Ces données sont nécessaires au bon fonctionnement des fonctionnalités du jeu dans l’objectif d’établir une expérience compétitive pour les utilisateurs. Nous nous engageons à protéger vos données conformément à la réglementation en vigueur, notamment le Règlement Général sur la Protection des Données (RGPD).\n" +
                "En vertu de la loi Informatique et Libertés, en date du 6 janvier 1978, l'Utilisateur dispose d'un droit d'accès, de rectification, de suppression et d'opposition de ses données personnelles en envoyant un mail à l’adresse : [adresse email de CG]\n\n" +
                "Article 3 : Utilisation des données\n" +
                "Les informations que nous collectons, à savoir votre département, ne seront utilisées que pour améliorer l’expérience utilisateur et nous nous réservons de pouvoir vous offrir des contenus ciblés. Nous ne partagerons pas ces informations à un tiers.\n\n" +
                "Article 4 : Propriété intellectuelle et droit d’auteur\n" +
                "Les marques, logos, signes ainsi que tous les contenus de nos produits (textes, images, son…) font l'objet d'une protection par le Code de la propriété intellectuelle et plus particulièrement par le droit d'auteur.\n" +
                "L'Utilisateur doit solliciter l'autorisation préalable de CG pour toute reproduction, publication ou copie des différents contenus. Il s'engage à une utilisation de nos contenus dans un cadre strictement privé, toute utilisation à des fins commerciales et publicitaires est strictement interdite.\n" +
                "Le non-respect de ses conditions peut amener à des sanctions.\n\n" +
                "Article 5 : Responsabilité\n" +
                "Les sources des informations diffusées sur CG sont fiables mais le produit ne garantit pas qu’il soit exempt de défauts, d’erreurs ou d’omissions. Le produit CG ne peut être tenu pour responsable d’éventuels virus qui pourraient infecter l’ordinateur ou tout matériel informatique de l'utilisateur, suite à une utilisation, à l’accès, ou au téléchargement provenant de ce produit.\n" +
                "Nous ne sommes pas responsables des dommages causés à votre appareil ou de toute perte de données résultant de l'utilisation de notre jeu. Vous utilisez le jeu à vos propres risques.\n\n" +
                "Article 6 : Droit applicable et juridiction compétente\n" +
                "La législation française s'applique au présent contrat et seuls les tribunaux français seront compétents pour résoudre un litige. Pour toute question relative à l’application des présentes CGU, vous pouvez joindre l’éditeur aux coordonnées inscrites à l’article 1.\n\n");
        RGPDPane.setVisible(true);
    }

    @FXML
    protected void retourRGPD() {
        RGPDPane.setVisible(false);
    }

    // Page de statistiques :
    @FXML
    private Pane statsJ1Pane;
    @FXML
    private Label highScoreStatJ1;
    @FXML
    private Label nomJoueur1Stat;

    @FXML
    protected void lanceStatsJ1() {
        comptePane.setVisible(false);
        statsJ1Pane.setVisible(true);
        nomJoueur1Stat.setText(j1.getNomJoueur());
        Stat statJ1 = ScorePartieManager.getInstance().createStat(j1.getNomJoueur());
        idTotalScoreJ1.setText(String.valueOf(ScoreManagerMotron.getInstance().sommeScore(j1.getNomJoueur())));
        highScoreStatJ1.setText(String.valueOf(ScoreManagerMotron.getInstance().highScore(j1.getNomJoueur())));
        idNbCasesJ1.setText(String.valueOf(statJ1.getNbBlocs()));
        idKillJ1.setText(String.valueOf(statJ1.getKill()));
        idMortJ1.setText(String.valueOf(statJ1.getDeath()));
        idRatioJ1.setText(String.valueOf(statJ1.getMoyenneKillParPartie()));
        idVictoireJ1.setText(String.valueOf(statJ1.getNbVictoire()));
        idDefaiteJ1.setText(String.valueOf(statJ1.getNbDefaite()));
        idEgaliteJ1.setText(String.valueOf(statJ1.getNbEgalite()));
    }

    @FXML
    private Pane statsJ2Pane;
    @FXML
    private Label highScoreStatJ2;
    @FXML
    private Label nomJoueur2Stat;

    @FXML
    protected void lanceStatsJ2() {
        comptePane.setVisible(false);
        statsJ2Pane.setVisible(true);
        nomJoueur2Stat.setText(j2.getNomJoueur());
        Stat statJ2 = ScorePartieManager.getInstance().createStat(j2.getNomJoueur());
        idTotalScoreJ2.setText(String.valueOf(ScoreManagerMotron.getInstance().sommeScore(j2.getNomJoueur())));
        highScoreStatJ2.setText(String.valueOf(ScoreManagerMotron.getInstance().highScore(j2.getNomJoueur())));
        idNbCasesJ2.setText(String.valueOf(statJ2.getNbBlocs()));
        idKillJ2.setText(String.valueOf(statJ2.getKill()));
        idMortJ2.setText(String.valueOf(statJ2.getDeath()));
        idRatioJ2.setText(String.valueOf(statJ2.getMoyenneKillParPartie()));
        idVictoireJ2.setText(String.valueOf(statJ2.getNbVictoire()));
        idDefaiteJ2.setText(String.valueOf(statJ2.getNbDefaite()));
        idEgaliteJ2.setText(String.valueOf(statJ2.getNbEgalite()));
    }

    @FXML
    protected void retourStats() {
        statsJ1Pane.setVisible(false);
        statsJ2Pane.setVisible(false);
        comptePane.setVisible(true);
    }

    public void afficherButtonsStats() {
        bouttonStatsJ1.setVisible(j1.isConnecter());
        bouttonStatsJ2.setVisible(j2.isConnecter());
    }

//    ------------------------------------------------------------------------------------------------------------------

    //     Règles
    @FXML
    Pane reglesPane;

    @FXML
    protected void lanceRegles() {
        menuPane.setVisible(false);
        reglesPane.setVisible(true);
    }

    @FXML
    protected void retourRegles() {
        reglesPane.setVisible(false);
        menuPane.setVisible(true);
    }

    //    ------------------------------------------------------------------------------------------------------------------
    //     Controller
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxText.getItems().addAll(departements);
    }

    public ControllerFXML() {
        player1 = new Player("J1");
        player2 = new Player("J2");
        j1 = new VueMoto("J1", new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/TronLegacyBleu.png")), 60, 40, true, true), Color.LIGHTBLUE, "Motron Bleu"), player1.getName());
        j2 = new VueMoto("J2", new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/TronLegacyJaune.png")), 60, 40, true, true), Color.YELLOW, "Motron Jaune"), player2.getName());

        keyList.add(Z);
        keyList.add(Q);
        keyList.add(S);
        keyList.add(D);
        keyList.add(UP);
        keyList.add(LEFT);
        keyList.add(DOWN);
        keyList.add(RIGHT);

        j1.setAdversaire(j2);
        j2.setAdversaire(j1);

        Media soundMenu = new Media(String.valueOf(Motron.class.getResource("music/musiqueMenu2.mp3")));
        mediaPlayerMenu = new MediaPlayer(soundMenu);
        mediaPlayerMenu.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerMenu.setVolume(0.05);

        Media soundJeu = new Media(String.valueOf(Motron.class.getResource("music/musiqueJeu.mp3")));
        mediaPlayerJeu = new MediaPlayer(soundJeu);
        mediaPlayerJeu.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerJeu.setVolume(0.05);

        Media soundStart = new Media(String.valueOf(Motron.class.getResource("music/StartSound.mp3")));
        startSound = new MediaPlayer(soundStart);
        startSound.setVolume(0.05);

        Media notifMedia = new Media(String.valueOf(Motron.class.getResource("music/notif.mp3")));
        notificationSound = new MediaPlayer(notifMedia);
        notificationSound.setVolume(0.5);


        Media pNotifMedia = new Media(String.valueOf(Motron.class.getResource("music/positiveNotif.wav")));
        positiveNotificationSound = new MediaPlayer(pNotifMedia);
        positiveNotificationSound.setVolume(0.1);
    }

    public VueJeu creerJeu() {
        j1.setScore(0);
        j2.setScore(0);
        return new VueJeu(j1, j2, vueArene, keyList);
    }


    public void afficherNotification(String type, String message) {
        notificationLabel.getStyleClass().clear();
        if (type.equals("success")) {
            positiveNotificationSound.seek(Duration.millis(0));
            positiveNotificationSound.play();
            notificationLabel.getStyleClass().add("notificationSuccess");
        }
        if (type.equals("warning")) {
            notificationSound.seek(Duration.millis(0));
            notificationSound.play();
            notificationLabel.getStyleClass().add("notificationWarning");
        }
        if (type.equals("error")) {
            notificationSound.seek(Duration.millis(0));
            notificationSound.play();
            notificationLabel.getStyleClass().add("notificationError");
        }
        notificationLabel.setText(message);
        notificationLabel.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.millis(2000));
        pause.setOnFinished(e -> notificationLabel.setVisible(false));
        pause.play();
    }


//    Getters and setters

    public MediaPlayer getMediaPlayerMenu() {
        return mediaPlayerMenu;
    }

    public Pane getMenuPane() {
        return menuPane;
    }

    public Pane getJeuPane() {
        return jeuPane;
    }

    public Pane getComptePane() {
        return comptePane;
    }

    public Pane getGameOverPane() {
        return gameOverPane;
    }

    public Pane getArenePane() {
        return arenePane;
    }

    public Pane getPausePane() {
        return pausePane;
    }

    public VueJeu getJeu() {
        return jeu;
    }

    public boolean isNewJeu() {
        return newJeu;
    }

    public void setNewJeu(boolean newJeu) {
        this.newJeu = newJeu;
    }

    public Label getRound() {
        return round;
    }

    public Label getScoreJ1() {
        return scoreJ1;
    }

    public Label getScoreJ2() {
        return scoreJ2;
    }

    public Label getScoreGOJ1() {
        return scoreGOJ1;
    }

    public Label getScoreGOJ2() {
        return scoreGOJ2;
    }

    public ImageView getMort1J1() {
        return mort1J1;
    }

    public ImageView getMort2J1() {
        return mort2J1;
    }

    public ImageView getMort3J1() {
        return mort3J1;
    }

    public ImageView getMort1J2() {
        return mort1J2;
    }

    public ImageView getMort2J2() {
        return mort2J2;
    }

    public ImageView getMort3J2() {
        return mort3J2;
    }

    public Label getLabelFin() {
        return labelFin;
    }

    public Pane getDecomptePane() {
        return decomptePane;
    }

    public int getxJ1() {
        return xJ1;
    }

    public int getyJ1() {
        return yJ1;
    }

    public int getxJ2() {
        return xJ2;
    }

    public int getyJ2() {
        return yJ2;
    }

    public ImageView getGifBoomJ1() {
        return gifBoomJ1;
    }

    public ImageView getGifBoomJ2() {
        return gifBoomJ2;
    }

    public VueMoto getJ1(){ return j1; }
    //    ------------------------------------------------------------------------------------------------------------------

}
