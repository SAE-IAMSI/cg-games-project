package games.project.motron.view;

import games.project.motron.Motron;
import games.project.motron.metier.entite.Score;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class VueMoto {

    enum Dir {
        UP, DOWN, LEFT, RIGHT
    }

    private final String motoName;
    private String nomJoueur;
    private Rectangle moto;
    private final int size = 20;
    private double motoX = 100;
    private double motoY = 400;
    private final int speed = 20;
    private final boolean trail = true; //false pour le mode activer/désactiver
    private boolean gameOver = false;
    private VueMoto adversaire;
    private int nbMorts;
    private boolean stop;

    private Dir movingAt;
    private Dir newDir;
    private Dir oldDir;
    private final MediaPlayer deathSound;
    private final Score score;
    private int scoreManche;
    private VueSprite sprite;
    private boolean connecter;

    private int nbBlocParcouru;


    //Constructeur--------------------------------

    public VueMoto(String motoName, VueSprite skin, String nomJoueur) {
        this.motoName = motoName;
        this.nomJoueur = nomJoueur;
        this.sprite = skin;
        this.nbMorts = 0;
        Media soundDeath = new Media(String.valueOf(Motron.class.getResource("music/Explosion8bit.mp3")));
        this.deathSound = new MediaPlayer(soundDeath);
        deathSound.setVolume(0.25);
        this.score = new Score(nomJoueur);
        connecter = false;
    }

    //-------------------------------------------

    //Getters & setters----------------------------


    public int getNbBlocParcouru() {
        return nbBlocParcouru;
    }

    public void setNbBlocParcouru(int nbBlocParcouru) {
        this.nbBlocParcouru = nbBlocParcouru;
    }

    public Dir getMovingAt() {
        return movingAt;
    }

    public void setMovingAt(Dir movingAt) {
        this.movingAt = movingAt;
    }

    public void setAdversaire(VueMoto adversaire) {
        this.adversaire = adversaire;
    }

    public void setNewDir(Dir newDir) {
        this.newDir = newDir;
    }

    public double getMotoX() {
        return motoX;
    }

    public double getMotoY() {
        return motoY;
    }

    public void setMotoX(double motoX) {
        this.motoX = motoX;
    }

    public void setMotoY(double motoY) {
        this.motoY = motoY;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getNbMorts() {
        return nbMorts;
    }

    public void setNbMorts(int nbMorts) {
        this.nbMorts = nbMorts;
    }

    public MediaPlayer getDeathSound() {
        return deathSound;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score.setScore(score);
    }

    public VueSprite getSprite() {
        return sprite;
    }

    public void setSprite(VueSprite skin) {
        this.sprite = skin;
    }

    public boolean isConnecter() {
        return connecter;
    }

    public void setConnecter(boolean connecter) {
        this.connecter = connecter;
    }

    //---------------------------------------------

    // crée la moto et l'instancie dans le pane de Jeu
    public void makeMoto(VueJeu jeu) {
        moto = new Rectangle();
        moto.setHeight(size);
        moto.setWidth(size);
        moto.setFill(sprite.getTrailColor());
        moto.setOpacity(0); // rend la moto invisible
        moto.setX(motoX);
        moto.setY(motoY);
        jeu.getPane().getChildren().add(moto);
        sprite.getSkinMotoView().setX(motoX - size);
        sprite.getSkinMotoView().setY(motoY - size / 4);
        sprite.getSkinMotoView().setRotate(180);
        sprite.getSkinMotoView().setScaleY(1);
        scoreManche = 0;
        if (motoName == "J1") {
            sprite.getSkinMotoView().setRotate(90);
            newDir = Dir.UP;
        } else {
            sprite.getSkinMotoView().setRotate(-90);
            newDir = Dir.DOWN;
        }
        this.stop = false;
        jeu.getMotoList().add(this);
    }

    // gère les déplacements de la moto
    public void move(VueJeu jeu) {
        if (!stop) {
            boolean adversaireMort = adversaire.collisionTrail(jeu, adversaire.newDir, adversaire.motoX, adversaire.motoY) || adversaire.collision(jeu, adversaire.newDir, adversaire.motoX, adversaire.motoY);
            boolean joueurMort = collisionTrail(jeu, newDir, motoX, motoY) || collision(jeu, newDir, motoX, motoY);
            if (!joueurMort && !jeu.isGameOver()) {
                oldDir = movingAt;
                movingAt = newDir;
                this.score.incrementScore(50);
                this.scoreManche += 50;
                adversaire.score.incrementScore(50);
                adversaire.scoreManche += 50;
                nbBlocParcouru++;
            }
            // Vérifie que l'adversaire n'a pas aussi perdu
            else if (adversaireMort && !jeu.isGameOver()) {
                playDeath();
                stopAllMoto(jeu);
                nbMorts++;
                adversaire.nbMorts += 1;
                jeu.setGameOver(true);
                adversaire.gameOver = true;
                gameOver = true;
            } else if (!jeu.isGameOver()) {
                playDeath();
                adversaire.scoreManche *= 2;
                adversaire.score.incrementScore(scoreManche);
                stopAllMoto(jeu);
                nbMorts++;
                jeu.setGameOver(true);
                gameOver = true;
            }


            if (trail) {

                Rectangle trail = new Rectangle(motoX, motoY, size, size);
                Line lineTop = new Line();
                Line lineBot = new Line();
                Line lineLeft = new Line();
                Line lineRight = new Line();
                Line lineCorner = new Line();
                Line lineCornerInv = new Line();
                LinearGradient gradient = new LinearGradient(20, 20, 100, 100, false, CycleMethod.REFLECT, new Stop(0.1, sprite.getTrailColor()), new Stop(1, Color.BLACK));

                if (newDir == Dir.LEFT && oldDir != Dir.DOWN || newDir == Dir.RIGHT && oldDir != Dir.DOWN || newDir == Dir.DOWN && oldDir != Dir.DOWN) {
                    lineTop = new Line(motoX, motoY, motoX + size, motoY);
                    lineTop.setStrokeWidth(3);
                    lineTop.setStroke(sprite.getTrailColor());
                }

                if (newDir == Dir.LEFT && oldDir != Dir.UP || newDir == Dir.RIGHT && oldDir != Dir.UP || newDir == Dir.UP && oldDir != Dir.UP) {
//
                    lineBot = new Line(motoX, motoY + size, motoX + size, motoY + size);
                    lineBot.setStrokeWidth(3);
                    lineBot.setStroke(sprite.getTrailColor());
                }

                if (newDir == Dir.DOWN && oldDir != Dir.RIGHT || newDir == Dir.UP && oldDir != Dir.RIGHT || newDir == Dir.RIGHT && oldDir != Dir.RIGHT) {
                    lineLeft = new Line(motoX, motoY, motoX, motoY + size);
                    lineLeft.setStrokeWidth(3);
                    lineLeft.setStroke(sprite.getTrailColor());
                }

                if (newDir == Dir.DOWN && oldDir != Dir.LEFT || newDir == Dir.UP && oldDir != Dir.LEFT || newDir == Dir.LEFT && oldDir != Dir.LEFT) {
                    lineRight = new Line(motoX + size, motoY, motoX + size, motoY + size);
                    lineRight.setStrokeWidth(3);
                    lineRight.setStroke(sprite.getTrailColor());
                }

                if (newDir == Dir.LEFT && oldDir == Dir.UP || newDir == Dir.RIGHT && oldDir == Dir.DOWN || newDir == Dir.UP && oldDir == Dir.LEFT || newDir == Dir.DOWN && oldDir == Dir.RIGHT) {
                    lineCorner = new Line(motoX + size, motoY, motoX, motoY + size);
                    lineCorner.setStrokeWidth(2);
                    lineCorner.setStroke(sprite.getTrailColor());
                }

                if (newDir == Dir.RIGHT && oldDir == Dir.UP || newDir == Dir.LEFT && oldDir == Dir.DOWN || newDir == Dir.UP && oldDir == Dir.RIGHT || newDir == Dir.DOWN && oldDir == Dir.LEFT) {
                    lineCornerInv = new Line(motoX, motoY, motoX + size, motoY + size);
                    lineCornerInv.setStrokeWidth(2);
                    lineCornerInv.setStroke(sprite.getTrailColor());
                }

                trail.setOpacity(0.6);
                trail.setFill(gradient);
                jeu.getTrailList().add(trail);

                jeu.getPane().getChildren().add(trail);
                jeu.getPane().getChildren().add(lineTop);
                jeu.getPane().getChildren().add(lineBot);
                jeu.getPane().getChildren().add(lineLeft);
                jeu.getPane().getChildren().add(lineRight);
                jeu.getPane().getChildren().add(lineCorner);
                jeu.getPane().getChildren().add(lineCornerInv);
                sprite.getSkinMotoView().toFront();
            } else {
                sprite.getSkinMotoView().setImage(sprite.getSkinMoto());
            }
            if (!collisionTrail(jeu, movingAt, motoX, motoY) && !collision(jeu, movingAt, motoX, motoY)) {
                if (movingAt == Dir.UP) {
                    moto.setY(motoY - speed);
                    sprite.getSkinMotoView().setImage(sprite.getSkinMoto());
                    sprite.getSkinMotoView().setX(motoX - size);
                    sprite.getSkinMotoView().setY(motoY - size);
                    sprite.getSkinMotoView().setRotate(90);

                } else if (movingAt == Dir.DOWN) {
                    moto.setY(motoY + speed);
                    sprite.getSkinMotoView().setImage(sprite.getSkinMoto());
                    sprite.getSkinMotoView().setX(motoX - size);
                    sprite.getSkinMotoView().setY(motoY + size);
                    sprite.getSkinMotoView().setRotate(270);

                } else if (movingAt == Dir.LEFT) {

                    moto.setX(motoX - speed);
                    sprite.getSkinMotoView().setX(motoX - size * 2);
                    sprite.getSkinMotoView().setY(motoY - size / 4);
                    sprite.getSkinMotoView().setRotate(0);
                    sprite.getSkinMotoView().setScaleY(-1);
                } else if (movingAt == Dir.RIGHT) {

                    moto.setX(motoX + speed);
                    sprite.getSkinMotoView().setX(motoX);
                    sprite.getSkinMotoView().setY(motoY - size / 4);
                    sprite.getSkinMotoView().setRotate(180);
                    sprite.getSkinMotoView().setScaleY(1);
                }
            }

            motoX = moto.getX();
            motoY = moto.getY();

        }
    }

    // gère les collisions des murs
    private boolean collision(VueJeu jeu, Dir direction, double x, double y) {

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
            } else if (x == adversaire.getMotoX() && y == adversaire.getMotoY()) {
                return true;
            }
        }

        return false;
    }

    // gère la collision de la trainée
    private boolean collisionTrail(VueJeu jeu, Dir direction, double x, double y) { // !attention! même bug qu'avec les murs: bugs de collision quand la moto est plus grande que le trail

        for (int i = 0; i < jeu.getTrailList().size(); i++) {
            double checkX = jeu.getTrailList().get(i).getX();
            double checkY = jeu.getTrailList().get(i).getY();

            if (direction == Dir.UP && checkY < y) {
                if ((x == checkX || (x < checkX + sprite.getTrailSize() && x > checkX)) && y - sprite.getTrailSize() <= checkY) {

                    return true;
                }
            } else if (direction == Dir.DOWN && checkY > y) {
                if ((x == checkX || (x < checkX + sprite.getTrailSize() && x > checkX)) && y + size >= checkY) {

                    return true;
                }
            } else if (direction == Dir.LEFT && checkX < x) {
                if (x - sprite.getTrailSize() <= checkX && (y == checkY || (y < checkY + sprite.getTrailSize() && y > checkY))) {

                    return true;
                }
            } else if (direction == Dir.RIGHT && checkX > x) {
                if (x + size >= checkX && (y == checkY || (y < checkY + sprite.getTrailSize() && y > checkY))) {

                    return true;
                }
            }
        }

        return false;
    }

    public void stopMoto() {
        this.setMovingAt(null);
        this.setNewDir(null);
    }

    public void stopAllMoto(VueJeu jeu) {
        for (int i = 0; i < jeu.getMotoList().size(); i++) {
            jeu.getMotoList().get(i).stopMoto();
        }
    }

    public void playDeath() {
        deathSound.seek(deathSound.getStartTime());
        deathSound.play();
    }
}
