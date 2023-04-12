package games.project.paco_mano.view.old;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Arene {

    private int mapWidth = 720;
    private int mapHeight = 1280;
    private final int murSize = 20;
    private Mur murs = new Mur();
    private ArrayList<Line> lineList = new ArrayList<>();
    private int[][]matArene;
    private final ArrayList<Circle> pelletList = new ArrayList<Circle>();
    private final ArrayList<Circle> bonusList = new ArrayList<Circle>();

    private final ArrayList<Rectangle> wallList = new ArrayList<Rectangle>();


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



    public int getMurSize() {
        return murSize;
    }

    public int[][] getMatArene() {
        return matArene;
    }

    public ArrayList<Circle> getPelletList() {
        return pelletList;
    }

    public ArrayList<Circle> getBonusList() {
        return bonusList;
    }

    public ArrayList<Rectangle> getWallList() {
        return wallList;
    }
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

    public void drawPellets(Pane pane) {
        int x, y, num = 0;

        for (x = 30; x < mapWidth-25; x += murSize) {
            for (y = 30; y < mapHeight-25; y += murSize) {
                if (!isAWall(x - (murSize / 2), y - (murSize / 2)) && !isABonusFood(x, y)) {
                    Circle pellet = new Circle();
                    pellet.setRadius(1.5);
                    pellet.setCenterX(x);
                    pellet.setCenterY(y);
                    pellet.setFill(Color.GREEN);
                    pelletList.add(pellet);
                    pane.getChildren().add(pellet);
                    num++;
                }
            }
        }
    }

    // method to put bonus food in the game. The cooridinates are held in an ArrayList
    public void drawBonusFood(Pane pane) {
        int x, y;

        for (y = 50; y < mapHeight; y += 540) {
            for (x = 70; x < mapWidth; x += 860) {
                Circle c = new Circle();
                c.setRadius(4);
                c.setCenterX(x);
                c.setCenterY(y);
                c.setFill(Color.BLACK);
                pane.getChildren().add(c);
                bonusList.add(c);
            }
        }

        y = 250;
        for (x = 410; x < 510; x += 80) {
            Circle circ = new Circle();
            circ.setRadius(4);
            circ.setCenterX(x);
            circ.setCenterY(y);
            circ.setFill(Color.GREEN);
            bonusList.add(circ);
        }

        Circle cr = new Circle();
        cr.setRadius(4);
        cr.setCenterX(90);
        cr.setCenterY(310);
        cr.setFill(Color.WHITE);
        bonusList.add(cr);
    }

    // method to check if coordinates of a certain point is the same as the coordinates of a wall
    private Boolean isAWall(double x, double y) {
        for (int n = 0; n < wallList.size(); n++)
            if (wallList.get(n).getX() == x && wallList.get(n).getY() == y) return true;

        return false;
    }

    private Boolean isABonusFood(double x, double y) {
        for (int n = 0; n < bonusList.size(); n++)
            if (bonusList.get(n).getCenterX() == x && bonusList.get(n).getCenterY() == y) return true;

        return false;
    }



}
