package games.project.factory_fall.logique;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Plateau {
    private final int hauteur;
    private final int largeur;
    private final Piece[][] plateau;
    private final Joueur joueur;
    private final IntegerProperty rang;

    /**
     * Le plateau est défini en fonction des largeur et hauteur indiquées, et contient des pièces (NULL ou autre). Un joueur est aussi nécessaire
     * pour définir le plateau dans l'objectif du 2 joueurs plus tard.
     */
    public Plateau(int largeur, int hauteur, Joueur joueur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.joueur = joueur;
        plateau = new Piece[hauteur][largeur];
        remplirTableau();
        rang = new SimpleIntegerProperty();
        rang.setValue(1);
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public Piece[][] getPlateau() {
        return plateau;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    /**
     * @return Le nom (string) de la pièce à l'emplacement indiqué
     */
    public String getPieceNom(int ligne, int colonne) {
        return this.plateau[ligne][colonne].getNom();
    }

    public IntegerProperty getRang() {
        return rang;
    }

    /**
     * Place des pièces NULL dans toutes les cases du plateau. C'est l'initialisation du plateau
     */
    public void remplirTableau() {
        Piece piece = new Piece(Forme.NULL);

        for (int ligne = 0; ligne < this.hauteur; ++ligne) {
            for (int colonne = 0; colonne < this.largeur; ++colonne) {
                this.plateau[ligne][colonne] = piece;
            }
        }
    }

    /**
     * @return Vrai si la case indiquée n'est pas occupée par une pièce non NULL
     */
    public boolean estVide(int ligne, int colonne) {
        return this.plateau[ligne][colonne].getNom().equals(" ");
    }

    /**
     * @return Vrai si la case indiquée est dans les limites du plateau, et qu'elle est vide
     */
    public boolean placementValide(int ligne, int colonne, Piece piece) {
        for (int numCase = 0; numCase < 4; numCase++) {
            if (!(0 <= ligne - piece.getForme()[numCase][0]
                    && ligne - piece.getForme()[numCase][0] < this.hauteur
                    && 0 <= colonne + piece.getForme()[numCase][1]
                    && colonne + piece.getForme()[numCase][1] < this.largeur
                    && this.estVide(ligne - piece.getForme()[numCase][0], colonne + piece.getForme()[numCase][1]))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Appelle placementValide() pour vérifier les coordonnées et place la pièce si le placement est valide
     *
     * @return Vrai si la pièce a été placée
     */
    public boolean placerPiece(int ligne, int colonne, Piece piece) {
        if (this.placementValide(ligne, colonne, piece)) {
            for (int numCase = 0; numCase < 4; numCase++) {
                this.plateau[ligne - piece.getForme()[numCase][0]][colonne + piece.getForme()[numCase][1]] = piece;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Le for est vide, car l'action se trouve dans la condition d'arrêt, via placerPiece()
     *
     * @return Vrai si la pièce a pu être placée
     * Pré-requis : la position actuelle donnée en paramètre est valide.
     */
    public boolean placerPieceParColonne(int ligneDepart, int colonne, Piece piece) {
        supprimerPieceTotale(ligneDepart, colonne, piece);

        if (placementValide(ligneDepart + 1, colonne, piece)) {
            return placerPieceParColonne(ligneDepart + 1, colonne, piece);
        } else return placerPiece(ligneDepart, colonne, piece);
    }

    /**
     * Supprime la pièce à l'emplacement indiqué (la remplace par NULL)
     */
    public void supprimerPieceTotale(int ligne, int colonne, Piece piece) {
        for (int numCase = 0; numCase < 4; numCase++) {
            this.plateau[ligne - piece.getForme()[numCase][0]][colonne + piece.getForme()[numCase][1]] = new Piece(Forme.NULL);
        }
    }

    /**
     * Supprime la ligne à l'emplacement indiqué (remplace toutes les colonnes par NULL)
     *
     * @param ligne ligne observée
     */
    public void supprimerLigne(int ligne) {
        for (int colonne = 0; colonne < this.largeur; colonne++) {
            this.plateau[ligne][colonne] = new Piece(Forme.NULL);
        }
    }

    /**
     * Vérifie pour chaque ligne, si elle est remplie et appelle la méthode de suppression supprimerLigne() le cas échéant
     */
    public int suppressionLignesRemplies() {
        int lignesSupprimees = 0;
        // pour chaque ligne, on regarde si elle est remplie
        for (int ligne = 0; ligne < this.hauteur; ligne++) {
            if (ligneRemplie(ligne)) {
                // le cas échéant, on remplace toutes les pièces par Piece.NULL
                supprimerLigne(ligne);
                tomberLignesApresSuppression(ligne);
                lignesSupprimees++;
            }
        }
        joueur.setLignesSup(joueur.getLignesSup().getValue() + lignesSupprimees);
        return lignesSupprimees;
    }

    /**
     * Regarde la ligne et vérifie si elle est remplie
     *
     * @param ligne ligne observée
     * @return Vrai, si la ligne est remplie.
     * Faux, sinon
     */
    public boolean ligneRemplie(int ligne) {
        for (int colonne = 0; colonne < this.largeur; colonne++) {
            if (this.estVide(ligne, colonne)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fait tomber les pièces qui sont au-dessus d'une ligne venant d'être supprimée
     *
     * @param ligneSupprimee la ligne qui a été supprimée
     */
    public void tomberLignesApresSuppression(int ligneSupprimee) {
        for (int ligne = ligneSupprimee; ligne >= 0; ligne--) {
            for (int colonne = 0; colonne < this.largeur; colonne++) {
                if (ligne != 0) {
                    // On remplit la ligne observée par les pièces de la ligne ligne - 1
                    this.plateau[ligne][colonne] = this.plateau[ligne - 1][colonne];
                } else {
                    // On remplit la colonne 0 (la plus haute) par des pièces NULL
                    this.plateau[0][colonne] = new Piece(Forme.NULL);
                }
            }
        }
    }

    /**
     * Incrémente le score du joueur attribué au plateau en fonction du rang et du nombre de lignes supprimées
     */
    public void incrementerScoreJoueur(int lignes) {
        if (lignes == 1) {
            joueur.getScore().setValue(joueur.getScore().getValue() + (40 * rang.getValue()));
        }
        if (lignes == 2) {
            joueur.getScore().setValue(joueur.getScore().getValue() + (100 * rang.getValue()));
        }
        if (lignes == 3) {
            joueur.getScore().setValue(joueur.getScore().getValue() + (300 * rang.getValue()));
        }
        if (lignes == 4) {
            joueur.getScore().setValue(joueur.getScore().getValue() + (1200 * rang.getValue()));
        }
    }

    public void incrementerScoreSoftDrop() {
        joueur.getScore().setValue(joueur.getScore().getValue() + 1);
    }

    public void incrementerScoreHardDrop() {
        int calcul = 0;
        int ligne = Jeu.ligneActuelle;
        int cologne = Jeu.colonneActuelle;
        Piece tampon = Jeu.pieceActuelle;
        while (placementValide(ligne, cologne, tampon)) {
            ligne++;
            calcul++;
        }
        joueur.getScore().setValue(joueur.getScore().getValue() + (calcul * 2));
    }


    /**
     * Incrémente le rang de la partie en fonction des paliers définis par (2^rang)*100
     */
    public void incrementerRang() {
        if (joueur.getScore().getValue() >= Math.pow(2, rang.getValue()) * 300) {
            rang.setValue(rang.getValue() + 1);
        }
    }
}
