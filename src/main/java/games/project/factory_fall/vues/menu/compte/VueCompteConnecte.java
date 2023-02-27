package games.project.factory_fall.vues.menu.compte;

import games.project.factory_fall.FactoryFall;
import games.project.factory_fall.logique.Score;
import games.project.factory_fall.parametres.Preferences;
import games.project.factory_fall.parametres.Ressources;
import games.project.factory_fall.stockage.ScoreManager;
import games.project.factory_fall.stockage.Session;
import games.project.factory_fall.vues.Menu;
import games.project.factory_fall.vues.VueMenuPrincipal;
import games.project.factory_fall.vues.helpers.BarreNavigation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class VueCompteConnecte extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;
    private final GridPane classement;
    private final VBox vbScores, container;
    private final HBox information, actions;
    private final Button modifier, deconnecter;
    private final Label pseudo, departement;

    VueModificationCompte vueModificationCompte = new VueModificationCompte();

    public VueCompteConnecte() {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        pseudo = new Label("Anonyme");

        modifier = new Button("Modifier le compte");
        deconnecter = new Button();

        departement = new Label("Département : ");

        if (Session.getInstance().isConnected()) {
            pseudo.setText(Session.getInstance().getLogin());
            departement.setText("Département : " + Session.getInstance().getDepartement());
        }

        classement = new GridPane();

        information = new HBox(pseudo, departement);
        actions = new HBox(modifier, deconnecter);

        vbScores = new VBox(information, classement, actions);

        container = new VBox(vbScores);

        recupererClassement();

        // Styles et bindings
        styliser();
        creerBindings();

        // Affichage
        root.setTop(new BarreNavigation("Compte", VueMenuPrincipal.getInstance().getSceneMenuPrincipal()));
        root.setCenter(container);

        this.setScene(scene);
    }

    protected void styliser() {
        // Root (BorderPane)
        root.setBackground(Preferences.getInstance().getBackground());

        //nomjoueur
        pseudo.setFont(Ressources.getPolice(45));
        pseudo.setStyle("-fx-text-fill: white;");

        //departementjoueur
        departement.setFont(Ressources.getPolice(25));
        departement.setStyle("-fx-text-fill: lightgray;");
        departement.setPadding(new Insets(10, 0, 0, 0));

        // Hbox des boutons
        actions.setAlignment(Pos.CENTER);
        actions.setSpacing(50);
        actions.setPadding(new Insets(10, 10, 10, 10));

        //bouton
        modifier.setText("Modifier votre profil");
        modifier.setFont(Ressources.getPolice(20));
        modifier.getStyleClass().add("bouton");
        modifier.setPrefWidth(500);

        deconnecter.setText("Déconnexion");
        deconnecter.setFont(Ressources.getPolice(20));
        deconnecter.getStyleClass().add("bouton-rouge");
        deconnecter.setPrefWidth(200);

        //Classement
        classement.setHgap(20);
        classement.setVgap(20);
        classement.setAlignment(Pos.TOP_CENTER);
        classement.setGridLinesVisible(true);

        for (int i = 0; i < classement.getChildren().size(); i++) {
            classement.getChildren().get(i).setStyle("-fx-text-fill: white");
        }

        // Scene
        scene.getStylesheets().add(Objects.requireNonNull(FactoryFall.class.getResource("css/main.css")).toString());

        // Classement (VBox)
        vbScores.getStyleClass().add("classement");
        vbScores.setAlignment(Pos.TOP_CENTER);
        vbScores.setPadding(new Insets(30));
        vbScores.setSpacing(30);

        //information HBox
        information.setSpacing(20);
        information.setPadding(new Insets(0, 0, 10, 50));

        //container
        container.setSpacing(10);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.CENTER);
        container.getStyleClass().add("panel-compte");

    }

    public void creerBindings() {
        modifier.setOnAction(event -> {
            vueModificationCompte.mettreAJour();
            vueModificationCompte.afficherScene();
        });
        deconnecter.setOnAction(event -> {
            Session.getInstance().disconnect();
            deconnecter.setText("Déconnecté");
            deconnecter.setStyle("-fx-text-fill: green; -fx-border-color: green;");
            modifier.setDisable(true);
            modifier.setVisible(false);
        });
    }

    /**
     * Récupère le classement depuis la base de données.
     */
    protected void recupererClassement() {
        if (Session.getInstance().isConnected()) {
            List<Score> scores = ScoreManager.getInstance().getTopScoreParLogin(Session.getInstance().getLogin());
            if (scores.isEmpty()) {
                Label erreur = new Label("Aucun score enregistré");
                erreur.setFont(Ressources.getPolice(25));
                classement.add(erreur, 0, 0);

            } else {

                Label score = new Label("Score");
                Label date = new Label("Date");
                Label heure = new Label("Heure");

                score.setFont(Ressources.getPolice(25));
                date.setFont(Ressources.getPolice(25));

                heure.setFont(Ressources.getPolice(25));
                classement.add(score, 0, 0);
                classement.add(date, 1, 0);
                classement.add(heure, 2, 0);
                for (int indice = 1; indice < 11 && indice <= scores.size(); indice++) {

                    Label Score = new Label(String.valueOf(scores.get(indice - 1).getScore()));
                    Label Date = new Label(new SimpleDateFormat("dd/MM/yyyy").format(scores.get(indice - 1).getHorodatage()));
                    Label Heure = new Label(new SimpleDateFormat("HH:mm").format(scores.get(indice - 1).getHorodatage()));

                    Score.setFont(Ressources.getPolice(20));
                    Date.setFont(Ressources.getPolice(20));
                    Heure.setFont(Ressources.getPolice(20));
                    classement.add(Score, 0, indice);
                    classement.add(Date, 1, indice);
                    classement.add(Heure, 2, indice);
                }
            }
        }
    }

    @Override
    public void afficherScene() {
        VueMenuPrincipal.getInstance().setScene(scene);
        mettreAJour();
    }

    public void mettreAJour() {
        root.setBackground(Preferences.getInstance().getBackground());
        classement.getChildren().clear();
        recupererClassement();
        pseudo.setText(Session.getInstance().getLogin());
        departement.setText("Département : " + Session.getInstance().getDepartement());
        if (Session.getInstance().isConnected()) {
            modifier.setDisable(false);
            modifier.setVisible(true);
            deconnecter.setText("Déconnexion");
            deconnecter.setStyle("-fx-text-fill: red; -fx-border-color: red;");
        }
    }
}
