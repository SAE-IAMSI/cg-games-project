package games.project.factory_fall.vues.helpers;

import games.project.factory_fall.logique.Plateau;

/**
 * La VuePieceExterieur est une classe qui hérite de VueGrille.
 * Elle est utilisée pour afficher une portion d'une pièce dans la vue de la prochaine pièce ou de la pièce sauvegardée.
 *
 * @see HelperGrille
 */
public class HelperPieceExterieur extends HelperGrille {

    public HelperPieceExterieur(Plateau p) {
        super(p);
    }
}
