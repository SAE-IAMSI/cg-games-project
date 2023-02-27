package games.project.factory_fall.vues.menu.compte;

import games.project.factory_fall.FactoryFall;
import games.project.factory_fall.parametres.Preferences;
import games.project.factory_fall.parametres.Ressources;
import games.project.factory_fall.stockage.DepartementManager;
import games.project.factory_fall.vues.Menu;
import games.project.factory_fall.vues.VueMenuPrincipal;
import games.project.factory_fall.vues.helpers.BarreNavigation;
import games.project.factory_fall.vues.helpers.BoiteCombinee;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class VueCompteDeconnecte extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    private final HBox option;
    private final VBox champsConnexion, champsCreation;

    private final Label titreConnexion, titreCreation, erreurConnexion, erreurCreation;
    private final TextField pseudoConnexion, pseudoCreation;
    private final BoiteCombinee departementCreation;
    private final PasswordField motDePasseConnexion, motDePasseCreation, motDePasseCreationConfirmation;
    private final Button boutonConnexion, boutonCreation, cgu;

    public VueCompteDeconnecte() {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        option = new HBox();


        champsConnexion = new VBox();
        champsCreation = new VBox();

        boutonConnexion = new Button();
        boutonCreation = new Button();
        cgu = new Button();

        pseudoConnexion = new TextField();
        pseudoCreation = new TextField();

        departementCreation = new BoiteCombinee(DepartementManager.getInstance().getAll(), "Département", 10);

        motDePasseConnexion = new PasswordField();
        motDePasseCreation = new PasswordField();
        motDePasseCreationConfirmation = new PasswordField();

        titreConnexion = new Label("Se connecter");
        titreCreation = new Label("S'inscrire");
        erreurConnexion = new Label("L'identifiant ou le mot de passe est incorrect");
        erreurCreation = new Label();

        champsConnexion.getChildren().addAll(titreConnexion, new VBox(pseudoConnexion, motDePasseConnexion), new VBox(erreurConnexion, boutonConnexion));
        champsCreation.getChildren().addAll(titreCreation, new VBox(pseudoCreation, departementCreation, motDePasseCreation, motDePasseCreationConfirmation), cgu, new VBox(erreurCreation, boutonCreation));

        option.getChildren().addAll(champsConnexion, champsCreation);

        // Styles et bindings
        styliser();
        setDisable();
        creerBindings();

        root.setCenter(option);
        root.setTop(new BarreNavigation("Compte", VueMenuPrincipal.getInstance().getSceneMenuPrincipal()));

        this.setScene(scene);
    }

    public void styliser() {
        // Root (BorderPane)
        root.setBackground(Preferences.getInstance().getBackground());

        // Scene
        scene.getStylesheets().add(Objects.requireNonNull(FactoryFall.class.getResource("css/main.css")).toString());

        // Options (connexion ou création)
        option.getStyleClass().add("option");
        option.setPadding(new Insets(30, 30, 70, 30));

        // Box de connexion/création
        champsConnexion.getStyleClass().add("panel-compte-deconnecte");
        champsCreation.getStyleClass().add("panel-compte-deconnecte");
        champsConnexion.setPrefWidth(scene.getWidth() * 0.4);
        champsCreation.setPrefWidth(scene.getWidth() * 0.4);
        champsConnexion.setSpacing(30);
        champsCreation.setSpacing(30);

        // Titre
        titreConnexion.getStyleClass().add("titre-compte-deconnecte");
        titreCreation.getStyleClass().add("titre-compte-deconnecte");
        titreConnexion.setFont(Ressources.getPolice(32));
        titreCreation.setFont(Ressources.getPolice(32));
        titreConnexion.setAlignment(Pos.CENTER);
        titreCreation.setAlignment(Pos.CENTER);
        titreConnexion.setPrefWidth(champsConnexion.getPrefWidth());
        titreCreation.setPrefWidth(champsCreation.getPrefWidth());
        titreConnexion.setPadding(new Insets(10, 0, 20, 0));
        titreCreation.setPadding(new Insets(10, 0, 20, 0));

        // Champs pseudo
        pseudoConnexion.setPromptText("Pseudo");
        pseudoCreation.setPromptText("Pseudo");

        // Champs mot de passe
        motDePasseConnexion.setPromptText("Mot de passe");
        motDePasseCreation.setPromptText("Mot de passe");
        motDePasseCreationConfirmation.setPromptText("Confirmation du mot de passe");

        // Boutons
        boutonConnexion.setText("Connexion");
        boutonConnexion.getStyleClass().add("bouton");
        boutonCreation.setText("S'inscrire");
        boutonCreation.getStyleClass().add("bouton");
        boutonConnexion.setAlignment(Pos.CENTER);
        boutonCreation.setAlignment(Pos.CENTER);
        boutonConnexion.setPrefWidth(champsConnexion.getPrefWidth());
        boutonCreation.setPrefWidth(champsCreation.getPrefWidth());
        boutonConnexion.setFont(Ressources.getPolice(20));
        boutonCreation.setFont(Ressources.getPolice(20));
        cgu.setText("En vous inscrivant, vous acceptez nos CGU.");
        cgu.setStyle("-fx-underline: true;");
        cgu.getStyleClass().add("bouton-blanc-fond-noir");
        cgu.setFont(Ressources.getPolice(15));
        cgu.setAlignment(Pos.CENTER);

        styliserChamps(champsConnexion);
        styliserChamps(champsCreation);
        champsConnexion.setMinHeight(champsConnexion.getPrefHeight());
        champsCreation.setMinHeight(champsCreation.getPrefHeight());

        erreurConnexion.setVisible(false);
        erreurCreation.setVisible(false);
        erreurCreation.setStyle("-fx-text-fill: red");
        erreurConnexion.setStyle("-fx-text-fill: red");
        erreurConnexion.setFont(Ressources.getPolice(16));
        erreurConnexion.setAlignment(Pos.CENTER);
        erreurCreation.setFont(Ressources.getPolice(16));
        erreurCreation.setAlignment(Pos.CENTER);

        ((VBox) champsConnexion.getChildren().get(2)).setAlignment(Pos.CENTER);
        ((VBox) champsCreation.getChildren().get(3)).setAlignment(Pos.CENTER);
    }

    /**
     * Pré-requis : l'enfant n°1 de la VBox champs doit contenir une VBox avec les TextField
     *
     * @param champs VBox
     */
    private void styliserChamps(VBox champs) {
        ((VBox) champs.getChildren().get(1)).setSpacing(30);
        ((VBox) champs.getChildren().get(1)).setAlignment(Pos.CENTER);
        for (Node enfant : ((VBox) champs.getChildren().get(1)).getChildren()) {
            enfant.getStyleClass().add("champ");
            if (enfant instanceof TextField) {
                ((TextField) enfant).setAlignment(Pos.CENTER);
                ((TextField) enfant).setPrefWidth(champsCreation.getPrefWidth() * 0.75);
                ((TextField) enfant).setFont(Ressources.getPolice(20));
            } else if (enfant instanceof BoiteCombinee) {
                ((BoiteCombinee) enfant).setPrefWidth(champsCreation.getPrefWidth() * 0.75);

            }
        }
    }

    /**
     * Empêche l'utilisateur de renseigner des champs de connexion s'il a déjà écrit dans des champs d'inscription, et vice-versa.
     */
    public void setDisable() {
        champsConnexionLock(pseudoConnexion, motDePasseConnexion);
        champsConnexionLock(motDePasseConnexion, pseudoConnexion);
        champsCreationLock(pseudoCreation, motDePasseCreation, motDePasseCreationConfirmation);
        champsCreationLock(motDePasseCreation, pseudoCreation, motDePasseCreationConfirmation);
        champsCreationLock(motDePasseCreationConfirmation, pseudoCreation, motDePasseCreation);
        champsCreationLockdepartement(departementCreation);

    }

    /**
     * Empêche l'utilisateur de renseigner des champs de connexion s'il a déjà écrit dans des champs d'inscription
     *
     * @param pseudoCreation                 pseudo de l'utilisateur
     * @param motDePasseCreation             mot de passe de l'utilisateur
     * @param motDePasseCreationConfirmation le département de l'utilisateur
     */
    private void champsCreationLock(TextField pseudoCreation, TextField motDePasseCreation, TextField motDePasseCreationConfirmation) {
        pseudoCreation.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                pseudoConnexion.setDisable(true);
                motDePasseConnexion.setDisable(true);
                boutonConnexion.setDisable(true);
            } else if (motDePasseCreation.getText().isEmpty() && motDePasseCreationConfirmation.getText().isEmpty()) {
                pseudoConnexion.setDisable(false);
                motDePasseConnexion.setDisable(false);
                boutonConnexion.setDisable(false);
            }
        });
    }

    private void champsCreationLockdepartement(BoiteCombinee departementCreationtest) {
        departementCreationtest.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != departementCreation.getItems().get(0) || departementCreation.estVide()) {
                pseudoConnexion.setDisable(true);
                motDePasseConnexion.setDisable(true);

            } else {
                pseudoConnexion.setDisable(false);
                motDePasseConnexion.setDisable(false);
            }
        });

    }

    /**
     * Empêche l'utilisateur de renseigner des champs d'inscription s'il a déjà écrit dans des champs de connexion
     *
     * @param pseudoConnexion     pseudo de l'utilisateur
     * @param motDePasseConnexion mot de passe de l'utilisateur
     */
    private void champsConnexionLock(TextField pseudoConnexion, TextField motDePasseConnexion) {
        pseudoConnexion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                pseudoCreation.setDisable(true);
                departementCreation.setDisable(true);
                motDePasseCreation.setDisable(true);
                motDePasseCreationConfirmation.setDisable(true);
                boutonCreation.setDisable(true);
            } else if (motDePasseConnexion.getText().isEmpty()) {
                pseudoCreation.setDisable(false);
                departementCreation.setDisable(false);
                motDePasseCreation.setDisable(false);
                motDePasseCreationConfirmation.setDisable(false);
                boutonCreation.setDisable(false);
            }
        });
    }

    /**
     * Fonction qui déclenche le lancement du jeu après la connexion d'un utilisateur dans TetrisIHM
     */
    public void setButtonConnecterJoueurCliqueListener(EventHandler<ActionEvent> joueurConnecte) {
        boutonConnexion.setOnAction(joueurConnecte);
    }

    /**
     * Fonction qui déclenche le lancement du jeu après la création d'un utilisateur dans TetrisIHM
     */
    public void setButtonCreerJoueurCliqueListener(EventHandler<ActionEvent> joueurCree) {
        boutonCreation.setOnAction(joueurCree);
    }

    public void creerBindings() {
        cgu.setOnAction(event -> {
                 try {
                     java.awt.Desktop.getDesktop().open(new File("documents/CGU.pdf"));
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
        });
    }

    /**
     * Récupère le pseudo de l'utilisateur
     *
     * @return pseudo de l'utilisateur
     */
    public TextField getPseudo() {
        return pseudoConnexion.isDisabled() ? pseudoCreation : pseudoConnexion;
    }

    /**
     * Récupère le mot de passe de l'utilisateur
     *
     * @return mot de passe de l'utilisateur
     */
    public PasswordField getMotDePasse() {
        return motDePasseConnexion.isDisabled() ? motDePasseCreation : motDePasseConnexion;
    }

    public PasswordField getMotDePasseConfirmation() {
        return motDePasseCreationConfirmation;
    }

    /**
     * Récupère le département de l'utilisateur
     *
     * @return departement de l'utilisateur
     */
    public String getDepartement() {
        return departementCreation.getValue().toString().split(" ")[0];
    }

    public void afficherErreurConnexion(String message) {
        ((Label) ((VBox) champsConnexion.getChildren().get(2)).getChildren().get(0)).setText(message);
        ((VBox) champsConnexion.getChildren().get(2)).getChildren().get(0).setVisible(true);
    }

    public void afficherErreurCreation(String message) {
        ((Label) ((VBox) champsCreation.getChildren().get(3)).getChildren().get(0)).setText(message);
        ((VBox) champsCreation.getChildren().get(3)).getChildren().get(0).setVisible(true);
    }

    @Override
    public void afficherScene() {
        VueMenuPrincipal.getInstance().setScene(scene);
        mettreAJour();
    }

    public void mettreAJour() {
        pseudoCreation.clear();
        motDePasseCreation.clear();
        motDePasseCreationConfirmation.clear();
        pseudoConnexion.clear();
        motDePasseConnexion.clear();
        root.setBackground(Preferences.getInstance().getBackground());
    }
}
