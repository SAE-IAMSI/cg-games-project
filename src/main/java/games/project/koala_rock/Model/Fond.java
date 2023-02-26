/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package games.project.koala_rock.Model;

import games.project.koala_rock.RessourcesAccess;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Fond extends Group {
    private static String choixFond = "Défaut";
    private static Fond instance = null;

    public Fond() {
    }

    public static Fond getInstance() {
        if (instance == null) {
            instance = new Fond();
        }
        return instance;
    }

    public static String getNomChoixFond() {
        return choixFond;
    }

    public static void setChoixFond(String choixFond) {
        Rectangle fond = new Rectangle(1280, 720);
        fond.setFill(setChoixFond_Img(choixFond));
        Fond.getInstance().getChildren().add(fond);
        Fond.choixFond = choixFond;
    }

    public static Node getChoixFond() {
        if (Fond.getInstance().getChildren().isEmpty()) {
            Fond.setChoixFond(Fond.getNomChoixFond());
        }
        return Fond.getInstance().getChildren().get(0);

    }

    /**
     * Methode qui permet de choisir l'image de l'echelle
     *
     * @param choixFond
     * @return
     */
    public static Paint setChoixFond_Img(String choixFond) {
        return switch (choixFond) {
            case "Défaut" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("fond/star.jpeg"))));
            case "Nuit" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("fond/night.jpeg"))));
            case "NuitBis" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("fond/night2.jpeg"))));
            case "Ville" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("fond/city.jpeg"))));
            case "Foret" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("fond/forest.jpeg"))));
            case "Star" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("fond/star2.jpeg"))));
            default -> null;
        };
    }
}
