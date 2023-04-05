package games.project.motron.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class VueArene {

    private int mapWidth = 800;
    private int mapHeight = 800;
    private final int murSize = 20;
    private final VueMur murs = new VueMur();
    private final ArrayList<Line> lineList = new ArrayList<>();

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

    public VueMur getMurs() {
        return murs;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public VueArene() {
        drawLines();
        murs.dessinerBordures(this);
    }

    public VueArene(int[][] matArene) {
        murs.dessinerArene(this, matArene);
        drawLines();
    }
    // -----------------------------------------------------------------

    public void drawLines() {
        //crée les lignes verticales
        int a = 0;
        for (int x = 0; x < mapWidth - murSize * 2; x += 20) {
            int y = 0;
            Line line = new Line(x + murSize, y + murSize, x + murSize, mapHeight - murSize);
            if (a % 3 == 0) {

                line.setStrokeWidth(0.7);
            } else {
                line.setStrokeWidth(0.5);
            }
            line.setStroke(Color.DIMGREY);
            line.setOpacity(0.5);
            lineList.add(line);
            a++;
        }

        //crée les lignes horizontales
        a = 0;
        for (int y = 0; y < mapHeight - murSize * 2; y += 20) {
            int x = 0;
            Line line = new Line(x + murSize, y + murSize, mapWidth - murSize, y + murSize);
            if (a % 3 == 0) {
                line.setStrokeWidth(0.7);
            } else {
                line.setStrokeWidth(0.5);
            }
            line.setStroke(Color.DIMGREY);
            line.setOpacity(0.5);
            lineList.add(line);
            a++;
        }
    }

}
