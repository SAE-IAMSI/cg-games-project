package games.project.paco_mano.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Mur {
    private ArrayList<Rectangle> listeMur;
    private final int tailleMur = 20;
    private int nbMurs;

    private static Color couleurMurs = Color.ALICEBLUE;

    public Mur() {
        listeMur = new ArrayList<>();
        nbMurs = 0;
    }

    public void dessinerArene(Arene arene, int[][] matriceMurs) {
//        matriceMurs = new int[arene.getMapWidth()/tailleMur][arene.getMapHeight()/tailleMur];
        arene.setMapHeight(matriceMurs.length * tailleMur);
        arene.setMapWidth(matriceMurs[0].length * tailleMur);

//        for (int x = 0; x<(arene.getMapWidth()/tailleMur); x++){
//            for (int y = 0; y<(arene.getMapHeight()/tailleMur); y++){
        for (int y = 0; y < matriceMurs.length; y++) {
            for (int x = 0; x < matriceMurs[0].length; x++) {

                if (matriceMurs[y][x] == 1) {
                    Rectangle mur = new Rectangle(x * tailleMur, y * tailleMur, tailleMur, tailleMur);
                    mur.setStroke(couleurMurs);
                    mur.setStrokeWidth(2.0);
                    listeMur.add(mur);
                    nbMurs++;
                }

            }
        }
    }


    public void dessinerBordures(Arene arene) {
        int x = 0;
        int y = 0;

        while (x < arene.getMapWidth())        // murs du haut
        {
            Rectangle mur = new Rectangle(x, y, tailleMur, tailleMur);
            mur.setStroke(couleurMurs);
            mur.setStrokeWidth(2.0);
            listeMur.add(mur);

            x += tailleMur;
            nbMurs++;
        }

        x = 0;
        y = tailleMur;
        while (y < arene.getMapHeight())        // murs de gauche
        {
            Rectangle mur = new Rectangle(x, y, tailleMur, tailleMur);
            mur.setStroke(couleurMurs);
            mur.setStrokeWidth(2.0);
            listeMur.add(mur);

            y += tailleMur;
            nbMurs++;
        }

        x = arene.getMapWidth() - tailleMur;
        y = tailleMur;
        while (y < arene.getMapHeight())        // murs de droite
        {
            Rectangle mur = new Rectangle(x, y, tailleMur, tailleMur);
            mur.setStroke(couleurMurs);
            mur.setStrokeWidth(2.0);
            listeMur.add(mur);

            y += tailleMur;
            nbMurs++;
        }

        x = 0;
        y = arene.getMapHeight() - tailleMur;
        while (x < arene.getMapWidth())        // murs du bas
        {
            Rectangle mur = new Rectangle(x, y, tailleMur, tailleMur);
            mur.setStroke(couleurMurs);
            mur.setStrokeWidth(2.0);
            listeMur.add(mur);

            x += tailleMur;
            nbMurs++;
        }

    }

    public void dessinerCarte(int i) {
        if (i == 0) {
            dessinerCarte1();
        }
        if (i == 1) {
            dessinerCarte2();
        }
        if (i == 2) {
            dessinerCarte3();
        }
        if (i == 3) {
            dessinerCarte4();
        }

    }

    public void dessinerMurVertical(int cooDebutX, int cooDebutY, int nbCarres) {
        int y;
        int longOb = cooDebutY + 20 * nbCarres;
        for (y = cooDebutY; y < longOb; y += tailleMur) // bout de mur 1
        {
            Rectangle mur = new Rectangle(cooDebutX, y, tailleMur, tailleMur);
            mur.setStroke(couleurMurs);
            mur.setStrokeWidth(2.0);
            listeMur.add(mur);
            nbMurs++;
        }
    }

    public void dessinerMurHorizontal(int cooDebutX, int cooDebutY, int nbCarres) {
        int x;
        int longOb = cooDebutX + 20 * nbCarres;
        for (x = cooDebutX; x < longOb; x += tailleMur) // bout de mur 1
        {
            Rectangle mur = new Rectangle(x, cooDebutY, tailleMur, tailleMur);
            mur.setStroke(couleurMurs);
            mur.setStrokeWidth(2.0);
            listeMur.add(mur);
            nbMurs++;
        }
    }

    public void dessinerMurDiagDroite(int cooDebutX, int cooDebutY, int nbCarres) {
        for (int i = 0; i < nbCarres; i++) {
            dessinerMurHorizontal(cooDebutX + i * 20, cooDebutY + i * 20, 1);
        }
    }

    public void dessinerMurDiagGauche(int cooDebutX, int cooDebutY, int nbCarres) {
        for (int i = 0; i < nbCarres; i++) {
            dessinerMurHorizontal(cooDebutX - i * 20, cooDebutY + i * 20, 1);
        }
    }

    public void dessinerCarte1() {
        dessinerMurVertical(420, 20, 7);
        dessinerMurHorizontal(80, 60, 6);
        dessinerMurVertical(500, 360, 12);
        dessinerMurHorizontal(600, 600, 10);
        dessinerMurHorizontal(100, 580, 10);
        dessinerMurHorizontal(580, 100, 10);
        dessinerMurVertical(280, 400, 10);
    }

    public void dessinerCarte2() {
        //cercle
        dessinerMurHorizontal(340, 300, 6);
        dessinerMurDiagGauche(320, 320, 2);
        dessinerMurDiagDroite(460, 320, 2);
        dessinerMurVertical(500, 360, 5);
        dessinerMurVertical(280, 360, 5);
        dessinerMurDiagDroite(300, 460, 2);
        dessinerMurDiagGauche(480, 460, 2);
        dessinerMurHorizontal(340, 500, 6);

        //Haut
        dessinerMurHorizontal(340, 100, 6);
        dessinerMurHorizontal(340, 200, 6);
        //Bas
        dessinerMurHorizontal(340, 600, 6);
        dessinerMurHorizontal(340, 700, 6);
        //Gauche
        dessinerMurVertical(80, 360, 5);
        dessinerMurVertical(200, 360, 5);
        //Droite
        dessinerMurVertical(580, 360, 5);
        dessinerMurVertical(700, 360, 5);
    }

    public void dessinerCarte3() {
        //Haut gauche
        dessinerMurHorizontal(100, 200, 11);
        dessinerMurVertical(200, 100, 11);
        //Haut droite
        dessinerMurHorizontal(480, 200, 11);
        dessinerMurVertical(580, 100, 11);
        //Bas gauche
        dessinerMurHorizontal(100, 580, 11);
        dessinerMurVertical(200, 480, 11);
        //Bas droite
        dessinerMurHorizontal(480, 580, 11);
        dessinerMurVertical(580, 480, 11);
    }

    public void dessinerCarte4() {
        //Diagonales du haut
        dessinerMurDiagDroite(80, 80, 12);
        dessinerMurDiagGauche(700, 80, 12);
        //Diagonales du bas
        dessinerMurDiagDroite(480, 500, 12);
        dessinerMurDiagGauche(300, 500, 12);
        //Verticales
        dessinerMurVertical(160, 320, 9);
        dessinerMurVertical(620, 320, 9);
        //Horizontales
        dessinerMurHorizontal(320, 160, 8);
        dessinerMurHorizontal(320, 620, 8);
    }

    public ArrayList<Rectangle> getMurList() {
        return listeMur;
    }

    public void setMurList(ArrayList<Rectangle> murList) {
        this.listeMur = murList;
    }

    public int getMurSize() {
        return tailleMur;
    }

    public int getNbMurs() {
        return nbMurs;
    }

    public void setNbMurs(int nbMurs) {
        this.nbMurs = nbMurs;
    }

    public static void setCouleurMurs(Color couleurMurs) {
        Mur.couleurMurs = couleurMurs;
    }
}
