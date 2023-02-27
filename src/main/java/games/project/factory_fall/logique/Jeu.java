package games.project.factory_fall.logique;

import games.project.factory_fall.IJeu;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import static games.project.factory_fall.logique.Forme.NULL;

public class Jeu implements IJeu {
    private final Joueur j;
    private final Plateau p;

    private final ArrayList<Piece> sacProchainesPieces;
    private final Plateau prochainePiece;
    private final Plateau stockage;
    private final BooleanProperty jeuEnCours;
    public static Timer timer;
    public static Piece pieceActuelle;
    public static int ligneActuelle;
    public static int colonneActuelle;
    public static Piece pieceSuivante;
    public static Piece pieceSauvegardee;
    private boolean pieceEchangee;
    private IntegerProperty nbVies;

    public Jeu(String pseudo) {
        j = new Joueur(pseudo);
        p = new Plateau(10, 22, j);
        prochainePiece = new Plateau(4, 2, j);
        stockage = new Plateau(4, 2, j);
        initialiserStockage();
        pieceEchangee = false;

        nbVies = new SimpleIntegerProperty(1);
        jeuEnCours = new SimpleBooleanProperty(true);

        sacProchainesPieces = new ArrayList<>();
        this.remplirSacProchainesPieces();
        nouvellePieceActuelle();
    }

    /**
     * Constructeur qui gère un nombre de vies custom
     */
    public Jeu(String pseudo, int nbVies) {
        this(pseudo);
        if (nbVies > 1) {
            this.nbVies = new SimpleIntegerProperty(nbVies);
        }
    }

    /**
     * Affiche le Plateau si le jeu est toujours en cours et arrête la partie sinon
     */
    public void jouerTour() {
        if (!jeuEnCours.getValue()) {
            timer.stop();
        } else {
            timer.setDelay((int) (Math.pow(0.8 - ((p.getRang().getValue() - 1) * 0.007), p.getRang().getValue() - 1) * 1000));
        }
    }

    public void perdreUneVie() {
        if (nbVies.get() <= 1) jeuEnCours.setValue(false);
        else {
            nbVies.setValue(nbVies.get() - 1);
            p.remplirTableau();
        }
    }

    /**
     * Si le sac des prochaines pièces est vide, le rempli avec une pièce de chaque forme
     */
    public void remplirSacProchainesPieces() {
        if (sacProchainesPieces.isEmpty()) {
            for (int i = 1; i < 8; i++) {
                sacProchainesPieces.add(new Piece(Forme.values()[i]));
            }
            Collections.shuffle(sacProchainesPieces);
        }
    }

    /**
     * Supprime et retourne la premiere piece de la liste des pieces suivantes.
     *
     * @return la premiere piece de la liste en attente
     */
    public Piece recupererProchainePiece() {
        return sacProchainesPieces.remove(0);
    }

