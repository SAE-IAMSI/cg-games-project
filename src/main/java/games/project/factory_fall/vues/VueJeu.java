package games.project.factory_fall.vues;

import games.project.factory_fall.FactoryFall;
import games.project.factory_fall.IJeu;
import games.project.factory_fall.logique.Jeu;
import games.project.factory_fall.logique.Plateau;
import games.project.factory_fall.parametres.Musique;
import games.project.factory_fall.parametres.Preferences;
import games.project.factory_fall.parametres.Ressources;
import games.project.factory_fall.stockage.ScoreManager;
import games.project.factory_fall.stockage.Session;
import games.project.factory_fall.vues.helpers.HelperPieceExterieur;
import games.project.factory_fall.vues.helpers.HelperVuePlateau;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class VueJeu extends Stage implements Menu {
    // Objets issus de JavaFX pour l'IHM du jeu
    private boolean jeuEnPause = false;
    private Scene scene;
    private BorderPane root;
    private VBox informationsJoueur;
    private ActionListener descenteAuto;
    private Button startJeu;
    private HBox nbVies;
    private Label score, pseudo, rang, nbLignes, vies, prochainePieceLabel, pieceSauvegardeeLabel;
    private VBox conteneurDroit;
    private VBox conteneurGauche;

    // Vues personnelles (créées par l'équipe)
    private HelperVuePlateau vuePlateau;
    private HelperPieceExterieur vueProchainePiece;
    private HelperPieceExterieur vuePieceSauvegardee;
    private VueControles vueControles;

    // Objets de la logique du jeu
    private IJeu jeu;
    private Plateau p;
    private Plateau prochainePiece;
    private Plateau stockage;
    private String nomjoueur = "";
    private int nbViesInitial;

    /**
     * Le StackPane sp permet de superposer les éléments de l'IHM. Ici, il superpose le plateau de jeu et l'icone de pause.
     */
    private StackPane sp;
    private final ImageView imgPause = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/pause.png"))));

    /**
     * Constructeur de la vue du jeu.
     *
     * @param modeDeJeu Le mode de jeu choisi par le joueur.
     */
    public VueJeu(String modeDeJeu) {
        nomjoueur = Session.getInstance().isConnected() ? Session.getInstance().getLogin() : "Anonyme";

        switch (modeDeJeu) {
            case "NORMAL" -> {
                nbViesInitial = 1;
                demarrerPartie();
            }
            case "AVENTURE" -> {
                nbViesInitial = 3;
                demarrerPartie();
            }
        }
    }

    public void demarrerPartie() {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        // Initialisations des objets nécessaires
        // classes de la logique du jeu
        Musique.stopMusicMainMenu();
        Musique.playMusicGame();

        jeu = new Jeu(nomjoueur, nbViesInitial);
        jeu.jeuEnCoursProperty().setValue(false);
        p = jeu.getPlateau();
        prochainePiece = jeu.getProchainePiece();
        stockage = jeu.getStockage();

        // javaFX
        vuePlateau = new HelperVuePlateau(p);
        vueProchainePiece = new HelperPieceExterieur(prochainePiece);
        vuePieceSauvegardee = new HelperPieceExterieur(stockage);
        vueControles = new VueControles();

        startJeu = new Button();
        score = new Label("score : 0.0");
        pseudo = new Label(jeu.getJoueur().getPseudo());
        rang = new Label("rang : 1");
        nbLignes = new Label("Lignes : 0");
        vies = new Label("Vies : ");
        nbVies = new HBox();
        if (jeu.getNbVies().get() != 1) {
            for (int i = 0; i < jeu.getNbVies().get(); i++) {
                nbVies.getChildren().add(new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/heart.png")))));
            }
        }
        prochainePieceLabel = new Label("prochaine :");
        pieceSauvegardeeLabel = new Label("pièce sauvegardée : ");
        conteneurDroit = new VBox(new VBox(prochainePieceLabel, vueProchainePiece), vueControles);
        informationsJoueur = new VBox(pseudo, score, nbLignes, rang, nbVies);
        conteneurGauche = new VBox(new VBox(pieceSauvegardeeLabel, vuePieceSauvegardee), informationsJoueur, startJeu);
        vuePieceSauvegardee.setAlignment(Pos.CENTER_RIGHT);

        // Gestion Plateau/Bouton Pause
        sp = new StackPane(imgPause, vuePlateau);

        // Affectations et constitution de vues
        root.setLeft(conteneurGauche);
        root.setCenter(sp);
        root.setRight(conteneurDroit);

        // Initialisation des grilles
        vuePlateau.initialiser();
        vueProchainePiece.initialiser();
        vuePieceSauvegardee.initialiser();

        // "Stylisation" et bindings/listeners
        creerBindings();
        styliser();

        // Initialisation du timer (obligatoire après les bindings)
        Jeu.timer = new Timer(1000, descenteAuto);

        // Bouton pause
        imgPause.setVisible(false);

        // Affichage définitif
        afficherScene();
    }

    /**
     * Créé tous les listeners/bindings afin que la partie se déroule correctement.
     * Voir les commentaires respectifs à chaque listener pour plus d'informations.
     */
    private void creerBindings() {
        // Listener sur les "inputs" (actions du joueur) lors d'une partie. Ces inputs sont les flèches du clavier
        scene.setOnKeyPressed(keyEvent -> {
            if (jeu.isJeuEnCours()) {
                if (!jeuEnPause) {
                    switch (keyEvent.getCode()) {
                        case LEFT -> jeu.actionGauche();
                        case RIGHT -> jeu.actionDroite();
                        case DOWN -> jeu.actionBas();
                        case UP -> jeu.actionHaut();
                        case R -> jeu.actionR();
                        case ESCAPE -> jeu.actionEchap();
                        case SPACE -> jeu.actionEspace();
                        case C -> jeu.actionC();
                    }
                }
                if (keyEvent.getCode() == KeyCode.P) {
                    jeuEnPause = !jeuEnPause;
                    if (jeuEnPause) {
                        Jeu.timer.stop();
                        imgPause.setVisible(true);
                    } else {
                        Jeu.timer.start();
                        imgPause.setVisible(false);
                    }
                    ObservableList<Node> childs = this.sp.getChildren();

                    if (childs.size() > 1) {
                        Node topNode = childs.get(childs.size() - 1);
                        topNode.toBack();
                    }
                }
                vuePlateau.mettreAJour();
                vueProchainePiece.mettreAJour();
                vuePieceSauvegardee.mettreAJour();
            }
        });

        // On définit l'action à faire automatiquement et le donne au Timer, qui l'exécutera tout seul
        descenteAuto = e -> Platform.runLater(() -> {
            jeu.tomberPieceActuelle1Ligne();
            vuePlateau.mettreAJour();
            jeu.jouerTour();
            vueProchainePiece.mettreAJour();
        });

        // Listener sur le score du joueur
        jeu.getJoueur().getScore().addListener((observableValue, number, t1) -> score.setText("score : " + jeu.getJoueur().getScore().getValue() + ""));

        // Listener pour actualiser le rang de la partie
        jeu.getRang(jeu.getPlateau()).addListener((observableValue, number, t1) -> rang.setText("rang : " + jeu.getRang(jeu.getPlateau()).getValue()));

        // Listener le nombre de vies restantes
        jeu.getNbVies().addListener((observableValue, number, t1) -> nbVies.getChildren().remove(nbVies.getChildren().size() - 1));


        // Listener pour agir en cas de fin de jeu
        // Gère les cas : Rejouer et Réessayer
        jeu.jeuEnCoursProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!jeu.isJeuEnCours()) {
                VueGameOver vueGameOver = new VueGameOver(jeu.getJoueur().getScore().getValue());
                vueGameOver.afficherScene();
                ScoreManager.getInstance().createScore(jeu.getJoueur().getScore().getValue(), jeu.getJoueur().getLignesSup().getValue(), Session.getInstance().getLogin());
                vueGameOver.arreterJeuProperty().addListener((observableValue12, aBoolean12, t112) -> {
                    if (vueGameOver.arreterJeuProperty().getValue()) {
                        vueGameOver.close();
                        VueMenuPrincipal.getInstance().setScene(VueMenuPrincipal.getInstance().getSceneMenuPrincipal());
                    }
                });

                vueGameOver.retryProperty().addListener((observableValue1, aBoolean1, t11) -> {
                    if (vueGameOver.retryProperty().getValue()) {
                        relancerPartie();
                    }
                });
            }
        });

        // Lance le jeu une fois le bouton startJeu cliqué
        startJeu.setOnAction(actionEvent -> {
            Jeu.timer.start();
            jeu.jeuEnCoursProperty().setValue(true);
            startJeu.setVisible(false);
        });

        jeu.getJoueur().getInstanceScore().nbLignesProperty().addListener(e -> nbLignes.setText("Lignes : " + jeu.getJoueur().getInstanceScore().getNbLignes()));
    }

    /**
     * Relance la partie, en réinitialisant l'état du jeu
     */
    private void relancerPartie() {
        startJeu.setDisable(false);
        sp.getChildren().clear();
        demarrerPartie();
        score.setText("score : 0.0");
        rang.setText("rang : 1");
        pseudo.setText(jeu.getJoueur().getPseudo());
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    private void styliser() {
        // Scene
        scene.getStylesheets().add(Objects.requireNonNull(FactoryFall.class.getResource("css/main.css")).toString());

        // BorderPane
        root.getStyleClass().add("borderPane");
        root.getLeft().getStyleClass().add("leftPane");
        root.getRight().getStyleClass().add("rightPane");
        root.getCenter().getStyleClass().add("centerPane");
        root.setBackground(Preferences.getInstance().getBackground());
        root.setPadding(new Insets(10, 10, 10, 10));

        // VuePlateu
        vuePlateau.getStyleClass().add("vuePlateau");
        vuePlateau.setAlignment(Pos.CENTER);

        // VueProchainePiece
        vueProchainePiece.setAlignment(Pos.TOP_LEFT);

        // vuePieceSauvegardee
        vuePieceSauvegardee.setAlignment(Pos.TOP_RIGHT);

        // informations joueur
        informationsJoueur.setAlignment(Pos.TOP_RIGHT);

        // Pseudo
        pseudo.setTextFill(Color.WHITE);
        pseudo.setFont(Ressources.getPolice(52));
        pseudo.setAlignment(Pos.TOP_RIGHT);

        // Score
        score.getStyleClass().add("score");
        score.setFont(Ressources.getPolice(30));
        score.setTextFill(Color.WHITE);
        score.setAlignment(Pos.TOP_RIGHT);

        //nbLignes
        nbLignes.getStyleClass().add("nbLignes");
        nbLignes.setFont(Ressources.getPolice(30));
        nbLignes.setTextFill(Color.WHITE);
        nbLignes.setAlignment(Pos.TOP_RIGHT);

        // Rang
        rang.setTextFill(Color.WHITE);
        rang.setFont(Ressources.getPolice(30));
        rang.setAlignment(Pos.TOP_RIGHT);

        // Vies
        vies.setTextFill(Color.WHITE);
        vies.setFont(Ressources.getPolice(30));
        vies.setAlignment(Pos.TOP_RIGHT);
        nbVies.setAlignment(Pos.TOP_RIGHT);

        // Label Prochaine pièce
        prochainePieceLabel.setTextFill(Color.WHITE);
        prochainePieceLabel.setFont(Ressources.getPolice(25));
        prochainePieceLabel.setAlignment(Pos.TOP_LEFT);

        //Label Piece sauvegardée
        pieceSauvegardeeLabel.setTextFill(Color.WHITE);
        pieceSauvegardeeLabel.setFont(Ressources.getPolice(25));
        pieceSauvegardeeLabel.setAlignment(Pos.TOP_RIGHT);

        // StartJeu
        startJeu.setText("Start");
        startJeu.setFont(Ressources.getPolice(40));
        startJeu.setStyle("-fx-background-color: black; -fx-text-fill: white");
        startJeu.getStyleClass().add("bouttonStart");

        // Container gauche
        conteneurGauche.setAlignment(Pos.TOP_RIGHT);
        conteneurGauche.setSpacing(10);

        // Container droit
        conteneurDroit.setAlignment(Pos.TOP_RIGHT);
        conteneurDroit.setSpacing(10);

        for (Node node : conteneurGauche.getChildren()) {
            node.getStyleClass().add("panel-partie");
        }
        for (Node node : conteneurDroit.getChildren()) {
            node.getStyleClass().add("panel-partie");
        }

        // Vue Prochaine pièce
        vueProchainePiece.setAlignment(Pos.CENTER_LEFT);

        // VueControles
        vueControles.setAlignment(Pos.BOTTOM_LEFT);

        // Image Pause
        imgPause.setPreserveRatio(true);
        imgPause.setFitWidth(150);
    }

    @Override
    public void afficherScene() {
        VueMenuPrincipal.getInstance().setScene(scene);
    }

}
