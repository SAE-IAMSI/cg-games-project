package games.project.factory_fall.vues;

import games.project.factory_fall.FactoryFall;
import games.project.factory_fall.parametres.Ressources;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Vue des contrôles du jeu.
 */
public class VueControles extends VBox {

    private final Label titre;

    private final ArrayList<HBox> hBoxes;
    private final ArrayList<ImageView> imageViews;

    public VueControles() {
        titre = new Label("Contrôles :");

        ImageView flechesDirectionnelles = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("icons/flechesDirectionnelles.png"))));
        ImageView espace = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("icons/espace.png"))));
        ImageView toucheR = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("icons/toucheR.png"))));
        ImageView toucheC = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("icons/toucheC.png"))));
        ImageView toucheP = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("icons/toucheP.png"))));
        ImageView haut = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("icons/haut.png"))));
        ImageView echap = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("icons/echap.png"))));

        Label flechesDirectionnellesLabel = new Label("Déplacer la pièce");
        Label espaceLabel = new Label("Descendre la pièce");
        Label toucheRLabel = new Label("Rotation anti-horaire");
        Label toucheCLabel = new Label("Sauvegarder la pièce");
        Label touchePLabel = new Label("Pause");
        Label hautLabel = new Label("Rotation horaire");
        Label echapLabel = new Label("Quitter le jeu");

        HBox flechesDirectionnellesHBox = new HBox(flechesDirectionnelles, flechesDirectionnellesLabel);
        HBox espaceHBox = new HBox(espace, espaceLabel);
        HBox toucheRHBox = new HBox(toucheR, toucheRLabel);
        HBox toucheCHBox = new HBox(toucheC, toucheCLabel);
        HBox touchePHBox = new HBox(toucheP, touchePLabel);
        HBox hautHBox = new HBox(haut, hautLabel);
        HBox echapHBox = new HBox(echap, echapLabel);

        hBoxes = new ArrayList<>();
        hBoxes.addAll(List.of(flechesDirectionnellesHBox, espaceHBox, touchePHBox, toucheRHBox, toucheCHBox, hautHBox, echapHBox));

        imageViews = new ArrayList<>();
        imageViews.addAll(List.of(flechesDirectionnelles, espace, toucheR, toucheC, toucheP, haut, echap));

        styliser();

        this.getChildren().addAll(titre, flechesDirectionnellesHBox, espaceHBox, toucheRHBox, toucheCHBox, touchePHBox, hautHBox, echapHBox);
    }

    private void styliser() {
        // VBox
        this.setAlignment(Pos.BOTTOM_LEFT);
        this.setBackground(Background.EMPTY);

        // Titre
        titre.setStyle("-fx-font-weight: bold; -fx-text-fill: #ffffff;");
        titre.setFont(Ressources.getPolice(30));
        titre.setTextAlignment(TextAlignment.LEFT);

        // HBoxes
        for (HBox hBox : hBoxes) {
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setSpacing(5);
            hBox.setPrefHeight(75.0);
            hBox.setBackground(Background.EMPTY);

            HBox.setHgrow(hBox.getChildren().get(0), Priority.ALWAYS);

            hBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff;");
            ((Label) hBox.getChildren().get(1)).setFont(Ressources.getPolice(16));
            ((Label) hBox.getChildren().get(1)).setAlignment(Pos.CENTER_RIGHT);
            ((Label) hBox.getChildren().get(1)).setWrapText(true);
        }

        // ImageViews
        for (ImageView imageView : imageViews) {
            imageView.setFitHeight(40.0);
            imageView.setFitWidth(190.0);
            imageView.setPreserveRatio(true);
        }
    }
}