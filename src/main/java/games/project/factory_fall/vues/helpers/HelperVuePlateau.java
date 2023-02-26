package games.project.factory_fall.vues.helpers;

import games.project.factory_fall.logique.Plateau;
import javafx.geometry.Pos;

/**
 * La VuePlateau est une classe qui hérite de VueGrille.
 * Elle est utilisée pour afficher toute la grille du jeu.
 *
 * @see HelperGrille
 */
public class HelperVuePlateau extends HelperGrille {

    public HelperVuePlateau(Plateau p) {
        super(p);
        styliser();
    }

    private void styliser() {
        this.setAlignment(Pos.CENTER);
    }
}
