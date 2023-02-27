package games.project.factory_fall.vues;

import games.project.factory_fall.FactoryFall;
import games.project.factory_fall.logique.AuthPlayer;
import games.project.factory_fall.parametres.Musique;
import games.project.factory_fall.parametres.Preferences;
import games.project.factory_fall.parametres.Ressources;
import games.project.factory_fall.stockage.PlayerManager;
import games.project.factory_fall.stockage.Security;
import games.project.factory_fall.stockage.Session;
import games.project.factory_fall.vues.menu.VueClassement;
import games.project.factory_fall.vues.menu.VuePersonnaliser;
import games.project.factory_fall.vues.menu.VueRegles;
import games.project.factory_fall.vues.menu.VueSelectionJeu;
import games.project.factory_fall.vues.menu.compte.VueCompteConnecte;
import games.project.factory_fall.vues.menu.compte.VueCompteDeconnecte;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class VueMenuPrincipal extends Stage implements Menu {
    private static VueMenuPrincipal INSTANCE;

    private final BorderPane root;
    private final Scene scene;

    private final ImageView logo;
    private final Button start, regles, personnaliser, compte, classement, quitter;

    private final VBox boutons;
    private final HBox actions;

    // Sous-vues
    private VueCompteDeconnecte vueCompteDeconnecte;
    private VueCompteConnecte vueCompteConnecte;
    private VuePersonnaliser vuePersonnaliser;
    private VueClassement vueClassement;
    private VueRegles vueRegles;
    private VueSelectionJeu vueSelectionJeu;

    public static VueMenuPrincipal getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VueMenuPrincipal();
            INSTANCE.init();
        }
        return INSTANCE;
    }

    private VueMenuPrincipal() {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        // Logo
        logo = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/titre.png"))));

        // Boutons
        start = new Button();
        regles = new Button();
        personnaliser = new Button();
        classement = new Button();
        quitter = new Button();
        compte = new Button();

        actions = new HBox(compte, quitter);
        boutons = new VBox(logo, start, regles, classement, personnaliser);

        // Affectations
        root.setCenter(boutons);
        root.setBottom(actions);
        root.setBackground(Preferences.getInstance().getBackground());

        if (!Preferences.getInstance().getMusiqueMute()) Musique.playMusicMainMenu();

        styliser();

        this.setScene(scene);
        this.show();
    }

    /**
     * Méthode permettant d'initialiser les attributs nécessitant que la vueMenuPrincipal
     * soit d'ores et déjà créée (à cause de getInstance).
     */
    public void init() {
        vueCompteDeconnecte = new VueCompteDeconnecte();
        vueCompteConnecte = new VueCompteConnecte();
        vuePersonnaliser = new VuePersonnaliser();
        vueClassement = new VueClassement();
        vueRegles = new VueRegles();
        vueSelectionJeu = new VueSelectionJeu();

        setButtonConnecterJoueurCliqueListener(joueurConnecte);
        setButtonCreerJoueurCliqueListener(nouveauJoueurCree);

        creerBindings();
    }

    public Scene getSceneMenuPrincipal() {
        return this.scene;
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    private void styliser() {
        // Général
        scene.getStylesheets().add(Objects.requireNonNull(FactoryFall.class.getResource("css/main.css")).toString());

        // Titre
        logo.setStyle("-fx-padding: 5px;");
        logo.setFitHeight(111);
        logo.setFitWidth(770);
        logo.setPreserveRatio(true);

        VBox.setMargin(logo, new Insets(100, 0, 30, 0));
        VBox.setMargin(compte, new Insets(30, 0, 30, 80));

        // VBox Bouton
        boutons.setAlignment(Pos.TOP_CENTER);
        boutons.setSpacing(20);

        // VBox Compte
        actions.setAlignment(Pos.CENTER);
        actions.setSpacing(300);
        actions.setPadding(new Insets(0, 0, 30, 0));

        // Boutons
        start.setText("Jouer");
        start.setFont(Ressources.getPolice(32));
        start.getStyleClass().add("bouton");

        regles.setText("Règles");
        regles.setFont(Ressources.getPolice(32));
        regles.getStyleClass().add("bouton");

        personnaliser.setText("Personnaliser");
        personnaliser.setFont(Ressources.getPolice(32));
        personnaliser.getStyleClass().add("bouton");


        classement.setText("Classements");
        classement.setFont(Ressources.getPolice(32));
        classement.getStyleClass().add("bouton");

        compte.setText("Compte");
        compte.setFont(Ressources.getPolice(45));
        compte.getStyleClass().add("bouton-clair");

        quitter.setText("Quitter");
        quitter.setFont(Ressources.getPolice(45));
        quitter.getStyleClass().add("bouton-clair");

        this.setResizable(false);
    }


    /**
     * Fonction qui créer tous les bindings in line utile pour l'ensemble des boutons du menu
     */
    private void creerBindings() {
        start.setOnAction(actionEvent -> {
            vueSelectionJeu.mettreAJourFond();
            vueSelectionJeu.afficherScene();
        });
        compte.setOnAction(actionEvent -> {
            vueCompteConnecte.mettreAJour();
            vueCompteDeconnecte.mettreAJour();
            if (Session.getInstance().isConnected()) vueCompteConnecte.afficherScene();
            else vueCompteDeconnecte.afficherScene();
        });
        regles.setOnAction(actionEvent -> {
            vueRegles.mettreAJourFond();
            vueRegles.afficherScene();
        });
        personnaliser.setOnAction(actionEvent -> {
            vuePersonnaliser.mettreAJourFond();
            vuePersonnaliser.afficherScene();
        });
        classement.setOnAction(actionEvent -> {
            vueClassement.mettreAJour();
            vueClassement.afficherScene();
        });
    }

    /**
     * Vérifie si les données rentrées sont valides.
     * Lance la vue demarrer partie apres avoir crée le joueur, et l'avoir connécté
     */
    private final EventHandler<ActionEvent> nouveauJoueurCree = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            AuthPlayer j = PlayerManager.getInstance().getPlayer(getNomJoueur().getText());
            String departement = getDepartement();
            String motDePasse = getMotDePasse().getText();
            String motDePasseConfirmation = getMotDePasseConfirmation().getText();
            if (j != null) {
                vueCompteDeconnecte.afficherErreurConnexion("Ce pseudo est déjà utilisé");
            } else if (departement.equals("Département")) {
                vueCompteDeconnecte.afficherErreurCreation("Veuillez choisir un département");
            } else if (motDePasse.equals("")) {
                vueCompteDeconnecte.afficherErreurCreation("Veuillez entrer un mot de passe");
            } else if (!motDePasse.equals(motDePasseConfirmation)) {
                vueCompteDeconnecte.afficherErreurCreation("Les mots de passe ne correspondent pas");
            } else {
                PlayerManager.getInstance().createPlayer(getNomJoueur().getText(), getMotDePasse().getText(), getDepartement());

                String nomjoueur = getNomJoueur().getText();
                departement = getDepartement();
                Session.getInstance().connect(nomjoueur, departement);
                afficherScene();
            }
        }
    };

    /**
     * Vérifie si les données rentrées sont valides.
     * Lance la vue demarrer partie apres avoir connecté le joueur.
     */
    private final EventHandler<ActionEvent> joueurConnecte = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            AuthPlayer j = PlayerManager.getInstance().getPlayer(getNomJoueur().getText());
            boolean connexionOK = false;

            if (j != null) {
                try {
                    connexionOK = Security.checkPassword(getMotDePasse().getText(), j.getSalt(), j.getHashedPassword());
                } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                    e.printStackTrace();
                }

                if (!connexionOK) {
                    //Si le mot de passe est incorrect (mais le login existe dans la BD)
                    vueCompteDeconnecte.afficherErreurConnexion("L'identifiant ou le mot de passe est incorrect");
                }
            } else {
                //Si l'identifiant est incorrect (aucun joueur de ce login n'est inscrit dans la BD)
                vueCompteDeconnecte.afficherErreurConnexion("L'identifiant ou le mot de passe est incorrect");
            }

            if (connexionOK) {
                String nomjoueur = getNomJoueur().getText();
                String departement = j.getDepartement();
                Session.getInstance().connect(nomjoueur, departement);
                afficherScene();
            }
        }
    };


    /**
     * Getter utile pour la récupération dans TetrisIHM
     */
    public TextField getNomJoueur() {
        return vueCompteDeconnecte.getPseudo();
    }

    /**
     * Getter utile pour la récupération dans TetrisIHM
     */
    public PasswordField getMotDePasse() {
        return vueCompteDeconnecte.getMotDePasse();
    }

    /**
     * Getter utile pour la récupération dans TetrisIHM
     */
    public String getDepartement() {
        return vueCompteDeconnecte.getDepartement();
    }

    /**
     * Getter utile pour la récupération dans TetrisIHM
     */
    public PasswordField getMotDePasseConfirmation() {
        return vueCompteDeconnecte.getMotDePasseConfirmation();
    }

    /**
     * Fonction qui déclenche le lancement du jeu après la connexion d'un utilisateur dans TetrisIHM
     */
    public void setButtonConnecterJoueurCliqueListener(EventHandler<ActionEvent> joueurConnecte) {
        vueCompteDeconnecte.setButtonConnecterJoueurCliqueListener(joueurConnecte);
    }

    /**
     * Fonction qui déclenche le lancement du jeu après la création d'un nouvel utilisateur dans TetrisIHM
     */
    public void setButtonCreerJoueurCliqueListener(EventHandler<ActionEvent> nouveauJoueurCree) {
        vueCompteDeconnecte.setButtonCreerJoueurCliqueListener(nouveauJoueurCree);
    }

    public void setButtonQuitterListener(EventHandler<ActionEvent> quitterAction) {
        quitter.setOnAction(quitterAction);
    }


    @Override
    public void afficherScene() {
        this.setScene(scene);
        root.setBackground(Preferences.getInstance().getBackground());
    }

    public void mettreAJour() {
        root.setBackground(Preferences.getInstance().getBackground());
    }
}
