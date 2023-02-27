package games.project.factory_fall;/*package tetris.Test;

import javafx.beans.property.*;
import org.testng.annotations.Test;
import tetris.logique.*;


import static org.testng.AssertJUnit.*;

public class TestUnitaireSprint2 {

   // static Timer timer;


    static Jeu j = new Jeu();


    /**
     * fonction qui fait un nouveau jeu
     *//*
    void start() {
        Jeu j = new Jeu();
        //  Joueur jo = new Joueur("Luther");
        //  Plateau p = new Plateau(10, 22, jo);


    }
/**
     * Test si la piece a bien été rotate selon la formule vers la droite
     *
     *//*
    @Test
    public void testRotationPieceGauche() {
        start();

        int[][] coord_PieceEnCours = Jeu.getPieceActuelle().getForme();

        System.out.println("rotation est actionnée !");
        j.tournerPieceActuelle();

        for(int i = 0; i < 4; i++) {
            coord_PieceEnCours[i][0] = coord_PieceEnCours[i][1] ;
            coord_PieceEnCours[i][1] = coord_PieceEnCours[i][0] * -1;
        }
        System.out.println("piece rotate !");
        assertEquals(coord_PieceEnCours,Jeu.getPieceActuelle().getForme());


    }
    /**
     * Test si la piece a bien été rotate selon la formule vers la gauche
     *
     *//*
    @Test
    public void testRotationPieceGauche() {
        start();

        int[][] coord_PieceEnCours = Jeu.getPieceActuelle().getForme();

        System.out.println("rotation est actionnée !");
        j.tournerPieceActuelle();

        for(int i = 0; i < 4; i++) {
            coord_PieceEnCours[i][0] = coord_PieceEnCours[i][1] * -1;
            coord_PieceEnCours[i][1] = coord_PieceEnCours[i][0];
        }
        System.out.println("piece rotate !");
        assertEquals(coord_PieceEnCours,Jeu.getPieceActuelle().getForme());
    }

    /**
     * Test si le rang est bien initialisé a 1
     *//*
    @Test
    public void testRangDeBasse() {
        start();
        IntegerProperty rang = new SimpleIntegerProperty();
        rang.setValue(1);
        assertEquals(rang.getValue(),j.getRang(j.getPlateau()).getValue());


    }

    /**
     * Test si le rang est bien augmenté apres appel de la fonction mais le score doit etre augmenté avant
     *//*
    @Test
    public void testRangSuperieur() {
        start();
        IntegerProperty rang = new SimpleIntegerProperty();
        rang.setValue(3);
        DoubleProperty dou = new SimpleDoubleProperty(1000.0);
        System.out.println("score initialisé a 1000 afin d'augmenté de rang: ");
        System.out.println("on augmente le rang a 3 et on assert que le rang a bien été augmenté !");
        j.getJoueur().setScore(dou);
        j.getPlateau().incrementerRang();
        j.getPlateau().incrementerRang();

        assertEquals(rang.getValue(), j.getRang(j.getPlateau()).getValue());

    }

    /**
     *test qui crée une piece avec
     * le timer de décente initialisé atttend 2 s et assert que la piece n'est plus au swaunw
     * @apiNote probleme pour le timer
     */



    /*
    @Disabled
    @Test
    public void test_decenteautomatique() throws InterruptedException {
        start(); // on doit essayé qyue la decente auto du timer ce fasse
        timer = new Timer(1000, descenteAuto);
        timer.start();
        j.nouvellePieceActuelle();
        String nom =  j.getPlateau().getPieceNom(2,5);

        int[][] coorspiecedepart = j.getPieceActuelle().getForme();
        wait(3000);
        int[][] coorspieceactulle = j.getPieceActuelle().getForme(); // on regarde les coo du la piece actuelle si elle a decendu auto

        assertNotEquals(coorspieceactulle,coorspiecedepart );
         // on test si la piece est toujours a la position de base
    }     // pas sur de la coordonée , a verif plus tard


     */






/*
    /**
     *test qui crée une piece sur un plateau et la fait décendre en bas
     *  assert que la coordonnée de la piece descendu est la bonne.
     *//*
    @Test
    public void testTomberPiece() {
        start();
        int[][] coord_PieceEnCours = Jeu.getPieceActuelle().getForme();
        System.out.println("barre espace est actionnée !");
        j.tomberPieceActuelle();

        // assertEquals(j.getColonneActuelle(), 4);

        for(int i = 0; i < 4; i++) {
            coord_PieceEnCours[i][0] = coord_PieceEnCours[i][1] + 20;
            coord_PieceEnCours[i][1] = coord_PieceEnCours[i][0] + 20;
        }
        j.tomberPieceActuelle1Ligne();
        assertEquals(coord_PieceEnCours, coord_PieceEnCours);
    }









    /**
     *test qui crée une piece sur un plateau et la fait décendre de 1 ligne
     *  assert que la coordonnée de la piece descendu est la bonne.
     *//*
    @Test
    public void testTomberPiece1ligne() {
        start();
        int[][] coord_PieceEnCours = Jeu.getPieceActuelle().getForme();
        System.out.println("Flèche du bas est actionnée !");
        j.tomberPieceActuelle1Ligne();

        // assertEquals(j.getColonneActuelle(), 4);

        for(int i = 0; i < 4; i++) {
            coord_PieceEnCours[i][0] = coord_PieceEnCours[i][1] + 1;
            coord_PieceEnCours[i][1] = coord_PieceEnCours[i][0] + 1;
        }
        j.tomberPieceActuelle1Ligne();
        assertEquals(coord_PieceEnCours, coord_PieceEnCours);
    }


    /**
     *test qui crée un joueur et add son score de 100,
     * et fait le joueur de jeu remplir ligne donc add 100 au score,
     * assert equal que les 2 joueurs on le même score
     *//*
    @Test
    public void TestCalculScore() {
        start();
        Joueur joueur= new Joueur(j.getJoueur().getPseudo());


        DoubleProperty score = new SimpleDoubleProperty();
        score.setValue(100);




        // a voir pour ca erreur de type

        joueur.setScore(score); // on set  le score a 100 soit une ligne
        j.remplirLigne(20);
        j.remplirLigne(21); // les ligne remplie sont censé entre suprimé et add 100 au score du jeu ??
        //sinon test a revoir , question a posé ;
        j.getPlateau().placerPiece( 21,  8,new Piece(Forme.O));


        j.getPlateau().incrementerScoreJoueur(j.getPlateau().suppressionLignesRemplies());
        // on supprime la ligne remplit et le score s'ajoute automatiquement


        assertEquals((j.getScoreJoueurChoisi(j.getJoueur())),
                joueur.getScore().getValue());

    }

}*/
