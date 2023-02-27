package games.project.motron;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Arene {

    private int mapWidth = 800;
    private int mapHeight = 800;
    private int murSize = 20;
    private Mur murs = new Mur();
    private ArrayList<Line> lineList = new ArrayList<>();
    private int[][]matArene;




    // Getters & setters-----------------------------------------------
    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public ArrayList<Line> getLineList() {
        return lineList;
    }

    public void setLineList(ArrayList<Line> lineList) {
        this.lineList = lineList;
    }

    public Mur getMurs() {
        return murs;
    }

    public void setMurs(Mur murs) {
        this.murs = murs;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

//    public int[][] getMatArene1() {
//        return matArene1;
//    }
//
//    public void setMatArene1(int[][] matArene1) {
//        this.matArene1 = matArene1;
//    }

    public Arene(){
        drawLines();
        murs.dessinerBordures(this);
    }
    public Arene(int nb){
        drawLines();
        murs.dessinerBordures(this);
        murs.dessinerCarte(nb);
    }

    public Arene(int[][]matArene){
        murs.dessinerArene(this, matArene);
        drawLines();
    }
    // -----------------------------------------------------------------

    public void drawLines()
    {
        //crée les lignes verticales
        int a = 0;
        for (int x = 0; x < mapWidth - murSize*2 ; x += 20 /*(mapWidth-murSize*2) / 12*/) //en commentaire: c'est si on peut un certain nombre de ligne précisément, le calcul permet de diviser l'arène en x morceaux
        {
            int y = 0;
            Line line = new Line(x + murSize, y + murSize, x + murSize, mapHeight - murSize);
            if (a%3 == 0)
            {

                line.setStrokeWidth(0.7);
            }
            else
            {
                line.setStrokeWidth(0.5);
            }
            line.setStroke(Color.DIMGREY);
            line.setOpacity(0.5);
            lineList.add(line);
            a ++;
        }

        //crée les lignes horizontales
        a = 0;
        for (int y = 0; y < mapHeight - murSize*2; y+= 20 /*(mapHeight - murSize*2) / 12*/) //en commentaire: c'est si on peut un certain nombre de ligne précisément, le calcul permet de diviser l'arène en x morceaux
        {
            int x = 0;
            Line line = new Line(x + murSize, y + murSize, mapWidth - murSize, y + murSize);
            if (a%3 == 0)
            {
                line.setStrokeWidth(0.7);
            }
            else
            {
                line.setStrokeWidth(0.5);
            }
            line.setStroke(Color.DIMGREY);
            line.setOpacity(0.5);
            lineList.add(line);
            a++;
        }
    }

}
