package games.project.paco_mano;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


// javac --module-path "%PATH_TO_FX%" --add-modules javafx.controls Game.java
// java --module-path "%PATH_TO_FX%" --add-modules javafx.controls Game


public class PacoMano extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    enum Dir {
        UP, DOWN, LEFT, RIGHT;
    }

    private final int mapWidth = 600;
    private final int mapHeight = 400;
    private final int size = 8;                // refers to the radius of the circle
    private final int speed = 10;                // both, pacman and the ghosts move with the same speed
    private final int ghostSize = size * 2;        // the width & height of the ghosts are equal to pacman's diameter
    private final int wallSize = 20;
    private boolean bonusEaten;
    private double pacmanX = 280;
    private double pacmanY = 30;
    private double pacmanTurnedAt_x, pacmanTurnedAt_y;        // holds the coordinates of the point where pacman made a turn
    private int numOfWalls;            // keeps track of the number of walls created
    private int score, highScore;

    private Stage window;
    private Scene scene;
    private Pane pane;
    private Circle pacman;
    private Ghost redGhost, pinkGhost, orangeGhost, cyanGhost;
    private KeyFrame pacman_keyFrame;
    private Timeline timeline;
    private Label scoreLabel, highLabel;

    private ArrayList<Rectangle> wallList = new ArrayList<Rectangle>();
    private ArrayList<Circle> pelletList = new ArrayList<Circle>();
    private ArrayList<Circle> bonusList = new ArrayList<Circle>();
    private Dir red_movingAt, pink_movingAt, orange_movingAt, cyan_movingAt;
    private Dir movingAt, newDir;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("P A C M A N");
        window.setResizable(false);

        pane = new Pane();
        pane.setStyle("-fx-background-color : black");

        initialize();

        scoreLabel = new Label();
        scoreLabel.setPrefWidth(130);

        highLabel = new Label("High Score : " + Integer.toString(highScore));
        highLabel.setPrefWidth(150);

        HBox h_box = new HBox(250);
        h_box.setPadding(new Insets(0, 5, 0, 5));
        h_box.getChildren().addAll(scoreLabel, highLabel);

        VBox vbox = new VBox(h_box, pane);

        scene = new Scene(vbox, mapWidth, mapHeight + 20);
        scene.getStylesheets().add("styles.css");
        scene.setOnKeyPressed(event ->
        {
            switch (event.getCode()) {
                case UP:
                    newDir = Dir.UP;
                    break;

                case DOWN:
                    newDir = Dir.DOWN;
                    break;

                case LEFT:
                    newDir = Dir.LEFT;
                    break;

                case RIGHT:
                    newDir = Dir.RIGHT;
                    break;
            }
        });

        play();

        window.setScene(scene);
        window.show();
    }

    // pacman and the ghosts are born in this method. The walls are created here, too
    private void initialize() {
        drawWalls();
        drawBonusFood();
        drawPellets();

        pacman = new Circle();                    // create pacman
        pacman.setRadius(size);
        pacman.setFill(Color.YELLOW);
        pacman.setCenterX(pacmanX);
        pacman.setCenterY(pacmanY);
        pane.getChildren().add(pacman);

        redGhost = new Ghost(pane, 120, 110, ghostSize, ghostSize);            // create the red ghost
        redGhost.setColor(Color.RED);
        setTimerForTransparency(redGhost, 5);

        pinkGhost = new Ghost(pane, 120, 150, ghostSize, ghostSize);            // create the pink ghost
        pinkGhost.setColor(Color.PINK);
        setTimerForTransparency(pinkGhost, 15);

        orangeGhost = new Ghost(pane, 100, 120, ghostSize, ghostSize);            // create the orange ghost
        orangeGhost.setColor(Color.ORANGE);
        setTimerForTransparency(orangeGhost, 30);

        cyanGhost = new Ghost(pane, 80, 100, ghostSize, ghostSize);            // create the cyan ghost
        cyanGhost.setColor(Color.CYAN);
        setTimerForTransparency(cyanGhost, 40);

        highScore = Integer.parseInt(getHighScore());
    }

    private void play() {
        pacman_keyFrame = new KeyFrame(Duration.millis(110), e ->
        {
            blinkBonus();

            if (bonusEaten)
                ateGhost();
            else            // check whether any of the ghosts have caught pacman
                if (redGhost.caughtPacman(pacman, speed) || pinkGhost.caughtPacman(pacman, speed) || orangeGhost.caughtPacman(pacman, speed) || cyanGhost.caughtPacman(pacman, speed))
                    endGame();

            // only when pacman cannot continue in its current direction, its direction is updated
            if (checkForWalls(newDir, pacmanX, pacmanY) == false) {
                if (movingAt != newDir)        // when pacman makes a turn, record the coordinates of the position he turned at
                {
                    pacmanTurnedAt_x = pacman.getCenterX();
                    pacmanTurnedAt_y = pacman.getCenterY();
                }

                movingAt = newDir;
            }

            if (movingAt == Dir.UP) {
                if (checkForWalls(movingAt, pacmanX, pacmanY) == false)
                    pacman.setCenterY(pacmanY - speed);
            } else if (movingAt == Dir.DOWN) {
                if (checkForWalls(movingAt, pacmanX, pacmanY) == false)
                    pacman.setCenterY(pacmanY + speed);
            } else if (movingAt == Dir.LEFT) {
                if (checkForWalls(movingAt, pacmanX, pacmanY) == false)
                    pacman.setCenterX(pacmanX - speed);
            } else if (movingAt == Dir.RIGHT) {
                if (checkForWalls(movingAt, pacmanX, pacmanY) == false)
                    pacman.setCenterX(pacmanX + speed);
            }

            ateFood(pacmanX, pacmanY);
            ateBonus(pacmanX, pacmanY);

            // move the ghosts
            moveRed();
            movePink();
            moveOrange();
            moveCyan();

            // check if all the pellets & bonus food have been eaten and then end the game
            if (pelletList.isEmpty() == true && bonusList.isEmpty()) endGame();

            scoreLabel.setText("Score : " + Integer.toString(score));

            // update pacman's coordinates
            pacmanX = pacman.getCenterX();
            pacmanY = pacman.getCenterY();
        });

        timeline = new Timeline(pacman_keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // method to move the red ghost
    private void moveRed() {
        Dir dontGo = null;

        if (bonusEaten && redGhost.getColor() == Color.BLUE)
            dontGo = pacmanAt(redGhost);        // the ghost must not go to this direction

        if (redGhost.isTransparent() == true)    // move the ghost out of the cell
        {
            if (redGhost.getX() < 210)
                red_movingAt = Dir.RIGHT;
            else
                redGhost.setTransparent(false);    // only when the ghost has passed the right border of the cell it loses its transparency
        } else if (checkForWalls(red_movingAt, redGhost.getX(), redGhost.getY()) == true && numOfTurns(red_movingAt, redGhost.getX(), redGhost.getY()) == 1) {                            // if the ghost runs to a dead end it goes in the direction opposite to its current direction
            red_movingAt = oppositeDir(red_movingAt);
        } else {
            for (; ; ) {
                if (redGhost.getColor() != Color.BLUE) {
                    if (tailPacman(redGhost, 2) != null) {
                        red_movingAt = tailPacman(redGhost, 2);    // check if pacman is in red's radar. If pacman is 2 walls away from red, red will follow him
                        break;
                    }
                }

                // move in a random direction
                Dir direction = getRandomDir();
                if (checkForWalls(direction, redGhost.getX(), redGhost.getY()) == false) {
                    if (dontGo != null && direction != dontGo) {
                        red_movingAt = direction;
                        break;
                    } else if (direction != oppositeDir(red_movingAt)) {
                        red_movingAt = direction;
                        break;
                    }
                }
            }
        }

        if (red_movingAt == Dir.UP) {
            if (checkForWalls(red_movingAt, redGhost.getX(), redGhost.getY()) == false || redGhost.isTransparent() == true)
                redGhost.setY(redGhost.getY() - speed);
        } else if (red_movingAt == Dir.DOWN) {
            if (checkForWalls(red_movingAt, redGhost.getX(), redGhost.getY()) == false || redGhost.isTransparent() == true)
                redGhost.setY(redGhost.getY() + speed);
        } else if (red_movingAt == Dir.LEFT) {
            if (checkForWalls(red_movingAt, redGhost.getX(), redGhost.getY()) == false || redGhost.isTransparent() == true)
                redGhost.setX(redGhost.getX() - speed);
        } else if (red_movingAt == Dir.RIGHT) {
            if (checkForWalls(red_movingAt, redGhost.getX(), redGhost.getY()) == false || redGhost.isTransparent() == true)
                redGhost.setX(redGhost.getX() + speed);
        }
    }

    // method to move the pink ghost
    private void movePink() {
        Dir dontGo = null;

        if (bonusEaten && pinkGhost.getColor() == Color.BLUE)
            dontGo = pacmanAt(pinkGhost);        // the ghost must not go to this direction


        if (pinkGhost.isTransparent() == true)    // move the ghost out of the cell
        {
            if (pinkGhost.getY() < 200)
                pink_movingAt = Dir.DOWN;
            else
                pinkGhost.setTransparent(false);    // only when the ghost has passed the bottom border of the cell it loses its transparency
        } else if (checkForWalls(pink_movingAt, pinkGhost.getX(), pinkGhost.getY()) == true && numOfTurns(pink_movingAt, pinkGhost.getX(), pinkGhost.getY()) == 1) {                            // if the ghost runs to a dead end it goes in the direction opposite to its current direction
            pink_movingAt = oppositeDir(pink_movingAt);
        } else {
            for (; ; ) {
                if (pinkGhost.getColor() != Color.BLUE) {
                    if (tailPacman(pinkGhost, 4) != null) {
                        pink_movingAt = tailPacman(pinkGhost, 4);    // check if pacman is in pink's radar. If pacman is 4 walls away from pink, pink will follow him
                        break;
                    }
                }

                // move in a random direction
                Dir direction = getRandomDir();
                if (checkForWalls(direction, pinkGhost.getX(), pinkGhost.getY()) == false) {
                    if (dontGo != null && direction != dontGo) {
                        pink_movingAt = direction;
                        break;
                    } else if (direction != oppositeDir(pink_movingAt)) {
                        pink_movingAt = direction;
                        break;
                    }
                }
            }
        }

        if (pink_movingAt == Dir.UP) {
            if (checkForWalls(pink_movingAt, pinkGhost.getX(), pinkGhost.getY()) == false || pinkGhost.isTransparent() == true)
                pinkGhost.setY(pinkGhost.getY() - speed);
        } else if (pink_movingAt == Dir.DOWN) {
            if (checkForWalls(pink_movingAt, pinkGhost.getX(), pinkGhost.getY()) == false || pinkGhost.isTransparent() == true)
                pinkGhost.setY(pinkGhost.getY() + speed);
        } else if (pink_movingAt == Dir.LEFT) {
            if (checkForWalls(pink_movingAt, pinkGhost.getX(), pinkGhost.getY()) == false || pinkGhost.isTransparent() == true)
                pinkGhost.setX(pinkGhost.getX() - speed);
        } else if (pink_movingAt == Dir.RIGHT) {
            if (checkForWalls(pink_movingAt, pinkGhost.getX(), pinkGhost.getY()) == false || pinkGhost.isTransparent() == true)
                pinkGhost.setX(pinkGhost.getX() + speed);
        }
    }

    // method to move the orange ghost
    private void moveOrange() {
        Dir dontGo = null;

        if (bonusEaten && orangeGhost.getColor() == Color.BLUE)
            dontGo = pacmanAt(orangeGhost);        // the ghost must not go to this direction

        if (orangeGhost.isTransparent() == true)    // move the ghost out of the cell
        {
            if (orangeGhost.getY() > 70)
                orange_movingAt = Dir.UP;
            else
                orangeGhost.setTransparent(false);    // only when the ghost has passed the top border of the cell it loses its transparency
        } else if (checkForWalls(orange_movingAt, orangeGhost.getX(), orangeGhost.getY()) == true && numOfTurns(orange_movingAt, orangeGhost.getX(), orangeGhost.getY()) == 1) {                            // if the ghost runs to a dead end it goes in the direction opposite to its current direction
            orange_movingAt = oppositeDir(orange_movingAt);
        } else {
            for (; ; ) {
                if (orangeGhost.getColor() != Color.BLUE) {
                    if (tailPacman(orangeGhost, 7) != null) {
                        orange_movingAt = tailPacman(orangeGhost, 7);    // check if pacman is in orange's radar. If pacman is 7 walls away from orange, orange will follow him
                        break;
                    }
                }

                // move in a random direction
                Dir direction = getRandomDir();
                if (checkForWalls(direction, orangeGhost.getX(), orangeGhost.getY()) == false) {
                    if (dontGo != null && direction != dontGo) {
                        orange_movingAt = direction;
                        break;
                    } else if (direction != oppositeDir(orange_movingAt)) {
                        orange_movingAt = direction;
                        break;
                    }
                }
            }
        }

        if (orange_movingAt == Dir.UP) {
            if (checkForWalls(orange_movingAt, orangeGhost.getX(), orangeGhost.getY()) == false || orangeGhost.isTransparent() == true)
                orangeGhost.setY(orangeGhost.getY() - speed);
        } else if (orange_movingAt == Dir.DOWN) {
            if (checkForWalls(orange_movingAt, orangeGhost.getX(), orangeGhost.getY()) == false || orangeGhost.isTransparent() == true)
                orangeGhost.setY(orangeGhost.getY() + speed);
        } else if (orange_movingAt == Dir.LEFT) {
            if (checkForWalls(orange_movingAt, orangeGhost.getX(), orangeGhost.getY()) == false || orangeGhost.isTransparent() == true)
                orangeGhost.setX(orangeGhost.getX() - speed);
        } else if (orange_movingAt == Dir.RIGHT) {
            if (checkForWalls(orange_movingAt, orangeGhost.getX(), orangeGhost.getY()) == false || orangeGhost.isTransparent() == true)
                orangeGhost.setX(orangeGhost.getX() + speed);
        }
    }

    private void moveCyan() {
        Dir dontGo = null;

        if (bonusEaten && cyanGhost.getColor() == Color.BLUE)
            dontGo = pacmanAt(cyanGhost);        // the ghost must not go to this direction

        if (cyanGhost.isTransparent() == true)    // move the ghost out of the cell
        {
            if (cyanGhost.getX() > 50)
                cyan_movingAt = Dir.LEFT;
            else
                cyanGhost.setTransparent(false);    // only when the ghost has passed the left border of the cell it loses its transparency
        } else if (checkForWalls(cyan_movingAt, cyanGhost.getX(), cyanGhost.getY()) == true && numOfTurns(cyan_movingAt, cyanGhost.getX(), cyanGhost.getY()) == 1) {                            // if the ghost runs to a dead end it goes in the direction opposite to its current direction
            cyan_movingAt = oppositeDir(cyan_movingAt);
        } else {
            for (; ; ) {
                if (cyanGhost.getColor() != Color.BLUE) {
                    if (tailPacman(cyanGhost, 5) != null)        // check if pacman is in cyan's radar. If pacman is 5 walls away from cyan, cyan will follow him
                    {
                        cyan_movingAt = tailPacman(cyanGhost, 5);
                        break;
                    }
                }

                Dir direction = getRandomDir();
                if (checkForWalls(direction, cyanGhost.getX(), cyanGhost.getY()) == false) {
                    if (dontGo != null && direction != dontGo) {
                        cyan_movingAt = direction;
                        break;
                    } else if (direction != oppositeDir(cyan_movingAt)) {
                        cyan_movingAt = direction;
                        break;
                    }
                }
            }
        }

        if (cyan_movingAt == Dir.UP) {
            if (checkForWalls(cyan_movingAt, cyanGhost.getX(), cyanGhost.getY()) == false || cyanGhost.isTransparent() == true)
                cyanGhost.setY(cyanGhost.getY() - speed);
        } else if (cyan_movingAt == Dir.DOWN) {
            if (checkForWalls(cyan_movingAt, cyanGhost.getX(), cyanGhost.getY()) == false || cyanGhost.isTransparent() == true)
                cyanGhost.setY(cyanGhost.getY() + speed);
        } else if (cyan_movingAt == Dir.LEFT) {
            if (checkForWalls(cyan_movingAt, cyanGhost.getX(), cyanGhost.getY()) == false || cyanGhost.isTransparent() == true)
                cyanGhost.setX(cyanGhost.getX() - speed);
        } else if (cyan_movingAt == Dir.RIGHT) {
            if (checkForWalls(cyan_movingAt, cyanGhost.getX(), cyanGhost.getY()) == false || cyanGhost.isTransparent() == true)
                cyanGhost.setX(cyanGhost.getX() + speed);
        }
    }

    // check if pacman is in the ghost's radar. If pacman is "wallCount" walls away from the ghost, this method will return the direction that leads to pacman
    private Dir tailPacman(Ghost ghost, int wallCount) {
        Dir direction = null;
        double ghostX = ghost.getX();
        double ghostY = ghost.getY();
        Dir pacmanDir = pacmanAt(ghost);        // from the ghost's current position find out in which direction pacman is

        if (pacmanDir == Dir.DOWN && pacmanY - ghostY <= (wallSize * wallCount)) {
            if (checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Dir.DOWN) == false) direction = Dir.DOWN;
        } else if (pacmanDir == Dir.UP && ghostY - pacmanY <= (wallSize * wallCount)) {
            if (checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Dir.UP) == false) direction = Dir.UP;
        } else if (pacmanDir == Dir.LEFT && ghostX - pacmanX <= (wallSize * wallCount)) {
            if (checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Dir.LEFT) == false) direction = Dir.LEFT;
        } else if (pacmanDir == Dir.RIGHT && pacmanX - ghostX <= (wallSize * wallCount)) {
            if (checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Dir.RIGHT) == false) direction = Dir.RIGHT;
        }

        return direction;
    }

    // method that'll return the direction that points towards pacman from a ghost's current position
    private Dir pacmanAt(Ghost ghost) {
        double x = ghost.getX();
        double y = ghost.getY();

        if (y == pacmanY && (pacmanX - x) > 0 && (pacmanX - x) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Dir.RIGHT) == false)
            return Dir.RIGHT;
        else if (y == pacmanY && (x - pacmanX) > 0 && (x - pacmanX) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Dir.LEFT) == false)
            return Dir.LEFT;
        else if (x == pacmanX && (pacmanY - y) > 0 && (pacmanY - y) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Dir.DOWN) == false)
            return Dir.DOWN;
        else if (x == pacmanX && (y - pacmanY) > 0 && (y - pacmanY) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Dir.UP) == false)
            return Dir.UP;

        return null;
    }

    private void setTimerForTransparency(Ghost ghost, long delay) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ghost.setTransparent(true);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, (1000 * delay));
    }

    // this method sets timer for how long the ghosts stay blue. Once the time is reached, the ghosts return to their original colours
    private void setTimerForGhosts() {
        TimerTask tsk = new TimerTask() {
            @Override
            public void run() {
                redGhost.setColor(Color.RED);
                pinkGhost.setColor(Color.PINK);
                orangeGhost.setColor(Color.ORANGE);
                cyanGhost.setColor(Color.CYAN);
                bonusEaten = false;
            }
        };

        Timer tmr = new Timer();
        tmr.schedule(tsk, (1000 * 10));            // schedule it for 10 seconds from the time it's called
    }

    // method that will return the number(1 to 3) of possible turns a ghost can make excluding the direction its already moving
    private Integer numOfTurns(Dir currentDir, double x, double y) {
        int numOfTurns = 0;

        if (currentDir != Dir.UP && checkForWalls(Dir.UP, x, y) == false) numOfTurns++;

        if (currentDir != Dir.DOWN && checkForWalls(Dir.DOWN, x, y) == false) numOfTurns++;

        if (currentDir != Dir.LEFT && checkForWalls(Dir.LEFT, x, y) == false) numOfTurns++;

        if (currentDir != Dir.RIGHT && checkForWalls(Dir.RIGHT, x, y) == false) numOfTurns++;

        return numOfTurns;
    }

    // method to check for an incoming wall specific to the direction
    private Boolean checkForWalls(Dir theDir, double x_coord, double y_coord) {
        x_coord = x_coord - speed;
        y_coord = y_coord - speed;

        for (int counter = 0; counter < wallList.size(); counter++) {
            double checkX = wallList.get(counter).getX();
            double checkY = wallList.get(counter).getY();

            if (theDir == Dir.UP && checkY < y_coord) {
                if ((x_coord == checkX || (x_coord < checkX + wallSize && x_coord > checkX) || x_coord + speed == checkX) && y_coord - wallSize <= checkY)
                    return true;
            } else if (theDir == Dir.DOWN && checkY > y_coord) {
                if ((x_coord == checkX || (x_coord < checkX + wallSize && x_coord > checkX) || x_coord + speed == checkX) && y_coord + wallSize >= checkY)
                    return true;
            } else if (theDir == Dir.LEFT && checkX < x_coord) {
                if (x_coord - wallSize <= checkX && (y_coord == checkY || (y_coord < checkY + wallSize && y_coord > checkY) || y_coord + speed == checkY))
                    return true;
            } else if (theDir == Dir.RIGHT && checkX > x_coord) {
                if (x_coord + wallSize >= checkX && (y_coord == checkY || (y_coord < checkY + wallSize && y_coord > checkY) || y_coord + speed == checkY))
                    return true;
            }
        }

        return false;
    }

    // method that'll check for walls between 2 positions in a specific direction
    private Boolean checkForWallsBetween(double from_x, double from_y, double to_x, double to_y, Dir direction) {
        boolean wall_present = false;

        if (direction == Dir.UP) {
            while (from_y > to_y && wall_present == false) {
                wall_present = checkForWalls(direction, from_x, from_y);
                from_y -= wallSize;
            }
        } else if (direction == Dir.DOWN) {
            while (from_y < to_y && wall_present == false) {
                wall_present = checkForWalls(direction, from_x, from_y);
                from_y += wallSize;
            }
        } else if (direction == Dir.LEFT) {
            while (from_x > to_x && wall_present == false) {
                wall_present = checkForWalls(direction, from_x, from_y);
                from_x -= wallSize;
            }
        } else if (direction == Dir.RIGHT) {
            while (from_x < to_x && wall_present == false) {
                wall_present = checkForWalls(direction, from_x, from_y);
                from_x += wallSize;
            }
        }

        return wall_present;
    }

    // returns the opposite direction of the specified dirrection
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

    // method that'll return a random direction
    private Dir getRandomDir() {
        Dir dirc = null;
        int num = (int) (Math.random() * 4);

        switch (num) {
            case 0:
                dirc = Dir.UP;
                break;
            case 1:
                dirc = Dir.DOWN;
                break;
            case 2:
                dirc = Dir.LEFT;
                break;
            case 3:
                dirc = Dir.RIGHT;
                break;
        }

        return dirc;
    }

    // method to check if pacman ate a pellet
    private Boolean ateFood(double x, double y) {
        for (int n = 0; n < pelletList.size(); n++) {
            if (pelletList.get(n).getCenterX() == x && pelletList.get(n).getCenterY() == y) {
                pane.getChildren().remove(pelletList.get(n));
                pelletList.remove(n);
                score += 10;                // increment the player's score by 10
                return true;
            }
        }

        return false;
    }

    // method to check if pacman ate a bonus food
    private Boolean ateBonus(double x, double y) {
        for (int n = 0; n < bonusList.size(); n++) {
            if (bonusList.get(n).getCenterX() == x && bonusList.get(n).getCenterY() == y) {
                pane.getChildren().remove(bonusList.get(n));
                bonusList.remove(n);
                score += 20;                // increment the player's score by 20
                bonusEaten = true;

                // set the colors of the ghosts to BLUE
                redGhost.setColor(Color.BLUE);
                pinkGhost.setColor(Color.BLUE);
                orangeGhost.setColor(Color.BLUE);
                cyanGhost.setColor(Color.BLUE);

                sleep(0.1);
                setTimerForGhosts();
                return true;
            }
        }

        return false;
    }

    // method that'll check if pacman ate a ghost
    private void ateGhost() {
        if (pacmanX == redGhost.getX() && (pacmanY + speed == redGhost.getY() || pacmanY - speed == redGhost.getY())) {
            redGhost.setX(120);
            redGhost.setY(170);
            redGhost.setColor(Color.RED);
            setTimerForTransparency(redGhost, 5);
            score += 50;
        } else if ((pacmanX - speed == redGhost.getX() || pacmanX + speed == redGhost.getX()) && pacmanY == redGhost.getY()) {
            redGhost.setX(120);
            redGhost.setY(170);
            redGhost.setColor(Color.RED);
            setTimerForTransparency(redGhost, 5);
            score += 50;
        } else if (pacmanX == redGhost.getX() && pacmanY == redGhost.getY()) {
            redGhost.setX(120);
            redGhost.setY(170);
            redGhost.setColor(Color.RED);
            setTimerForTransparency(redGhost, 5);
            score += 50;
        }

        if (pacmanX == pinkGhost.getX() && (pacmanY + speed == pinkGhost.getY() || pacmanY - speed == pinkGhost.getY())) {
            pinkGhost.setX(120);
            pinkGhost.setY(150);
            pinkGhost.setColor(Color.PINK);
            setTimerForTransparency(pinkGhost, 10);
            score += 50;
        } else if ((pacmanX - speed == pinkGhost.getX() || pacmanX + speed == pinkGhost.getX()) && pacmanY == pinkGhost.getY()) {
            pinkGhost.setX(120);
            pinkGhost.setY(150);
            pinkGhost.setColor(Color.PINK);
            setTimerForTransparency(pinkGhost, 10);
            score += 50;
        } else if (pacmanX == pinkGhost.getX() && pacmanY == pinkGhost.getY()) {
            pinkGhost.setX(120);
            pinkGhost.setY(150);
            pinkGhost.setColor(Color.PINK);
            setTimerForTransparency(pinkGhost, 10);
            score += 50;
        }

        if (pacmanX == orangeGhost.getX() && (pacmanY + speed == orangeGhost.getY() || pacmanY - speed == orangeGhost.getY())) {
            orangeGhost.setX(120);
            orangeGhost.setY(110);
            orangeGhost.setColor(Color.ORANGE);
            setTimerForTransparency(orangeGhost, 20);
            score += 50;
        } else if ((pacmanX - speed == orangeGhost.getX() || pacmanX + speed == orangeGhost.getX()) && pacmanY == orangeGhost.getY()) {
            orangeGhost.setX(120);
            orangeGhost.setY(110);
            orangeGhost.setColor(Color.ORANGE);
            setTimerForTransparency(orangeGhost, 20);
            score += 50;
        } else if (pacmanX == orangeGhost.getX() && pacmanY == orangeGhost.getY()) {
            orangeGhost.setX(120);
            orangeGhost.setY(110);
            orangeGhost.setColor(Color.ORANGE);
            setTimerForTransparency(orangeGhost, 20);
            score += 50;
        }

        if (pacmanX == cyanGhost.getX() && (pacmanY + speed == cyanGhost.getY() || pacmanY - speed == cyanGhost.getY())) {
            cyanGhost.setX(80);
            cyanGhost.setY(130);
            cyanGhost.setColor(Color.CYAN);
            setTimerForTransparency(cyanGhost, 25);
            score += 50;
        } else if ((pacmanX - speed == cyanGhost.getX() || pacmanX + speed == cyanGhost.getX()) && pacmanY == cyanGhost.getY()) {
            cyanGhost.setX(80);
            cyanGhost.setY(130);
            cyanGhost.setColor(Color.CYAN);
            setTimerForTransparency(cyanGhost, 25);
            score += 50;
        } else if (pacmanX == cyanGhost.getX() && pacmanY == cyanGhost.getY()) {
            cyanGhost.setX(80);
            cyanGhost.setY(130);
            cyanGhost.setColor(Color.CYAN);
            setTimerForTransparency(cyanGhost, 25);
            score += 50;
        }
    }

    private void blinkBonus() {
        for (int index = 0; index < bonusList.size(); index++) {
            Circle food = bonusList.get(index);

            if (food.getFill() == Color.WHITE)
                food.setFill(Color.BLACK);
            else
                food.setFill(Color.WHITE);
        }
    }

    private void endGame() {
        sleep(3);

        if (score > highScore) setHighScore(Integer.toString(score));

        Runtime.getRuntime().exit(0);            // terminate the program
    }

    private void sleep(double num)        // a method to put the system to sleep
    {
        try {
            Thread.sleep((long) (1000 * num));    // putting the system to sleep for 'num' seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getHighScore() {
        String s = "0";
        String filePath = new File("").getAbsolutePath();

        try {
            File file = new File(filePath.concat("\\high_score.txt"));

            if (file.exists()) {
                Scanner scan = new Scanner(file);
                s = scan.nextLine();
            } else {
                // create a the high_score.txt file and insert a 0
                setHighScore("0");
            }
        } catch (Exception e) {
            System.out.println("Failed to retrieve the high score");
        }

        return s;
    }


    private Boolean setHighScore(String newScore) {
        boolean highScoreUpdated = false;
        try {
            FileWriter writer = new FileWriter("high_score.txt");
            writer.write(newScore);
            writer.close();

            highScoreUpdated = true;
        } catch (Exception e) {
            System.out.println("Failed to set the new high score");
        }

        return highScoreUpdated;
    }


    // method to create pellets, store them in an ArrayList and put them on the pane
    private void drawPellets() {
        int x, y, num = 0;

        for (x = 30; x < mapWidth; x += wallSize) {
            for (y = 30; y < mapHeight; y += wallSize) {
                if (isAWall(x - (wallSize / 2), y - (wallSize / 2)) == false && !isABonusFood(x, y) && (x < 50 || x > 190 || y < 90 || y > 200)) {
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
    private void drawBonusFood() {
        int x, y;

        for (y = 30; y < mapHeight; y += (mapHeight - (wallSize * 3))) {
            for (x = 30; x < mapWidth; x += (mapWidth - (wallSize * 3))) {
                Circle c = new Circle();
                c.setRadius(4);
                c.setCenterX(x);
                c.setCenterY(y);
                c.setFill(Color.WHITE);
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
            circ.setFill(Color.WHITE);
            pane.getChildren().add(circ);
            bonusList.add(circ);
        }

        Circle cr = new Circle();
        cr.setRadius(4);
        cr.setCenterX(90);
        cr.setCenterY(310);
        cr.setFill(Color.WHITE);
        pane.getChildren().add(cr);
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

    // method to make the walls
    private void drawWalls() {
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
}
