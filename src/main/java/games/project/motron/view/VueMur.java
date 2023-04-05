package games.project.motron.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class VueMur {
    private final ArrayList<Rectangle> listeMur;
    private final int tailleMur = 20;
    private int nbMurs;

    private static Color couleurMurs = Color.ALICEBLUE;

    public VueMur() {
        listeMur = new ArrayList<>();
        nbMurs = 0;
    }

    public void dessinerArene(VueArene vueArene, int[][] matriceMurs) {
        vueArene.setMapHeight(matriceMurs.length * tailleMur);
        vueArene.setMapWidth(matriceMurs[0].length * tailleMur);
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


    public void dessinerBordures(VueArene vueArene) {
        int x = 0;
        int y = 0;

        while (x < vueArene.getMapWidth())        // murs du haut
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
        while (y < vueArene.getMapHeight())        // murs de gauche
        {
            Rectangle mur = new Rectangle(x, y, tailleMur, tailleMur);
            mur.setStroke(couleurMurs);
            mur.setStrokeWidth(2.0);
            listeMur.add(mur);

            y += tailleMur;
            nbMurs++;
        }

        x = vueArene.getMapWidth() - tailleMur;
        y = tailleMur;
        while (y < vueArene.getMapHeight())        // murs de droite
        {
            Rectangle mur = new Rectangle(x, y, tailleMur, tailleMur);
            mur.setStroke(couleurMurs);
            mur.setStrokeWidth(2.0);
            listeMur.add(mur);

            y += tailleMur;
            nbMurs++;
        }

        x = 0;
        y = vueArene.getMapHeight() - tailleMur;
        while (x < vueArene.getMapWidth())        // murs du bas
        {
            Rectangle mur = new Rectangle(x, y, tailleMur, tailleMur);
            mur.setStroke(couleurMurs);
            mur.setStrokeWidth(2.0);
            listeMur.add(mur);

            x += tailleMur;
            nbMurs++;
        }

    }

    public ArrayList<Rectangle> getMurList() {
        return listeMur;
    }

    public int getMurSize() {
        return tailleMur;
    }

    public static void setCouleurMurs(Color couleurMurs) {
        VueMur.couleurMurs = couleurMurs;
    }
}
