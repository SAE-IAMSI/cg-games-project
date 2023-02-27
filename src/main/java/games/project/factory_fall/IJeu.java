package games.project.factory_fall;

import games.project.factory_fall.logique.Joueur;
import games.project.factory_fall.logique.Plateau;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

public interface IJeu {

    Plateau getStockage();

    BooleanProperty jeuEnCoursProperty();

    void actionGauche();

    void actionDroite();

    void actionBas();

    void actionHaut();

    void actionR();

    void actionC();

    void actionEspace();

    void actionEchap();


    Plateau getPlateau();

    Plateau getProchainePiece();

    Joueur getJoueur();

    void nouvellePieceActuelle();

    void jouerTour();

    void tomberPieceActuelle1Ligne();


    boolean isJeuEnCours();

    IntegerProperty getRang(Plateau plateauChoisi);

    IntegerProperty getNbVies();
}
