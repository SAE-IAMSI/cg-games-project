/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package games.project.factory_fall.vues.menu.compte;

import games.project.factory_fall.FactoryFall;
import games.project.factory_fall.logique.AuthPlayer;
import games.project.factory_fall.parametres.Preferences;
import games.project.factory_fall.parametres.Ressources;
import games.project.factory_fall.stockage.DepartementManager;
import games.project.factory_fall.stockage.PlayerManager;
import games.project.factory_fall.stockage.Security;
import games.project.factory_fall.stockage.Session;
import games.project.factory_fall.vues.Menu;
import games.project.factory_fall.vues.VueMenuPrincipal;
import games.project.factory_fall.vues.helpers.BarreNavigation;
import games.project.factory_fall.vues.helpers.BoiteCombinee;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class VueModificationCompte extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;
    private final VBox pane;
    private final VBox container, informations, motDePasse;
    private final HBox champs, boutons;
    private final Button valider, effacer, supprimer;
    private final Label labelInformations, labelMotDePasse, message;
    private final TextField champPseudo;
    private final PasswordField champMotDePasse, champMotDePasseConfirmation, champMotDePasseModification;
    private final BoiteCombinee champDepartement;

    public VueModificationCompte() {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        labelInformations = new Label("Informations");
        labelMotDePasse = new Label("Changer de mot de passe");
        message = new Label();

        champPseudo = new TextField(Session.getInstance().getLogin());
        champDepartement = new BoiteCombinee(DepartementManager.getInstance().getAll(), "Département", 10);
        champDepartement.setValue(Session.getInstance().getDepartement());
        champMotDePasse = new PasswordField();
        champMotDePasseConfirmation = new PasswordField();
        champMotDePasseModification = new PasswordField();

        valider = new Button("Valider");
        effacer = new Button("Effacer préférences");
        supprimer = new Button("Supprimer le compte");

        informations = new VBox(labelInformations, champPseudo, champDepartement, champMotDePasseModification);
        motDePasse = new VBox(labelMotDePasse, champMotDePasse, champMotDePasseConfirmation);
        champs = new HBox(informations, motDePasse);

        boutons = new HBox(valider, effacer, supprimer);

        container = new VBox(champs, message, boutons);

        pane = new VBox(container);

        // Styles et bindings
        styliser();
        creerBindings();

        root.setTop(new BarreNavigation("Modification", VueMenuPrincipal.getInstance().getSceneMenuPrincipal()));
        root.setCenter(pane);

        this.setScene(scene);
    }

    private void styliser() {
        root.setBackground(Preferences.getInstance().getBackground());

        scene.getStylesheets().add(Objects.requireNonNull(FactoryFall.class.getResource("css/main.css")).toString());

        pane.setPadding(new Insets(50, 50, 50, 50));
        pane.setAlignment(Pos.CENTER);

        container.setSpacing(10);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.CENTER);
        container.getStyleClass().add("panel-modification-compte");

        champs.setSpacing(30);
        champs.setPadding(new Insets(10));
        champs.setAlignment(Pos.CENTER);

        boutons.setSpacing(10);
        boutons.setPadding(new Insets(10));
        boutons.setAlignment(Pos.CENTER);

        informations.setSpacing(30);
        informations.setPadding(new Insets(10));
        informations.setAlignment(Pos.CENTER);

        motDePasse.setSpacing(30);
        motDePasse.setPadding(new Insets(10));
        motDePasse.setAlignment(Pos.CENTER);

        champPseudo.setPrefWidth(300);
        champMotDePasseModification.setPrefWidth(300);
        champDepartement.setPrefWidth(300);
        champPseudo.setPromptText("Pseudo");
        champMotDePasseModification.setPromptText("Mot de passe");
        champMotDePasse.setPromptText("Mot de passe actuel");

        champMotDePasse.setPrefWidth(300);
        champMotDePasseConfirmation.setPrefWidth(300);
        champMotDePasse.setPromptText("Nouveau mot de passe");
        champMotDePasseConfirmation.setPromptText("Confirmation nouveau mot de passe");

        valider.setPrefWidth(300);
        effacer.setPrefWidth(300);
        supprimer.setPrefWidth(300);

        labelInformations.setFont(Ressources.getPolice(20));
        labelMotDePasse.setFont(Ressources.getPolice(20));
        labelInformations.getStyleClass().add("titre");
        labelMotDePasse.getStyleClass().add("titre");

        message.setVisible(false);
        message.setFont(Ressources.getPolice(20));

        champPseudo.setFont(Ressources.getPolice(20));
        champMotDePasse.setFont(Ressources.getPolice(20));
        champMotDePasseConfirmation.setFont(Ressources.getPolice(20));
        champMotDePasseModification.setFont(Ressources.getPolice(20));

        champPseudo.getStyleClass().add("champ");
        champMotDePasse.getStyleClass().add("champ");
        champMotDePasseConfirmation.getStyleClass().add("champ");
        champMotDePasseModification.getStyleClass().add("champ");

        valider.setFont(Ressources.getPolice(20));
        effacer.setFont(Ressources.getPolice(20));
        supprimer.setFont(Ressources.getPolice(20));

        valider.getStyleClass().add("bouton");
        effacer.getStyleClass().add("bouton-rouge");
        supprimer.getStyleClass().add("bouton-rouge");

    }

    private void creerBindings() {
        valider.setOnAction(action -> {
            String login = champPseudo.getText();
            String departement = champDepartement.getValue().toString().split(" ")[0];
            String motDePasseActuel = champMotDePasseModification.getText();
            String motDePasse = champMotDePasse.getText();
            String motDePasseConfirmation = champMotDePasseConfirmation.getText();

            AuthPlayer j = PlayerManager.getInstance().getPlayer(login);
            if (j != null) {
                boolean mdpOK = false;

                try {
                    mdpOK = Security.checkPassword(motDePasseActuel, j.getSalt(), j.getHashedPassword());
                } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                    e.printStackTrace();
                }

                if (login.isEmpty() || departement.isEmpty() || motDePasseActuel.isEmpty()) {
                    afficherMessage("Veuillez remplir tous les champs", Color.RED);
                } else if (!motDePasse.isEmpty() && !motDePasseConfirmation.isEmpty()) {
                    if (!motDePasse.equals(motDePasseConfirmation)) {
                        afficherMessage("Les mots de passe ne correspondent pas", Color.RED);
                    } else {
                        if (mdpOK) {
                            PlayerManager.getInstance().updatePlayer(login, departement, motDePasse);
                            afficherMessage("Modification effectuée", Color.GREEN);
                        } else {
                            afficherMessage("Mot de passe actuel incorrect", Color.RED);
                        }
                    }
                } else {
                    if (mdpOK) {
                        PlayerManager.getInstance().updatePlayer(login, departement, motDePasseActuel);
                        afficherMessage("Modification effectuée", Color.GREEN);
                    } else {
                        afficherMessage("Mot de passe actuel incorrect", Color.RED);
                    }
                }
            }
        });

        effacer.setOnAction(action -> {
            Preferences.getInstance().reinitialiser();
            mettreAJour();
        });

        supprimer.setOnAction(action -> {
            AuthPlayer j = PlayerManager.getInstance().getPlayer(Session.getInstance().getLogin());
            try {
                if (!Security.checkPassword(champMotDePasseModification.getText(), j.getSalt(), j.getHashedPassword())) {
                    afficherMessage("Entrez votre mot de passe avant de supprimer votre compte", Color.RED);
                } else {
                    PlayerManager.getInstance().deletePlayer(Session.getInstance().getLogin());
                    Session.getInstance().disconnect();
                    afficherMessage("Compte supprimé", Color.GREEN);
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                afficherMessage("Une erreur est survenue.", Color.ORANGE);
                System.out.println("Une erreur est survenue.");
            }
        });
    }

    private void afficherMessage(String message, Color couleur) {
        this.message.setText(message);
        this.message.setTextFill(couleur);
        this.message.setVisible(true);
    }

    @Override
    public void afficherScene() {
        VueMenuPrincipal.getInstance().setScene(scene);
        mettreAJour();
    }

    public void mettreAJour() {
        root.setBackground(Preferences.getInstance().getBackground());
    }
}
