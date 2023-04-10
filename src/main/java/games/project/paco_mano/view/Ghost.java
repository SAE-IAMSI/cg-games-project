package games.project.paco_mano.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Ghost {

    enum Dir {
        UP, DOWN, LEFT, RIGHT
    }

    public enum ColorGhost{
        CYAN, RED, ORANGE, PINK
    }
    private final Polygon triangle;
    private double x1, y1, x2, y2, x3, y3;
    private double centerX, centerY;                // these are the coordinates used to move the triange
    private final double width;
    private final double height;
    private final int size = 16;
    private Color color;
    private boolean transparent;

    private Dir movingAt;

    private ColorGhost colorGhost;

    private final int wallSize = 20;

    private final int speed = 10;

    public Ghost(Pane pane, double x, double y, double width, double height, ColorGhost cg)        // receive the layout, x, y coordinates and the width and height of the equilateral triangle
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
        colorGhost=cg;

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

    public void updateCoords() {
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

    private Dir getRandomDir() {
        Dir dirc = null;
        int num = (int) (Math.random() * 4);

        switch (num) {
            case 0 -> dirc = Dir.UP;
            case 1 -> dirc = Dir.DOWN;
            case 2 -> dirc = Dir.LEFT;
            case 3 -> dirc = Dir.RIGHT;
        }

        return dirc;
    }

    private Dir oppositeDir(Dir d) {
        Dir dir = null;

        if (d == Dir.UP)
            dir = Dir.DOWN;
        else if (d == Dir.DOWN)
            dir = Dir.UP;
        else if (d == Dir.LEFT)
            dir = Dir.RIGHT;
        else if (d == Dir.RIGHT)
            dir = Dir.LEFT;

        return dir;
    }

    public void move(Jeu jeu, PacMan pacMan, int radar) {
        Dir dontGo = null;

        if (pacMan.isBonusEaten() && this.getColor() == Color.BLUE)
            dontGo = pacmanAt(jeu, pacMan);        // the ghost must not go to this direction

        if (this.isTransparent())    // move the ghost out of the cell
        {
            switch(colorGhost){
                case PINK -> {
                    if (this.getY() < 200)
                        movingAt = Dir.DOWN;
                }
                case RED -> {
                    if (this.getX() < 210)
                        movingAt = Dir.RIGHT;
                }
                case CYAN -> {
                    if (this.getX() > 50)
                        movingAt = Dir.LEFT;

                }
                case ORANGE -> {
                    if (this.getY() > 70)
                        movingAt = Dir.UP;
                }
            }

                this.setTransparent(false);    // only when the ghost has passed the right border of the cell it loses its transparency
        } else if (collision(jeu, movingAt, this.getX(), this.getY()) && numOfTurns(jeu, movingAt, this.getX(), this.getY()) == 1) {                            // if the ghost runs to a dead end it goes in the direction opposite to its current direction
            movingAt = oppositeDir(movingAt);
        } else {
            for (; ; ) {
                if (this.getColor() != Color.BLUE) {
                    if (tailPacman(jeu, pacMan, radar) != null) {
                        movingAt = tailPacman(jeu, pacMan, radar);    // check if pacman is in red's radar. If pacman is 2 walls away from red, red will follow him
                        break;
                    }
                }

                // move in a random direction
                Dir direction = getRandomDir();
                if (!collision(jeu, direction, this.getX(), this.getY())) {
                    if (dontGo != null && direction != dontGo) {
                        movingAt = direction;
                        break;
                    } else if (direction != oppositeDir(movingAt)) {
                        movingAt = direction;
                        break;
                    }
                }
            }
        }

        if (movingAt == Dir.UP) {
            if (!collision(jeu, movingAt, this.getX(), this.getY()) || this.isTransparent())
                this.setY(this.getY() - speed);
        } else if (movingAt == Dir.DOWN) {
            if (!collision(jeu, movingAt, this.getX(), this.getY()) || this.isTransparent())
                this.setY(this.getY() + speed);
        } else if (movingAt == Dir.LEFT) {
            if (!collision(jeu, movingAt, this.getX(), this.getY()) || this.isTransparent())
                this.setX(this.getX() - speed);
        } else if (movingAt == Dir.RIGHT) {
            if (!collision(jeu, movingAt, this.getX(), this.getY()) || this.isTransparent())
                this.setX(this.getX() + speed);
        }
    }

    private boolean collision(Jeu jeu, Dir direction, double x, double y) {

        for (int i = 0; i < jeu.getArene().getMurs().getMurList().size(); i++) {
            double checkX = jeu.getArene().getMurs().getMurList().get(i).getX();
            double checkY = jeu.getArene().getMurs().getMurList().get(i).getY();

            if (direction == Dir.UP && checkY < y) {
                if ((x == checkX || (x < checkX + jeu.getArene().getMurs().getMurSize() && x > checkX)) && y - jeu.getArene().getMurs().getMurSize() <= checkY) {

                    return true;
                }
            } else if (direction == Dir.DOWN && checkY > y) {
                if ((x == checkX || (x < checkX + jeu.getArene().getMurs().getMurSize() && x > checkX)) && y + size >= checkY) {

                    return true;
                }
            } else if (direction == Dir.LEFT && checkX < x) {
                if (x - jeu.getArene().getMurs().getMurSize() <= checkX && (y == checkY || (y < checkY + jeu.getArene().getMurs().getMurSize() && y > checkY))) {

                    return true;
                }
            } else if (direction == Dir.RIGHT && checkX > x) {
                if (x + size >= checkX && (y == checkY || (y < checkY + jeu.getArene().getMurs().getMurSize() && y > checkY))) {

                    return true;
                }

            }
        }

        return false;
    }

    private Dir tailPacman(Jeu jeu, PacMan pacMan, int wallCount) {
        Dir direction = null;
        Dir pacmanDir = pacmanAt(jeu, pacMan);

        double pacmanY = pacMan.getPacmanY();
        double pacmanX =pacMan.getPacmanX();
// from the ghost's current position find out in which direction pacman is

        if (pacmanDir == Dir.DOWN && pacmanY - this.getY() <= (wallSize * wallCount)) {
            if (!checkForWallsBetween(jeu, this.getX(), this.getY(), pacmanX, pacmanY, Dir.DOWN)) direction = Dir.DOWN;
        } else if (pacmanDir == Dir.UP && this.getY() - pacmanY <= (wallSize * wallCount)) {
            if (!checkForWallsBetween(jeu, this.getX(), this.getY(), pacmanX, pacmanY, Dir.UP)) direction = Dir.UP;
        } else if (pacmanDir == Dir.LEFT && this.getX() - pacmanX <= (wallSize * wallCount)) {
            if (!checkForWallsBetween(jeu, this.getX(), this.getY(), pacmanX, pacmanY, Dir.LEFT)) direction = Dir.LEFT;
        } else if (pacmanDir == Dir.RIGHT && pacmanX - this.getX() <= (wallSize * wallCount)) {
            if (!checkForWallsBetween(jeu, this.getX(), this.getY(), pacmanX, pacmanY, Dir.RIGHT)) direction = Dir.RIGHT;
        }

        return direction;
    }

    // method that'll return the direction that points towards pacman from a ghost's current position
    private Dir pacmanAt(Jeu jeu, PacMan pacMan) {
        double x = this.getX();
        double y = this.getY();

        double pacmanY = pacMan.getPacmanY();
        double pacmanX =pacMan.getPacmanX();

        if (y == pacmanY && (pacmanX - x) > 0 && (pacmanX - x) < 100 && !checkForWallsBetween(jeu, x, y, pacmanX, pacmanY, Dir.RIGHT))
            return Dir.RIGHT;
        else if (y == pacmanY && (x - pacmanX) > 0 && (x - pacmanX) < 100 && !checkForWallsBetween(jeu, x, y, pacmanX, pacmanY, Dir.LEFT))
            return Dir.LEFT;
        else if (x == pacmanX && (pacmanY - y) > 0 && (pacmanY - y) < 100 && !checkForWallsBetween(jeu, x, y, pacmanX, pacmanY, Dir.DOWN))
            return Dir.DOWN;
        else if (x == pacmanX && (y - pacmanY) > 0 && (y - pacmanY) < 100 && !checkForWallsBetween(jeu, x, y, pacmanX, pacmanY, Dir.UP))
            return Dir.UP;

        return null;
    }

    private Boolean checkForWallsBetween(Jeu jeu, double from_x, double from_y, double to_x, double to_y, Dir direction) {
        boolean wall_present = false;

        if (direction == Dir.UP) {
            while (from_y > to_y && !wall_present) {
                wall_present = collision(jeu, direction, from_x, from_y);
                from_y -= wallSize;
            }
        } else if (direction == Dir.DOWN) {
            while (from_y < to_y && !wall_present) {
                wall_present = collision(jeu, direction, from_x, from_y);
                from_y += wallSize;
            }
        } else if (direction == Dir.LEFT) {
            while (from_x > to_x && !wall_present) {
                wall_present = collision(jeu, direction, from_x, from_y);
                from_x -= wallSize;
            }
        } else if (direction == Dir.RIGHT) {
            while (from_x < to_x && !wall_present) {
                wall_present = collision(jeu, direction, from_x, from_y);
                from_x += wallSize;
            }
        }

        return wall_present;
    }

    private Integer numOfTurns(Jeu jeu,Dir currentDir, double x, double y) {
        int numOfTurns = 0;

        if (currentDir != Dir.UP && !collision(jeu, Dir.UP, x, y)) numOfTurns++;

        if (currentDir != Dir.DOWN && !collision(jeu, Dir.DOWN, x, y)) numOfTurns++;

        if (currentDir != Dir.LEFT && !collision(jeu, Dir.LEFT, x, y)) numOfTurns++;

        if (currentDir != Dir.RIGHT && !collision(jeu, Dir.RIGHT, x, y)) numOfTurns++;

        return numOfTurns;
    }



}