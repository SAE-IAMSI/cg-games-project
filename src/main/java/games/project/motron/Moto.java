package games.project.motron;

import games.project.motron.metier.entite.Score;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.ArrayList;

public class Moto {

    enum Dir {
        UP, DOWN, LEFT, RIGHT
    }

//    ArrayList<ImageView> listMotoView = new ArrayList<ImageView>(/*Arrays.asList(blueMotoView, redMotoView)*/);
    private String motoName;
    private String nomJoueur;
    private Rectangle moto;
    private int size = 20;
    private double motoX = 100;
    private double motoY = 400;
    private int speed = 20;

    private boolean trail = true; //false pour le mode activer/désactiver
    private boolean gameOver = false;
    private Moto adversaire;
    private ArrayList<Image> vies;
    private int nbMorts;

    private boolean stop;

//    private Color brighterTrail = trailColor.brighter();
//    private Color trailColor2 = Color.DARKBLUE;

    private Dir movingAt;
    private Dir newDir;
    private Dir oldDir;
    private MediaPlayer deathSound;
    private Score score;
    private int scoreManche;
    private Sprite sprite;
    private boolean connecter;


    //Constructeur--------------------------------

    public Moto(String motoName, Sprite skin) {
        this.motoName = motoName;
        this.sprite = skin;
        this.nbMorts = 0;
        String linkSoundDeath = "src/main/resources/music/Explosion8bit.mp3";
        Media soundDeath = new Media(new File(linkSoundDeath).toURI().toString());
        this.deathSound = new MediaPlayer(soundDeath);
        connecter = false;
    }
    public Moto(String motoName, Sprite skin, String nomJoueur) {
        this.motoName = motoName;
        this.nomJoueur = nomJoueur;
        this.sprite = skin;
        this.nbMorts = 0;
        String linkSoundDeath = "src/main/resources/music/Explosion8bit.mp3";
        Media soundDeath = new Media(new File(linkSoundDeath).toURI().toString());
        this.deathSound = new MediaPlayer(soundDeath);
        this.score = new Score(nomJoueur);
        connecter = false;
    }

    public Moto(Moto moto){
        this.motoName = moto.motoName;
        this.sprite = moto.getSprite();
        connecter = false;
    }



    //-------------------------------------------

    //Getters & setters----------------------------
    public Dir getMovingAt() {
        return movingAt;
    }

    public void setMovingAt(Dir movingAt) {
        this.movingAt = movingAt;
    }

    public void setAdversaire(Moto adversaire) {
        this.adversaire = adversaire;
    }

    public Dir getNewDir() {
        return newDir;
    }

    public void setNewDir(Dir newDir) {
        this.newDir = newDir;
    }

    public boolean isTrail() {
        return trail;
    }

