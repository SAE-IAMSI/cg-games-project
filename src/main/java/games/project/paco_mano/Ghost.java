package games.project.paco_mano;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Ghost {
    private final Polygon triangle;
    private double x1, y1, x2, y2, x3, y3;
    private double centerX, centerY;                // these are the coordinates used to move the triange
    private final double width;
    private final double height;
    private Color color;
    private boolean transparent;

    public Ghost(Pane pane, double x, double y, double width, double height)        // receive the layout, x, y coordinates and the width and height of the equilateral triangle
    {
        // store the coordinates of the triangle's center point, its height and width
        this.centerX = x;
        this.centerY = y;
        this.width = width;
        this.height = height;

        // set the 3 coordinates of the equilateral triangle
        this.x1 = x - (width / 2);
        this.y1 = y - (height / 2);
        this.x2 = x + (width / 2);
        this.y2 = y - (height / 2);
        this.x3 = x;
        this.y3 = y + (height / 2);

        // create the triangle using the 3 coordinates
        triangle = new Polygon();
        triangle.getPoints().addAll(x1, y1,
                x2, y2,
                x3, y3);

        this.transparent = false;
        pane.getChildren().add(triangle);
    }

    public void setColor(Color c) {
        this.color = c;
        this.triangle.setFill(c);
    }


    public Color getColor() {
        return this.color;
    }

    public Double getX() {
        return this.centerX;
    }

    public Double getY() {
        return this.centerY;
    }

    public void setX(double x_coord) {
        double moving = x_coord - this.centerX;

        this.x1 = this.x1 + moving;
        this.x2 = this.x2 + moving;
        this.x3 = this.x3 + moving;
        this.centerX = x_coord;

        updateCoords();
    }

    public void setY(double y_coord) {
        double moving = y_coord - this.centerY;

        this.y1 = this.y1 + moving;
        this.y2 = this.y2 + moving;
        this.y3 = this.y3 + moving;
        this.centerY = y_coord;

        updateCoords();
    }

    private void updateCoords() {
        triangle.getPoints().clear();                // get rid of all the current coordinates
        triangle.getPoints().addAll(this.x1, this.y1,
                this.x2, this.y2,
                this.x3, this.y3);
    }

    public void setTransparent(boolean b) {
        this.transparent = b;
    }

    public Boolean isTransparent() {
        return this.transparent;
    }

    public Boolean caughtPacman(Circle pacman, int speed)        // will check if the ghost has caught pacman depending on the speed
    {
        if ((this.centerX - speed == pacman.getCenterX() && this.centerY == pacman.getCenterY()) || (this.centerX + speed == pacman.getCenterX() && this.centerY == pacman.getCenterY()))
            return true;
        if ((this.centerX == pacman.getCenterX() && this.centerY - speed == pacman.getCenterY()) || (this.centerX == pacman.getCenterX() && this.centerY + speed == pacman.getCenterY()))
            return true;
        return this.centerX == pacman.getCenterX() && this.centerY == pacman.getCenterY();
    }
}