    /**
     * Créé une nouvelle pièce sur le plateau, au spawn.
     * Si la case de spawn est occupée, alors le jeu se termine.
     * La méthode supprime également les lignes remplies
     * Incrémente le score et supprime les lignes si nécessaires
     * Incrémente le niveau de la partie.
     */
    public void nouvellePieceActuelle() {
        pieceActuelle = recupererProchainePiece();
        ligneActuelle = 1;
        colonneActuelle = p.getLargeur() / 2 - 1;
        remplirSacProchainesPieces();
        nouvelleProchainePiece();
        p.incrementerScoreJoueur(p.suppressionLignesRemplies());
        p.incrementerRang();
        pieceEchangee = false;
        if (!p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle)) {
            perdreUneVie();
        }
    }

    /**
     * Vide la grille de visualisation de la prochaine piece, change la pièce suivante, et l'affiche dans la grille
     */
    public void nouvelleProchainePiece() {
        prochainePiece.remplirTableau();
        pieceSuivante = sacProchainesPieces.get(0);
        prochainePiece.placerPiece(1, prochainePiece.getLargeur() / 2 - 1, pieceSuivante);
    }

    /**
     * Initialise l'espace de stockage ainsi que la piece sauvegardée
     */
    public void initialiserStockage() {
        stockage.remplirTableau();
        pieceSauvegardee = new Piece(NULL);
    }

    /**
     * echange la piece actuelle avec la piece contenue dans stockage. Si stockage est vide génère une nouvelle piece et
     * place la piece actuelle dans le stockage. Ne peut etre appelée qu'une seule jusqu'a se qu'une nouvelle piece soit générée.
     */
    public void echangePieceActuelleEtPieceSauvegarde() {
        if (Objects.equals(pieceSauvegardee.getNom(), " ")) {
            pieceSauvegardee = new Piece(Forme.valueOf(pieceActuelle.getNom()));
            p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
            stockage.placerPiece(1, stockage.getLargeur() / 2 - 1, pieceSauvegardee);
            nouvellePieceActuelle();
        } else if (!pieceEchangee) {
            stockage.supprimerPieceTotale(1, stockage.getLargeur() / 2 - 1, pieceSauvegardee);
            p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
            ligneActuelle = 1;
            colonneActuelle = p.getLargeur() / 2 - 1;
            p.placerPiece(ligneActuelle, colonneActuelle, pieceSauvegardee);
            Piece copie = pieceSauvegardee;
            pieceSauvegardee = new Piece(Forme.valueOf(pieceActuelle.getNom()));
            stockage.placerPiece(1, stockage.getLargeur() / 2 - 1, pieceSauvegardee);
            pieceActuelle = copie;
            jouerTour();
        }
        pieceEchangee = true;
    }

    /**
     * Supprime la pièce actuelle. Essaie de placer la pièce sur la colonne indiqué.
     * Si la nouvelle pièce actuelle a bien été placée, actualise les coordonnées actuelles.
     * Sinon, replace l'ancienne pièce actuelle.
     */
    public void deplacerPieceActuelle(int colonne) {
        p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
        if (p.placerPiece(ligneActuelle, colonne, pieceActuelle)) {
            colonneActuelle = colonne;
        } else p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle);
    }

    /**
     * Fait tomber la pièce le plus bas possible dans la colonne actuelle.
     * Si ça échoue, c'est que toute la colonne est occupée, donc la pièce
     * dépasse les limites. On met alors fin au jeu.
     */
    public void tomberPieceActuelle() {
        if (p.placerPieceParColonne(ligneActuelle, colonneActuelle, pieceActuelle)) {
            p.incrementerScoreHardDrop();

            nouvellePieceActuelle();
        } else {
            perdreUneVie();
        }
    }

    /**
     * Fait tomber la pièce d'une ligne dans la colonne actuelle.
     * Si ça échoue, pose la pièce actuelle aux coordonnées actuelles
     * (donc sans baisser de ligne).
     */
    public void tomberPieceActuelle1Ligne() {
        p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
        if (p.placerPiece(ligneActuelle + 1, colonneActuelle, pieceActuelle)) {
            ligneActuelle++;
        } else {
            p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle);
            nouvellePieceActuelle();
        }
    }

    /**
     * Supprimer la pièce courante, puis vérifie la validité du placement de la rotation de la pièce
     * actuelle aux coordonnées actuelles. Si c'est valide, place la nouvelle pièce, sinon replace l'ancienne
     */
    public void tournerPieceActuelle(char sens) {
        p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
        if (p.placementValide(ligneActuelle, colonneActuelle, pieceActuelle.creerPieceTournee(sens))) {
            pieceActuelle = pieceActuelle.creerPieceTournee(sens);
        }
        p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle);
    }

    @Override
    public void actionGauche() {
        deplacerPieceActuelle(colonneActuelle - 1);
        jouerTour();
    }

    @Override
    public void actionDroite() {
        deplacerPieceActuelle(colonneActuelle + 1);
        jouerTour();
    }

    @Override
    public void actionBas() {
        tomberPieceActuelle1Ligne();
        p.incrementerScoreSoftDrop();
        jouerTour();
    }

    @Override
    public void actionEspace() {
        tomberPieceActuelle();
        jouerTour();
    }

    @Override
    public void actionHaut() {
        tournerPieceActuelle('d');
        jouerTour();
    }

    @Override
    public void actionR() {
        tournerPieceActuelle('g');
        jouerTour();
    }

    @Override
    public void actionEchap() {
        jeuEnCours.setValue(false);
        jouerTour();
    }

    @Override
    public void actionC() {
        echangePieceActuelleEtPieceSauvegarde();
        jouerTour();
    }

    @Override
    public boolean isJeuEnCours() {
        return jeuEnCours.getValue();
    }

    @Override
    public IntegerProperty getRang(Plateau plateauChoisi) {
        return plateauChoisi.getRang();
    }

    @Override
    public Joueur getJoueur() {
        return j;
    }

    @Override
    public Plateau getPlateau() {
        return p;
    }

    public Plateau getProchainePiece() {
        return prochainePiece;
    }

    @Override
    public Plateau getStockage() {
        return stockage;
    }

    public BooleanProperty jeuEnCoursProperty() {
        return jeuEnCours;
    }

    public IntegerProperty getNbVies() {
        return nbVies;
    }
}
