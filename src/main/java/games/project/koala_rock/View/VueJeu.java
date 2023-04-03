package games.project.koala_rock.View;

import games.project.koala_rock.Metier.manager.ScoreManager;
import games.project.koala_rock.Model.*;
import games.project.koala_rock.RessourcesAccess;
import games.project.koala_rock.Sound.Son;
import games.project.stockage.Session;
import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class VueJeu {
    private int compteur = 1;
    ArrayList<Echelle> echelles;
    ArrayList<EchelleBroken> echellesBrokens;
    ArrayList<ObjetAttaque> tonneaux;
    PersonnagePrincipale personnagePrincipale;
    private boolean isPause = false;
    private Scene scene;
    private Stage primaryStage;
    private PersonnageEnnemi dk;
    private Pane jeu;
    private final VueGagne vueGagne = new VueGagne();
    private final VuePerdre vuesPerdu = new VuePerdre();
    private final VueFinInfiniPartie vueFinInfiniPartie = new VueFinInfiniPartie();
    private String mode = "Normal";
    private final LongProperty time = new SimpleLongProperty(0);
    private Label button_Bas, button_Haut, button_Gauche, button_Droite, button_Espace;
    private ImageView image_Bas, image_Haut, image_Gauche, image_Droite, image_Espace;
    private AnimationTimer timer, collisionTimer;
    private Timeline timelineTonneaux = new Timeline();

    public IntegerProperty getScore() {
        return personnagePrincipale.getScore();
    }

    public String getMode() {
        return mode;
    }

    public IntegerProperty getVie() {
        return personnagePrincipale.getVie();
    }

    public void demarrerJeu(Stage stage, String modeJeu) throws IOException, InterruptedException {

        if (time.getValue() != 0) {
            time.setValue(0);
        }

        if (isPause) {
            isPause = false;
            timer = null;
        }

        primaryStage = stage;
        Pane interfaceJeu = new Pane();
        // add border to interfaceJeu to 20px
        jeu = new Pane();
        BorderPane root = new BorderPane();
        personnagePrincipale = new PersonnagePrincipale(20, -10, 30, 30);
        Echelle echelle1 = new Echelle(400, 485, 25, 80);
        Echelle echelle2 = new Echelle(200, 408, 25, 80);
        Echelle echelle3 = new Echelle(500, 331, 25, 80);
        Echelle echelle4 = new Echelle(100, 255, 25, 80);
        Echelle echelle5 = new Echelle(500, 177, 25, 80);
        Echelle echelle6 = new Echelle(320, 100, 25, 80);

        EchelleBroken echelleBroken1 = new EchelleBroken(300, 331, 25, 80);
        EchelleBroken echelleBroken2 = new EchelleBroken(240, 177, 25, 80);

        PersonnageEnnemi dk = new PersonnageEnnemi(60, 80, 100, 100);
        FondNiveau fondNiveau = new FondNiveau(0, 0, 600, 600);


        personnagePrincipale.setLayoutX(20 * 10);
        personnagePrincipale.setLayoutY(545);

        //////////////// Label ///////////////////////
        Label menuScreen = new Label();
        menuScreen.setGraphic(Fond.getChoixFond());

        Label score = new Label("Score : 0");
        score.getStyleClass().add("Score_Vie");
        score.setLayoutX(900);
        score.setLayoutY(17);

        Label vie = new Label("Vie : 0");
        vie.getStyleClass().add("Score_Vie");
        vie.setLayoutX(20);
        vie.setLayoutY(17);

        Label nomJeu = new Label("Koala Rock");
        nomJeu.getStyleClass().add("nomJeu");
        nomJeu.setLayoutX(500);
        nomJeu.setLayoutY(17);

        Label chrono = new Label("Chrono : 0s");
        chrono.getStyleClass().add("Chrono");
        chrono.setLayoutX(20);
        chrono.setLayoutY(50);

        Label modeDeJeu = new Label("Mode : Classic");
        modeDeJeu.getStyleClass().add("Chrono");
        modeDeJeu.setLayoutX(900);
        modeDeJeu.setLayoutY(50);

        Label niveau = new Label("");
        niveau.getStyleClass().add("Chrono");
        niveau.setLayoutX(20);
        niveau.setLayoutY(100);

        Label niveauAlerte = new Label("");
        niveauAlerte.getStyleClass().add("Score_Vie");
        niveauAlerte.setLayoutX(580);
        niveauAlerte.setLayoutY(390);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), event -> niveauAlerte.setVisible(false)));
        timeline.play();

        //////////////// End Label ///////////////////////

        //////////////// Button ///////////////////////
        button_Bas = new Label();
        image_Bas = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Bas.png"))));
        image_Bas.setFitHeight(50);
        image_Bas.setFitWidth(50);
        button_Bas.setGraphic(image_Bas);
        button_Bas.getStyleClass().add("button_Action");
        button_Bas.setLayoutX(150);
        button_Bas.setLayoutY(530);

        button_Haut = new Label();
        image_Haut = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Haut.png"))));
        image_Haut.setFitHeight(50);
        image_Haut.setFitWidth(50);
        button_Haut.setGraphic(image_Haut);
        button_Haut.getStyleClass().add("button_Action");
        button_Haut.setLayoutX(150);
        button_Haut.setLayoutY(465);

        button_Gauche = new Label();
        image_Gauche = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Gauche.png"))));
        image_Gauche.setFitHeight(50);
        image_Gauche.setFitWidth(50);
        button_Gauche.setGraphic(image_Gauche);
        button_Gauche.getStyleClass().add("button_Action");
        button_Gauche.setLayoutX(80);
        button_Gauche.setLayoutY(530);

        button_Droite = new Label();
        image_Droite = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Droite.png"))));
        image_Droite.setFitHeight(50);
        image_Droite.setFitWidth(50);
        button_Droite.setGraphic(image_Droite);
        button_Droite.getStyleClass().add("button_Action");
        button_Droite.setLayoutX(221);
        button_Droite.setLayoutY(530);

        button_Espace = new Label();
        image_Espace = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Espace.png"))));
        image_Espace.setFitHeight(50);
        image_Espace.setFitWidth(200);
        button_Espace.setGraphic(image_Espace);
        button_Espace.getStyleClass().add("button_Action");
        button_Espace.setLayoutX(75);
        button_Espace.setLayoutY(600);

        Label boutonMenuPrincipal = new Label("Menu Principal");
        boutonMenuPrincipal.getStyleClass().add("LabelConnexionField");
        boutonMenuPrincipal.setLayoutX(1080);
        boutonMenuPrincipal.setLayoutY(650);
        //////////////// End Button ///////////////////////

        //////////////// ALERTE ////////////////
        boutonMenuPrincipal.setOnMouseClicked(event -> {
            Label alerte = new Label("Voulez vous vraiment \n" + "retourner au menu principal ?");
            alerte.getStyleClass().add("LabelError");
            alerte.setLayoutX(160);
            alerte.setLayoutY(200);

            Rectangle rectangle = new Rectangle();
            rectangle.setX(140);
            rectangle.setY(150);
            rectangle.setWidth(350);
            rectangle.setHeight(200);

            rectangle.setArcHeight(50);
            rectangle.setArcWidth(50);
            rectangle.setFill(Color.BLACK);
            rectangle.setEffect(new DropShadow(10, Color.WHITE));

            Button oui = new Button("Oui");
            oui.getStyleClass().add("btnGrey");
            oui.setLayoutX(160);
            oui.setLayoutY(275);

            Button non = new Button("Non");
            non.getStyleClass().add("btnRed");
            non.setLayoutX(410);
            non.setLayoutY(275);

            oui.setOnAction(e -> {
                supprimerElements(jeu, tonneaux, echelles, echellesBrokens, personnagePrincipale, dk);
                collisionTimer.stop();
                timer.stop();
                VueMenu vueMenu = new VueMenu();
                vueMenu.demarrerMenu(stage);
                jeu.getChildren().removeAll(oui, non, rectangle, alerte);
            });
            non.setOnAction(e -> {
                jeu.getChildren().removeAll(oui, non, rectangle, alerte);
            });

            jeu.getChildren().addAll(rectangle, oui, non, alerte);
        });
        //////////////// End ALERTE ////////////////


        //////////////// Chronometre ///////////////////////
        timer = new AnimationTimer() {
            private long timestamp;
            private long fraction = 0;

            /**
             * Démarre le chronomètre
             */
            @Override
            public void start() {
                // current time adjusted by remaining time from last run
                timestamp = System.currentTimeMillis() - fraction;
                super.start();
            }

            /**
             * Arrête le chronomètre
             */
            @Override
            public void stop() {
                fraction = System.currentTimeMillis() - timestamp;
                super.stop();
                // save leftover time not handled with the last update
            }

            /**
             * Met à jour le chronomètre
             *
             * @param now
             */
            @Override
            public void handle(long now) {
                long newTime = System.currentTimeMillis();
                if (timestamp + 1000 <= newTime) {
                    long deltaT = (newTime - timestamp) / 1000;
                    time.set(time.get() + deltaT);
                    timestamp += 1000 * deltaT;
                }
            }
        };

        //////////////// Listener score,vie,chrono ///////////////////////
        personnagePrincipale.getScore().addListener((observableValue, number, t1) -> score.setText("Score : " + personnagePrincipale.getScore().getValue().toString()));
        personnagePrincipale.getVie().addListener((observableValue, number, t1) -> vie.setText("Vie : " + personnagePrincipale.getVie().getValue().toString()));
        time.addListener((observableValue, number, t1) -> chrono.setText("Chrono : " + time.getValue().toString() + "s"));


        if (modeJeu.equals("Normal")) {
            mode = "Normal";
            getVie().setValue(1);
        } else if (modeJeu.equals("Infini")) {
            mode = "Infini";
            modeDeJeu.setText("Mode : Infini");
            niveau.setText("Niveau : " + compteur);
            niveauAlerte.setText("Niveau : " + compteur);
            getVie().setValue(3);
        }

        // Fond
        interfaceJeu.getChildren().add(menuScreen);
        // panneau du jeu
        jeu.setPrefSize(600, 600);
        // Fond
        jeu.getChildren().add(fondNiveau);
        // Echelle
        jeu.getChildren().addAll(echelle1, echelle2, echelle3, echelle4, echelle5, echelle6);
        // EchelleBroken
        jeu.getChildren().addAll(echelleBroken1, echelleBroken2);
        // Perso
        jeu.getChildren().addAll(personnagePrincipale, dk);
        // Tonneaux
        Rectangle tour = new Rectangle(335, 105, 610, 610);
        tour.setFill(Color.LIGHTGRAY);
        tour.setEffect(new DropShadow(20, Color.WHITE));
        tour.setEffect(new GaussianBlur(10));

        interfaceJeu.getChildren().add(tour);

        // Score
        // Placement du jeu dans le panneau
        jeu.setLayoutX(340);
        jeu.setLayoutY(110);

        // Interface jeu
        interfaceJeu.setPrefSize(1280, 720);
        interfaceJeu.getChildren().add(jeu);
        // Couleur Interface
        interfaceJeu.setStyle("-fx-background-color: #000000;");

        interfaceJeu.getChildren().addAll(score, vie, nomJeu, chrono, modeDeJeu, niveau, niveauAlerte);
        interfaceJeu.getChildren().add(boutonMenuPrincipal);
        interfaceJeu.getChildren().addAll(button_Bas, button_Haut, button_Gauche, button_Droite, button_Espace);

        //////////////////////// Echelle ////////////////////////
        // Attribution des coordonnées etc des échelles
        echelles = new ArrayList<>();
        echelles.addAll(Arrays.asList(echelle1, echelle2, echelle3, echelle4, echelle5, echelle6));

        // Attribution des coordonnées des echelles
        ArrayList<Double> coordonneesEchelle1 = new ArrayList<>();
        coordonneesEchelle1.add(380.0); // x
        coordonneesEchelle1.add(468.0); // y
        ArrayList<Double> coordonneesEchelle2 = new ArrayList<>();
        coordonneesEchelle2.add(180.0);
        coordonneesEchelle2.add(391.0);
        ArrayList<Double> coordonneesEchelle3 = new ArrayList<>();
        coordonneesEchelle3.add(475.0);
        coordonneesEchelle3.add(314.0);
        ArrayList<Double> coordonneesEchelle4 = new ArrayList<>();
        coordonneesEchelle4.add(75.0);
        coordonneesEchelle4.add(237.0);
        ArrayList<Double> coordonneesEchelle5 = new ArrayList<>();
        coordonneesEchelle5.add(475.0);
        coordonneesEchelle5.add(160.0);
        ArrayList<Double> coordonneesEchelle6 = new ArrayList<>();
        coordonneesEchelle6.add(300.0);
        coordonneesEchelle6.add(83.0);

        ArrayList<ArrayList<Double>> coordonneesEchelles = new ArrayList<>(Arrays.asList(coordonneesEchelle1, coordonneesEchelle2, coordonneesEchelle3, coordonneesEchelle4, coordonneesEchelle5, coordonneesEchelle6));

        //////////////////////// EchelleBroken ////////////////////////
        echellesBrokens = new ArrayList<>(Arrays.asList(echelleBroken1, echelleBroken2));

        ArrayList<Double> coordonneesEchelleBroken1 = new ArrayList<>();
        coordonneesEchelleBroken1.add(275.0);
        coordonneesEchelleBroken1.add(314.0);
        ArrayList<Double> coordonneesEchelleBroken2 = new ArrayList<>();
        coordonneesEchelleBroken2.add(220.0);
        coordonneesEchelleBroken2.add(160.0);

        coordonneesEchelles.addAll(Arrays.asList(coordonneesEchelleBroken1, coordonneesEchelleBroken2));

        ////////////////////////////////////////////////

        // Tonneaux
        creerTonneaux(coordonneesEchelles, dk);

        /**
         * Boucle de jeu
         */
        collisionTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                try {
                    if (personnagePrincipale.collisionTonneaux(tonneaux) == -1 && !isPause) {
                        if (getVie().getValue() > 1) {
                            personnagePrincipale.setLayoutX(20 * 10);
                            personnagePrincipale.setLayoutY(545);
                            getVie().setValue(getVie().getValue() - 1);
                            supprimerTonneaux(tonneaux);
                            creerTonneaux(coordonneesEchelles, dk);
                        } else {
                            timer.stop();
                            isPause = true;
                            personnagePrincipale.setLayoutX(20 * 10);
                            personnagePrincipale.setLayoutY(545);
                            //replacer les tonneaux
                            for (ObjetAttaque tonneau : tonneaux) {
                                tonneau.setLayoutX(0);
                                tonneau.setLayoutY(-30);
                            }
                            int saveScore = getScore().getValue();

                            supprimerElements(jeu, tonneaux, echelles, echellesBrokens, personnagePrincipale, dk);
                            //empecher le jeu de continuer
                            timelineTonneaux.stop();
                            primaryStage.close();
                            if (mode.equals("Infini")) {
                                vueFinInfiniPartie.screenLose(saveScore, stage);
                                if (Session.getInstance().getLogin() == null) {
                                    ScoreManager.getInstance().createScore(saveScore, "");
                                } else {
                                    ScoreManager.getInstance().createScore(saveScore, Session.getInstance().getLogin());
                                }
                            } else {
                                vuesPerdu.screenLose(stage);
                            }
                        }
                    }
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                if (personnagePrincipale.getLayoutX() == 305 && personnagePrincipale.getLayoutY() == 94 || personnagePrincipale.getLayoutX() == 300 && personnagePrincipale.getLayoutY() == 94 || personnagePrincipale.getLayoutX() == 295 && personnagePrincipale.getLayoutY() == 94 || personnagePrincipale.getLayoutX() == 290 && personnagePrincipale.getLayoutY() == 94) {
                    if (getVie().getValue() > 1 || getVie().getValue() > 0 && mode.equals("Infini")) {
                        personnagePrincipale.setLayoutX(20 * 10);
                        personnagePrincipale.setLayoutY(545);
                        getScore().setValue(getScore().getValue() + 1000);
                        supprimerTonneaux(tonneaux);
                        creerTonneaux(coordonneesEchelles, dk);
                        niveau.setText("Niveau : " + (compteur += 1));
                        niveauAlerte.setText("Niveau : " + (compteur));
                        niveauAlerte.setVisible(true);
                        timeline.play();
                    } else {
                        isPause = true;
                        personnagePrincipale.setLayoutX(20 * 10);
                        personnagePrincipale.setLayoutY(545);
                        supprimerElements(jeu, tonneaux, echelles, echellesBrokens, personnagePrincipale, dk);
                        primaryStage.close();
                        vueGagne.screenWin(personnagePrincipale.getScore(), stage);
                        if (modeJeu.equals("Normal")) {
                            if (Session.getInstance().getLogin() == null) {
                                ScoreManager.getInstance().addTemps(time.getValue(), "");
                            } else {
                                ScoreManager.getInstance().addTemps(time.getValue(), Session.getInstance().getLogin());
                            }
                        }
                    }
                }
            }
        };
        // Fin Start Game //
        collisionTimer.start();
        root.setCenter(interfaceJeu);
        scene = new Scene(root);
        scene.getStylesheets().add(String.valueOf(RessourcesAccess.class.getResource("css/style.css")));
        move(personnagePrincipale, echelles, echellesBrokens, coordonneesEchelles);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            Label alerte = new Label("Voulez vous vraiment \n" + "quitter le jeu ?");
            alerte.getStyleClass().add("LabelError");
            alerte.setLayoutX(520);
            alerte.setLayoutY(250);

            Rectangle rectangle = new Rectangle();
            rectangle.setX(500);
            rectangle.setY(200);
            rectangle.setWidth(300);
            rectangle.setHeight(200);

            rectangle.setArcHeight(50);
            rectangle.setArcWidth(50);
            rectangle.setFill(Color.BLACK);
            rectangle.setEffect(new DropShadow(10, Color.WHITE));

            Button oui = new Button("Oui");
            oui.getStyleClass().add("btnGrey");
            oui.setLayoutX(520);
            oui.setLayoutY(325);

            Button non = new Button("Non");
            non.getStyleClass().add("btnRed");
            non.setLayoutX(720);
            non.setLayoutY(325);

            oui.setOnAction(e -> {
                Session.getInstance().disconnect();
                System.exit(0);
            });
            non.setOnAction(e -> {
                interfaceJeu.getChildren().removeAll(oui, non, rectangle, alerte);
            });
            interfaceJeu.getChildren().addAll(rectangle, alerte, oui, non);
        });
    }

    // Méthode qui permet de retourner la scène du jeu
    public void restartGame(Stage stage) throws InterruptedException {
        stage.close();
        supprimerElements(jeu, tonneaux, echelles, echellesBrokens, personnagePrincipale, dk);
        personnagePrincipale.setLayoutX(20 * 10);
        personnagePrincipale.setLayoutY(545);
        sleep(1000);
    }

    // Méthode qui permet de supprimer les éléments du jeu
    public void supprimerElements(Pane jeu, ArrayList<ObjetAttaque> tonneaux, ArrayList<Echelle> echelles, ArrayList<EchelleBroken> echellesBrokens, PersonnagePrincipale personnagePrincipale, PersonnageEnnemi dk) {
        supprimerTonneaux(tonneaux);
        echelles = null;
        echellesBrokens = null;
        personnagePrincipale = null;
        dk = null;
        jeu = null;
    }

    // Méthode qui permet de supprimer les tonneaux
    public void supprimerTonneaux(ArrayList<ObjetAttaque> tonneaux) {
        for (ObjetAttaque tonneau : tonneaux) {
            jeu.getChildren().remove(tonneau);
        }
        timelineTonneaux.stop();
        timer.stop();
    }

    // Méthode qui permet de créer les tonneaux
    public void creerTonneaux(ArrayList<ArrayList<Double>> coordonneesEchelles, PersonnageEnnemi dk) {
        ObjetAttaque tonneau1 = new ObjetAttaque(20, -10, 20, 20);
        ObjetAttaque tonneau2 = new ObjetAttaque(20, -10, 20, 20);
        ObjetAttaque tonneau3 = new ObjetAttaque(20, -10, 20, 20);
        ObjetAttaque tonneau4 = new ObjetAttaque(20, -10, 20, 20);
        ObjetAttaque tonneau5 = new ObjetAttaque(20, -10, 20, 20);
        tonneau1.setLayoutY(-30);
        tonneau2.setLayoutY(-30);
        tonneau3.setLayoutY(-30);
        tonneau4.setLayoutY(-30);
        tonneau5.setLayoutY(-30);

        jeu.getChildren().addAll(tonneau1, tonneau2, tonneau3, tonneau4, tonneau5);

        tonneaux = new ArrayList<>(Arrays.asList(tonneau1, tonneau2, tonneau3, tonneau4, tonneau5));
        if (!isPause) {
            tonneau1.moveTonneaux(coordonneesEchelles, dk);
            tonneau1.setLayoutY(160);
            dk.lance(tonneau1);
        }
        final IntegerProperty i = new SimpleIntegerProperty(0);
        final int[] x = {1};
        timelineTonneaux = new Timeline(new KeyFrame(Duration.seconds(6), event -> {
            i.set(i.get() + 1);
            if (x[0] < 5) {
                tonneaux.get(x[0]).moveTonneaux(coordonneesEchelles, dk);
                tonneaux.get(x[0]).setLayoutY(160);
                dk.lance(tonneaux.get(x[0]));
                x[0]++;
            }


        }));
        timelineTonneaux.setCycleCount(Animation.INDEFINITE);
        timelineTonneaux.play();
        timer.start();
    }

    // Méthode qui permet le mouvement
    private void move(PersonnagePrincipale personnagePrincipale, ArrayList<Echelle> echelles, ArrayList<EchelleBroken> echellesBrokens, ArrayList<ArrayList<Double>> coordonneesEchelles) {
        ImageView image_Haut_Click = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Haut_Click.png"))));
        image_Haut_Click.setFitHeight(50);
        image_Haut_Click.setFitWidth(50);

        ImageView image_Bas_Click = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Bas_Click.png"))));
        image_Bas_Click.setFitHeight(50);
        image_Bas_Click.setFitWidth(50);

        ImageView image_Gauche_Click = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Gauche_Click.png"))));
        image_Gauche_Click.setFitHeight(50);
        image_Gauche_Click.setFitWidth(50);

        ImageView image_Droite_Click = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Droite_Click.png"))));
        image_Droite_Click.setFitHeight(50);
        image_Droite_Click.setFitWidth(50);

        ImageView image_Espace_Click = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Espace_Click.png"))));
        image_Espace_Click.setFitHeight(50);
        image_Espace_Click.setFitWidth(200);


        scene.setOnKeyPressed((KeyEvent event) -> {
            personnagePrincipale.tomberEtage();
            switch (event.getCode()) {
                case UP:
                    button_Haut.setGraphic(image_Haut_Click);
                    button_Bas.setGraphic(image_Bas);
                    button_Gauche.setGraphic(image_Gauche);
                    button_Droite.setGraphic(image_Droite);
                    button_Espace.setGraphic(image_Espace);
                    if (personnagePrincipale.collisionEchelle(echelles) && !(personnagePrincipale.estEn(coordonneesEchelles))) {
                        personnagePrincipale.directionHaut();
                        personnagePrincipale.setEstSurEchelle(true);
                    }
                    if (personnagePrincipale.collisionEchelleBroken(echellesBrokens) && !(personnagePrincipale.estEnBroken(coordonneesEchelles))) {
                        personnagePrincipale.directionHaut();
                        personnagePrincipale.setEstSurEchelle(true);
                    }
                    break;
                case LEFT:
                    button_Haut.setGraphic(image_Haut);
                    button_Bas.setGraphic(image_Bas);
                    button_Gauche.setGraphic(image_Gauche_Click);
                    button_Droite.setGraphic(image_Droite);
                    button_Espace.setGraphic(image_Espace);
                    if (!personnagePrincipale.estDansEchelle(coordonneesEchelles)) {
                        personnagePrincipale.directionGauche();
                        personnagePrincipale.setEstSurEchelle(false);
                    }
                    break;
                case RIGHT:
                    button_Haut.setGraphic(image_Haut);
                    button_Bas.setGraphic(image_Bas);
                    button_Gauche.setGraphic(image_Gauche);
                    button_Droite.setGraphic(image_Droite_Click);
                    button_Espace.setGraphic(image_Espace);
                    if (!personnagePrincipale.estDansEchelle(coordonneesEchelles)) {
                        personnagePrincipale.directionDroite(scene.getWidth());
                        personnagePrincipale.setEstSurEchelle(false);
                    }
                    break;
                case DOWN:
                    button_Haut.setGraphic(image_Haut);
                    button_Bas.setGraphic(image_Bas_Click);
                    button_Gauche.setGraphic(image_Gauche);
                    button_Droite.setGraphic(image_Droite);
                    button_Espace.setGraphic(image_Espace);
                    if (personnagePrincipale.collisionEchelle(echelles) && !(personnagePrincipale.estDansBasEchelle(coordonneesEchelles))) {
                        personnagePrincipale.directionBas(scene.getHeight());
                    }
                    if (personnagePrincipale.collisionEchelleBroken(echellesBrokens) && !(personnagePrincipale.estDansBasEchelle(coordonneesEchelles))) {
                        personnagePrincipale.directionBas(scene.getHeight());
                    }
                    personnagePrincipale.setEstSurEchelle(false);
                    break;
                case SPACE:
                    button_Haut.setGraphic(image_Haut);
                    button_Bas.setGraphic(image_Bas);
                    button_Gauche.setGraphic(image_Gauche);
                    button_Droite.setGraphic(image_Droite);
                    button_Espace.setGraphic(image_Espace_Click);
                    if (!personnagePrincipale.isEstEnSaut()) {
                        Son.jump();
                        PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
                        personnagePrincipale.jump();
                        personnagePrincipale.setaEuSonScore(false);
                        pause.play();
                        pause.setOnFinished(event1 -> personnagePrincipale.atterir());
                        break;
                    }
                default:
                    break;
            }
        });

    }
}
