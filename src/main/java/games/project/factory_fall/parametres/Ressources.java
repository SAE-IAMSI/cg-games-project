package games.project.factory_fall.parametres;

import games.project.factory_fall.FactoryFall;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.util.Objects;

/**
 * À la différence de la classe Preferences, la classe Ressources charge toutes les ressources du jeu et les rend disponibles sous forme d'attributs statiques.
 */
public class Ressources {

    // Pièces affichées dans MenuPersonnalisation
    public static final Image pieceDefault = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/default/L.jpg")));
    public static final Image pieceConteneur = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/conteneur/L.jpg")));
    public static final Image pieceBrique = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/brique/L.jpg")));

    public static Font getPolice(int taille) {
        return Font.loadFont(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("fonts/arcade.ttf")), taille);
    }

    public static Image getPieceDefault() {
        return pieceDefault;
    }

    public static Image getPieceConteneur() {
        return pieceConteneur;
    }

    public static Image getPieceBrique() {
        return pieceBrique;
    }

    public static ImageView getFlecheGauche() {
        return new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/fleches/fleche-gauche.png")), 55, 55, true, true));
    }

    public static ImageView getFlecheDroite() {
        return new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/fleches/fleche-droite.png")), 55, 55, true, true));
    }
}
