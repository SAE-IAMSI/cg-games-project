package games.project.factory_fall.vues;

import games.project.factory_fall.parametres.Musique;
import games.project.factory_fall.parametres.Preferences;
import games.project.factory_fall.parametres.Ressources;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Vue de fin de partie. Possibilité de rejouer ou de revenir au menu principal.
 * Affiche le score atteint.
 */
public class VueGameOver extends Stage implements Menu {

    private final Scene scene;
    private final BorderPane root;
    private final VBox vBox;
    private final HBox actions;

    protected BooleanProperty arreterJeu, retry;

    private final Label gameOver, score;
    private final Button accueil, rejouer;

    public VueGameOver(int scoreJoueur) {
        // Instanciation d'éléments JavaFX
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        vBox = new VBox();
        actions = new HBox();

        // Instanciation d'attributs de la logique du jeu
        arreterJeu = new SimpleBooleanProperty();
        retry = new SimpleBooleanProperty();

        // Instanciation d'objets graphiques JavaFX
        gameOver = new Label("Game Over!");
        score = new Label("Score : " + scoreJoueur);
        rejouer = new Button("Rejouer");
        accueil = new Button("Accueil");

        arreterJeu.setValue(false);
        retry.setValue(false);

        // Ajout dans les vues appropriées les éléments
        actions.getChildren().addAll(rejouer, accueil);
        vBox.getChildren().addAll(gameOver, score, actions);
        root.setCenter(vBox);

        // Styles définitifs
        styliser();
        creerBindings();

        this.setScene(scene);
    }

    private void arreterJeu() {
        Musique.stopMusicGame();
        Musique.playMusicMainMenu();
        arreterJeu.setValue(true);
        close();
    }

    private void relancerPartie() {
        retry.setValue(true);
        close();
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    private void styliser() {
        mettreAJourFond();

        this.setResizable(false);

        vBox.setAlignment(Pos.CENTER);

        actions.setPadding(new Insets(200, 0, 0, 0));
        actions.setAlignment(Pos.CENTER);
        actions.setSpacing(20);

        gameOver.setFont(Ressources.getPolice(100));
        score.setFont(Ressources.getPolice(40));

        gameOver.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #000000; -fx-padding: 15px 30px");
        score.setStyle("-fx-border-color: #ffffff; -fx-border-width: 2px; -fx-text-fill: #ffffff; -fx-background-color: #000000; -fx-padding: 10px");
        score.setTranslateY(-15);

        for (Node action : actions.getChildren()) {
            action.setStyle("-fx-border-color: #000000; -fx-border-width: 2px; -fx-text-fill: #000000; -fx-background-color: #ffffff; -fx-padding: 10px");
            ((Button) action).setFont(Ressources.getPolice(50));
            ((Button) action).setPadding(new Insets(10, 10, 10, 10));
        }
    }

    /**
     * Créé tous les listeners/bindings afin que la partie se déroule correctement.
     */
    private void creerBindings() {
        accueil.setOnAction(actionEvent -> arreterJeu());
        rejouer.setOnAction(actionEvent -> relancerPartie());
    }

    public BooleanProperty arreterJeuProperty() {
        return arreterJeu;
    }

    public BooleanProperty retryProperty() {
        return retry;
    }

    @Override
    public void afficherScene() {
        VueMenuPrincipal.getInstance().setScene(scene);
    }

    private void mettreAJourFond() {
        root.setBackground(Preferences.getInstance().getBackground());
    }
}
