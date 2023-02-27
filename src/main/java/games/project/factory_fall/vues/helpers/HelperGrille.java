/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package games.project.factory_fall.vues.helpers;

import games.project.factory_fall.FactoryFall;
import games.project.factory_fall.logique.Plateau;
import games.project.factory_fall.parametres.Preferences;
import games.project.factory_fall.vues.VueJeu;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;

import java.util.Objects;

/**
 * Vue de la grille de jeu. Elle est composée de 20 lignes et 10 colonnes.
 * Chaque case de la grille est une ImageView.
 * La grille est affichée dans la classe VueJeu.
 *
 * @see VueJeu
 */
public class HelperGrille extends GridPane {

    protected String dossierImg;
    private final Image vide = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/vide.png")));
    private final Image vide_clair = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/vide_clair.png")));
    private final Image imgS, imgI, imgJ, imgL, imgO, imgT, imgZ;

    private final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    Plateau p;

    public HelperGrille(Plateau p) {
        this.p = p;
        this.dossierImg = Preferences.getInstance().getStylePiece();
        if (Preferences.getInstance().isLocked("pieces")) {
            dossierImg = "conteneur";
        }
        imgS = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + dossierImg + "/S.jpg")));
        imgI = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + dossierImg + "/I.jpg")));
        imgJ = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + dossierImg + "/J.jpg")));
        imgL = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + dossierImg + "/L.jpg")));
        imgO = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + dossierImg + "/O.jpg")));
        imgT = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + dossierImg + "/T.jpg")));
        imgZ = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + dossierImg + "/Z.jpg")));
    }

    public void initialiser() {
        // Effacer le GridPane (effectué à chaque mise à jour)
        this.getChildren().clear();

        // Ajoute les pièces vides dans la grille
        for (int colonne = 0; colonne < p.getPlateau().length; colonne++) {
            for (int ligne = 0; ligne < p.getPlateau()[0].length; ligne++) {
                ImageView imagePiece = getClass() == HelperPieceExterieur.class ? new ImageView() : ligne % 2 == 0 ? new ImageView(vide) : new ImageView(vide_clair);

                imagePiece.setFitHeight((primaryScreenBounds.getHeight() * 0.03));
                imagePiece.setFitWidth((primaryScreenBounds.getHeight() * 0.03));

                this.add(imagePiece, ligne, colonne);
            }
        }
    }

    public void mettreAJour() {
        // Effacer le GridPane (effectué à chaque mise à jour)
        this.getChildren().clear();

        // Ajoute les pièces dans la grille
        for (int colonne = 0; colonne < p.getPlateau().length; colonne++) {
            for (int ligne = 0; ligne < p.getPlateau()[0].length; ligne++) {
                ImageView imagePiece = getClass() == HelperPieceExterieur.class ? new ImageView() : ligne % 2 == 0 ? new ImageView(vide) : new ImageView(vide_clair);

                imagePiece.setFitHeight((primaryScreenBounds.getHeight() * 0.03));
                imagePiece.setFitWidth((primaryScreenBounds.getHeight() * 0.03));

                String nomPiece = p.getPlateau()[colonne][ligne].getNom();
                switch (nomPiece) {
                    case "S" -> imagePiece.setImage(imgS);
                    case "I" -> imagePiece.setImage(imgI);
                    case "J" -> imagePiece.setImage(imgJ);
                    case "L" -> imagePiece.setImage(imgL);
                    case "O" -> imagePiece.setImage(imgO);
                    case "T" -> imagePiece.setImage(imgT);
                    case "Z" -> imagePiece.setImage(imgZ);
                }
                this.add(imagePiece, ligne, colonne);
            }
        }
    }
}