    public void setTrail(boolean trail) {
        this.trail = trail;
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

    public String getMotoName() {
        return motoName;
    }

    public void setMotoName(String motoName) {
        this.motoName = motoName;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public Rectangle getMoto() {
        return moto;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setOldDir(Dir oldDir) {
        this.oldDir = oldDir;
    }

    public ArrayList<Image> getVies() {
        return vies;
    }

    public void setVies(ArrayList<Image> vies) {
        this.vies = vies;
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

    public int getScoreManche() {
        return scoreManche;
    }

    public void setScoreManche(int scoreManche) {
        this.scoreManche = scoreManche;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite skin) {
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
    public void makeMoto(Jeu jeu) {
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
        Image vie;
        if(motoName == "J1"){
            sprite.getSkinMotoView().setRotate(90);
            newDir = Dir.UP;
            vie = new Image("file:src/main/resources/images/DiscTronBleu.png", 60, 40, true, true);
        }
        else{
            sprite.getSkinMotoView().setRotate(-90);
            newDir = Dir.DOWN;
            vie = new Image("file:src/main/resources/images/DiscTronRouge.png", 60, 40, true, true);
        }
        vies = new ArrayList<>();
        for (int i=0; i<3; i++) {
            vies.add(vie);
        }
        for(int i=0; i<nbMorts; i++){
            vies.remove(i);
            vies.add(i, new Image("file:src/main/resources/images/DiscTronVide.png", 60, 40, true, true));
        }
        this.stop = false;
        jeu.getMotoList().add(this);
    }

    // gère les déplacements de la moto
    // "bug" : collision quand la moto va en arrière. Peut être ajouter une condition qui empèche le joueur de reculer quand le trail est true
    public void move(Jeu jeu) {
//        moto_keyFrame = new KeyFrame(Duration.millis(keyFrameMillis), e -> { // note: MOINS la valeur de la KeyFrame est élevée, PLUS la moto va aller vite
        if(!stop) {
            boolean adversaireMort = adversaire.collisionTrail(jeu, adversaire.newDir, adversaire.motoX, adversaire.motoY) || adversaire.collision(jeu, adversaire.newDir, adversaire.motoX, adversaire.motoY);
            boolean joueurMort = collisionTrail(jeu, newDir, motoX, motoY) ||  collision(jeu, newDir, motoX, motoY);
            if (!joueurMort) {
                oldDir = movingAt;
                movingAt = newDir;
                this.score.incrementScore(50);
                this.scoreManche += 50;
                jeu.setScore(this, this.score.getScore());
                adversaire.score.incrementScore(50);
                adversaire.scoreManche += 50;
                jeu.setScore(adversaire, adversaire.score.getScore());
            }
            // Vérifie que l'adversaire n'a pas aussi perdu
            else if (adversaireMort) {
                System.out.println("egalite");
                playDeath();
                jeu.setMotoMorte(this);
                stopAllMoto(jeu);
                ArrayList<Moto> motoFin = jeu.getMotoList();
                nbMorts++;
                adversaire.nbMorts += 1;
                GameOver go = new GameOver(motoFin);
                jeu.setGameOver(go);
                gameOver = true;
            } else {
                System.out.println("mort " + motoName);
                playDeath();
                adversaire.scoreManche *= 2;
                adversaire.score.incrementScore(scoreManche);
                System.out.println(adversaire.scoreManche);
                jeu.setScore(adversaire, adversaire.score.getScore());
                jeu.setMotoMorte(this);
                stopAllMoto(jeu);
                ArrayList<Moto> motoFin = jeu.getMotoList();
                nbMorts++;
                GameOver go = new GameOver(motoFin);
                jeu.setGameOver(go);
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
//                LinearGradient gradient = new LinearGradient(0, 0, 0, 0, true, CycleMethod.NO_CYCLE, new Stop(0.1, trailColor), new Stop(0, trailColor));
                LinearGradient gradient = new LinearGradient(20, 20, 100, 100, false, CycleMethod.REFLECT, new Stop(0.1, sprite.getTrailColor()), new Stop(1, Color.BLACK));

                if (newDir == Dir.LEFT && oldDir != Dir.DOWN || newDir == Dir.RIGHT && oldDir != Dir.DOWN || newDir == Dir.DOWN && oldDir != Dir.DOWN) {
//                    gradient = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, new Stop(0.1, trailColor), new Stop(1, trailColor2));
//                    gradient = new LinearGradient(20, 20, 100, 100, false, CycleMethod.REFLECT, new Stop(0.1, trailColor), new Stop(1, Color.BLACK));
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
//                    gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0.1, trailColor), new Stop(1, trailColor2));
//                    gradient = new LinearGradient(20, 20, 100, 100, false, CycleMethod.REFLECT, new Stop(0.1, trailColor), new Stop(1, Color.BLACK));
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
//
                    lineCorner = new Line(motoX + size, motoY, motoX, motoY + size);
                    lineCorner.setStrokeWidth(2);
                    lineCorner.setStroke(sprite.getTrailColor());
                }

                if (newDir == Dir.RIGHT && oldDir == Dir.UP || newDir == Dir.LEFT && oldDir == Dir.DOWN || newDir == Dir.UP && oldDir == Dir.RIGHT || newDir == Dir.DOWN && oldDir == Dir.LEFT) {
//                    gradient = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, new Stop(0.1, trailColor), new Stop(1, trailColor2));
//                    gradient = new LinearGradient(20, 20, 100, 100, false, CycleMethod.REFLECT, new Stop(0.1, trailColor), new Stop(1, Color.BLACK));
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
    private boolean collision(Jeu jeu, Dir direction, double x, double y) {  // !attention! bugs de collision quand la moto est plus grande que le mur

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

    // gère la collision de la trainée
    private boolean collisionTrail(Jeu jeu, Dir direction, double x, double y) { // !attention! même bug qu'avec les murs: bugs de collision quand la moto est plus grande que le trail

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

    public void stopAllMoto(Jeu jeu){
        for (int i=0; i<  jeu.getMotoList().size(); i++)
        {
            jeu.getMotoList().get(i).stopMoto();
        }
    }

    public void playDeath(){
        deathSound.seek(deathSound.getStartTime());
        deathSound.play();
    }


    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
