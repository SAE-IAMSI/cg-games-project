package games.project.factory_fall.vues.menu;

import games.project.factory_fall.FactoryFall;
import games.project.factory_fall.parametres.Preferences;
import games.project.factory_fall.vues.Menu;
import games.project.factory_fall.vues.VueMenuPrincipal;
import games.project.factory_fall.vues.helpers.BarreNavigation;
import games.project.factory_fall.vues.helpers.PanelPersonnalisation;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class VuePersonnaliser extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    private final VBox personnalisations;

    private final ImageView imageStylePiece, cadenas, speaker;

    private final Button flecheGauche, flecheDroite;

    private final StackPane locked;

    Preferences preferences = Preferences.getInstance();

    /**
     * Évènements déclenchés par chacune des personnalisations
     */
    EventHandler<ActionEvent> stylePieceG = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                preferences.changerImage("-");
                imageStylePiece.setImage(preferences.getImagePiecePreference());
                switchCadenas("pieces");
            });
        }
    };
    EventHandler<ActionEvent> stylePieceD = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                preferences.changerImage("+");
                imageStylePiece.setImage(preferences.getImagePiecePreference());
                switchCadenas("pieces");
            });
        }
    };

    EventHandler<ActionEvent> backgroundG = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                preferences.changerFond("-");
                root.setBackground(preferences.getBackground());
            });
        }
    };
    EventHandler<ActionEvent> backgroundD = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                preferences.changerFond("+");
                root.setBackground(preferences.getBackground());
            });
        }
    };

    EventHandler<ActionEvent> musiqueG = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                Preferences.getInstance().changerMusique();
            });
        }
    };
    EventHandler<ActionEvent> musiqueD = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                Preferences.getInstance().changerMusique();
            });
        }
    };

    public VuePersonnaliser() {
        // Instanciation
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        personnalisations = new VBox();

        flecheGauche = new Button();
        flecheDroite = new Button();

        imageStylePiece = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + preferences.getStylePiece() + "/L.jpg"))));
        cadenas = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("icons/cadenas.png"))));
        speaker = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("icons/speaker.png")), 45, 45, true, true));

        if (Preferences.getInstance().isLocked("pieces")) {
            locked = new StackPane(imageStylePiece, cadenas);
        } else {
            locked = new StackPane(cadenas, imageStylePiece);
        }


        // Styles et bindings
        styliser();

        personnalisations.getChildren().addAll(
                new PanelPersonnalisation("Style des pièces", stylePieceG, locked, stylePieceD),
                new PanelPersonnalisation("Image de fond", backgroundG, new ImageView(), backgroundD),
                new PanelPersonnalisation("Musique de jeu", musiqueG, speaker, musiqueD)
        );

        root.setCenter(personnalisations);
        root.setTop(new BarreNavigation("Personnaliser", VueMenuPrincipal.getInstance().getSceneMenuPrincipal()));

        this.setScene(scene);
    }

    private void styliser() {
        root.setBackground(Preferences.getInstance().getBackground());
        scene.getStylesheets().add(Objects.requireNonNull(FactoryFall.class.getResource("css/main.css")).toString());

        flecheGauche.getStyleClass().add("flecheG");
        flecheDroite.getStyleClass().add("flecheD");

        personnalisations.getStyleClass().add("personnalisations");
        personnalisations.setSpacing(30);
    }

    private void switchCadenas(String typePerso) {
        ObservableList<Node> childs = this.locked.getChildren();
        if (Preferences.getInstance().isLocked(typePerso)) {
            if (!childs.get(childs.size() - 1).equals(cadenas)) {
                if (childs.size() > 1) {
                    Node topNode = childs.get(childs.size() - 1);
                    topNode.toBack();
                }
            }
        } else if (childs.get(childs.size() - 1).equals(cadenas)) {
            if (childs.size() > 1) {
                Node topNode = childs.get(childs.size() - 1);
                topNode.toBack();
            }
        }
    }

    @Override
    public void afficherScene() {
        VueMenuPrincipal.getInstance().setScene(scene);
        mettreAJourFond();
    }

    public void mettreAJourFond() {
        root.setBackground(Preferences.getInstance().getBackground());
    }
}
