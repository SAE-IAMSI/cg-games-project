package games.project.factory_fall.vues.helpers;

import games.project.factory_fall.FactoryFall;
import games.project.factory_fall.parametres.Ressources;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * Le PanelPersonnalisation est un Helper utilisé dans la VuePersonnaliser. Son objectif est de fournir un "panel" au style commun.
 * Il contient un titre, deux actions EventHandler et une ImageView d'apercu.
 * <p></p>
 * Pour correctement l'utiliser, il ne faut l'employer uniquement dans la classe VuePersonnaliser. Toute autre utilisation ne sera pas admise.
 * Référez-vous à la documentation de cette classe pour en savoir plus.
 */
public class PanelPersonnalisation extends VBox {

    /**
     * Titre du panel de personnalisation
     */
    private final Label titre;

    /**
     * Boîte contenant les flèches et l'apercu de la personnalisation susnommés
     */
    private final HBox personnalisation;

    /**
     * Flèches, contiennent les actions passées au constructeur.
     */
    private final Button gauche, droite;

    /**
     * Constructeur d'un panel de personnalisation
     *
     * @param titre                  Titre du panel (ex. "Style des pièces")
     * @param actionGauche           Action déclenchée par la flèche gauche
     * @param actionDroite           Action déclenchée par la flèche droite
     * @param personnalisationApercu Image d'apercu de la personnalisation
     */
    public PanelPersonnalisation(String titre, EventHandler<ActionEvent> actionGauche, Node personnalisationApercu, EventHandler<ActionEvent> actionDroite) {
        this.titre = new Label(titre);
        this.gauche = new Button();
        this.droite = new Button();

        this.personnalisation = new HBox(gauche, personnalisationApercu, droite);

        // Styles et affectations des actions
        styliser();
        creerBindings(actionGauche, actionDroite);

        // Ajout des éléments
        this.getChildren().addAll(this.titre, this.personnalisation);
    }

    private void styliser() {
        this.getStylesheets().add(Objects.requireNonNull(FactoryFall.class.getResource("css/main.css")).toString());
        this.getStyleClass().add("panel-personnalisation");
        this.setAlignment(Pos.CENTER);

        titre.getStyleClass().add("titre-panel-personnalisation");
        titre.setFont(Ressources.getPolice(28));

        // Flèches
        gauche.getStyleClass().add("gauche");
        droite.getStyleClass().add("droite");
        gauche.setGraphic(Ressources.getFlecheGauche());
        droite.setGraphic(Ressources.getFlecheDroite());
        gauche.setAlignment(Pos.CENTER);
        droite.setAlignment(Pos.CENTER);

        // Élément central
        //((ImageView) personnalisation.getChildren().get(1)).setFitHeight(55);
        //((ImageView) personnalisation.getChildren().get(1)).setFitWidth(55);

        // Personnalisation (flèches comprises)
        personnalisation.setAlignment(Pos.CENTER);
    }

    private void creerBindings(EventHandler<ActionEvent> actionGauche, EventHandler<ActionEvent> actionDroite) {
        gauche.setOnAction(actionGauche);
        droite.setOnAction(actionDroite);
    }
}
