package games.project.paco_mano.view;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PacMan  {

    enum Dir {
        UP, DOWN, LEFT, RIGHT
    }

    private Circle circle;
    private int pacmanX;

    private int pacmanY;

    private boolean bonusEaten;


    private Dir movingAt;
    private Dir newDir;
    private Dir oldDir;

    private double pacmanTurnedAt_x;

    private double pacmanTurnedAt_y;

    private final int speed = 10;

    private final int size = 8;

    public boolean isBonusEaten() {
        return bonusEaten;
    }

    public int getPacmanX() {
        return pacmanX;
    }

    public int getPacmanY() {
        return pacmanY;
    }

    public void setNewDir(Dir newDir) {
        this.newDir = newDir;
    }

    public PacMan(){
        circle = new Circle();
        circle.setRadius(7);
        circle.setFill(Color.YELLOW);
    }

    public void makePacMan(Jeu jeu){
        jeu.getChildren().add(circle);
    }

    public void move(Jeu jeu){
        // only when pacman cannot continue in its current direction, its direction is updated
        if (!collision(jeu, newDir, pacmanX, pacmanY)) {
            if (movingAt != newDir)        // when pacman makes a turn, record the coordinates of the position he turned at
            {
                pacmanTurnedAt_x = circle.getCenterX();
                pacmanTurnedAt_y = circle.getCenterY();
            }

            movingAt = newDir;
        }

        if (movingAt == Dir.UP) {
            if (!collision(jeu,movingAt, pacmanX, pacmanY))
                circle.setCenterY(pacmanY - speed);
        } else if (movingAt == Dir.DOWN) {
            if (!collision(jeu,movingAt, pacmanX, pacmanY))
                circle.setCenterY(pacmanY + speed);
        } else if (movingAt == Dir.LEFT) {
            if (!collision(jeu,movingAt, pacmanX, pacmanY))
                circle.setCenterX(pacmanX - speed);
        } else if (movingAt == Dir.RIGHT) {
            if (!collision(jeu,movingAt, pacmanX, pacmanY))
                circle.setCenterX(pacmanX + speed);
        }
    }

    private boolean collision(Jeu jeu, PacMan.Dir direction, double x, double y) {

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

}
