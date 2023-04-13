package games.project.paco_mano.view;

import games.project.motron.Motron;
import games.project.paco_mano.PacoMano;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Objects;

public class Arene {

    private Pane pane;
    private int mapWidth;
    private int mapHeight;

    private Image salami = new Image(Objects.requireNonNull(PacoMano.class.getResourceAsStream("images/assets/salami.png")), 15, 15, true, true);

    private final int wallSize = 20;

    private int numOfWalls;            // keeps track of the number of walls created

    private final ArrayList<Rectangle> wallList = new ArrayList<Rectangle>();
    private final ArrayList<Circle> pelletList = new ArrayList<Circle>();
    private final ArrayList<ImageView> bonusList = new ArrayList<ImageView>();


    public Arene(Pane pane, int mapWidth, int mapHeight)
    {
        this.pane = pane;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }


    // method to create pellets, store them in an ArrayList and put them on the pane
    public void drawPellets() {
        int x, y, num = 0;

        for (x = 30; x < mapWidth; x += wallSize) {
            for (y = 30; y < mapHeight; y += wallSize) {
                if (!isAWall(x - (wallSize / 2), y - (wallSize / 2)) && !isABonusFood(x, y) && (x < 50 || x > 190 || y < 90 || y > 200)) {
                    Circle pellet = new Circle();
                    pellet.setRadius(1.5);
                    pellet.setCenterX(x);
                    pellet.setCenterY(y);
                    pellet.setFill(Color.WHITE);
                    pane.getChildren().add(pellet);
                    pelletList.add(pellet);
                    num++;
                }
            }
        }
    }

    // method to put bonus food in the game. The cooridinates are held in an ArrayList
    public void drawBonusFood() {
        int x, y;

        for (y = 30; y < mapHeight; y += (mapHeight - (wallSize * 3))) {
            for (x = 30; x < mapWidth; x += (mapWidth - (wallSize * 3))) {
                ImageView c = new ImageView();
                c.setImage(salami);
                c.setX(x-8);
                c.setY(y-8);
                pane.getChildren().add(c);
                bonusList.add(c);
            }
        }

        y = 250;
        for (x = 410; x < 510; x += 80) {
            ImageView c1 = new ImageView();
            c1.setImage(salami);
            c1.setX(x-8);
            c1.setY(y-8);
            pane.getChildren().add(c1);
            bonusList.add(c1);
        }

        ImageView c2 = new ImageView();
        c2.setImage(salami);
        c2.setX(x-8);
        c2.setY(y-8);
        pane.getChildren().add(c2);
        bonusList.add(c2);
    }

    // method to check if coordinates of a certain point is the same as the coordinates of a wall
    private Boolean isAWall(double x, double y) {
        for (int n = 0; n < wallList.size(); n++)
            if (wallList.get(n).getX() == x && wallList.get(n).getY() == y) return true;

        return false;
    }

    private Boolean isABonusFood(double x, double y) {
        for (int n = 0; n < bonusList.size(); n++)
            if (bonusList.get(n).getX() == x-8 && bonusList.get(n).getY() == y-8) return true;

        return false;
    }

    // method to make the walls
    public void drawWalls() {
        int x, y;
        numOfWalls = 0;

        x = 0;
        y = 0;
        while (x < mapWidth)        // walls on the top
        {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);

            x += wallSize;
            numOfWalls++;
        }

        x = 0;
        y = wallSize;
        while (y < mapHeight)        // walls on the left edge
        {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);

            y += wallSize;
            numOfWalls++;
        }

        x = mapWidth - wallSize;
        y = wallSize;
        while (y < mapHeight)        // walls on the right edge
        {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);

            y += wallSize;
            numOfWalls++;
        }

        x = 0;
        y = mapHeight - wallSize;
        while (x < mapWidth)        // walls on the bottom
        {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);

            x += wallSize;
            numOfWalls++;
        }

        y = 40;
        for (x = 40; x < 320; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 40;
        for (y = 80; y < 180; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 80;
        for (x = 60; x < 200; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 180;
        for (y = 100; y < 180; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 180;
        for (x = 40; x < 200; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 220;
        for (x = 20; x < 200; x += (wallSize * 2)) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 40;
        for (y = 260; y < 360; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 340;
        for (x = 60; x < 140; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 340;
        for (x = 400; x < 600; x += wallSize) {
            if (x != 500) {
                Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
                wall.setStroke(Color.CORNFLOWERBLUE);
                wall.setStrokeWidth(2.0);
                wallList.add(wall);
                pane.getChildren().add(wall);
                numOfWalls++;
            }
        }

        y = 300;
        for (x = 340; x < mapWidth; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 260;
        for (x = 80; x < 140; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 120;
        for (y = 280; y < 320; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 340;
        for (x = 200; x < 280; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }


        x = 340;
        for (y = 20; y < 160; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 380;
        for (y = 40; y < 180; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 420;
        for (y = 20; y < 160; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 460;
        for (y = 40; y < 180; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 500;
        for (y = 20; y < 160; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 540;
        for (y = 40; y < 180; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 180;
        for (x = 340; x < 560; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 80;
        for (x = 220; x < 320; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 260;
        for (y = 100; y < 220; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 220;
        for (y = 120; y < 200; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 220;
        for (x = 220; x < 320; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 300;
        for (y = 120; y < 200; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 300;
        for (y = 260; y < 360; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 340;
        for (x = 320; x < 380; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 160;
        for (y = 320; y < mapHeight; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 300;
        for (x = 160; x < 240; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 260;
        for (x = 160; x < 260; x += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        x = 260;
        for (y = 260; y < 360; y += wallSize) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 220;
        for (x = 340; x < mapWidth; x += (wallSize * 2)) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 240;
        for (x = 360; x < mapWidth - wallSize; x += (wallSize * 4)) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }

        y = 260;
        for (x = 340; x < mapWidth; x += (wallSize * 2)) {
            Rectangle wall = new Rectangle(x, y, wallSize, wallSize);        // pass in x, y, width and height
            wall.setStroke(Color.CORNFLOWERBLUE);
            wall.setStrokeWidth(2.0);
            wallList.add(wall);
            pane.getChildren().add(wall);
            numOfWalls++;
        }
    }

    public ArrayList<Rectangle> getWallList() {
        return wallList;
    }

    public ArrayList<Circle> getPelletList() {
        return pelletList;
    }

    public ArrayList<ImageView> getBonusList() {
        return bonusList;
    }

    public int getWallSize() {
        return wallSize;
    }
}
