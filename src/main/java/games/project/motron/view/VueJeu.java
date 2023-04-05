package games.project.motron.view;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class VueJeu extends Pane {
    private Pane pane;
    private final VueArene vueArene;
    private final VueMoto j1;
    private final VueMoto j2;

    private boolean pauseOn = false;

    private final ArrayList<VueMoto> motoList = new ArrayList<>();


    private final ArrayList<Rectangle> trailList = new ArrayList<>();

    private final ArrayList<KeyCode> keyList;
    private boolean gameOver;

    // Getters & setters-----------------------------

    public VueMoto getJ1() {
        return j1;
    }

    public VueMoto getJ2() {
        return j2;
    }

    public Pane getPane() {
        return pane;
    }

    public ArrayList<Rectangle> getTrailList() {
        return trailList;
    }

    public ArrayList<VueMoto> getMotoList() {
        return motoList;
    }

    public VueArene getArene() {
        return vueArene;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isPauseOn() {
        return pauseOn;
    }

    public void setPauseOn(boolean pauseOn) {
        this.pauseOn = pauseOn;
    }

    // ----------------------------------------------


    public VueJeu(VueMoto j1, VueMoto j2, VueArene vueArene, ArrayList<KeyCode> keyList) {
        this.j1 = j1;
        this.j1.setMotoX((vueArene.getMapWidth() + 120) - vueArene.getMapWidth());
        this.j2 = j2;
        this.j2.setMotoX(vueArene.getMapWidth() - 140);
        this.j1.setGameOver(false);
        this.j2.setGameOver(false);
        this.vueArene = vueArene;
        this.keyList = keyList;
    }

    // ajoute les murs au pane du Jeu
    public void setMurs() {
        for (int i = 0; i < vueArene.getMurs().getMurList().size(); i++) {
            pane.getChildren().add(vueArene.getMurs().getMurList().get(i));
        }
    }

    //ajoute les lignes à l'arène
    private void setLines() {
        for (int i = 0; i < vueArene.getLineList().size(); i++) {
            pane.getChildren().add(vueArene.getLineList().get(i));
        }
    }

    public void jouer(int xJ1, int yJ1, int xJ2, int yJ2) {
        j1.setMotoX(xJ1);
        j2.setMotoX(xJ2);
        j1.setMotoY(yJ1);
        j2.setMotoY(yJ2);
        trailList.clear();
        pane = new Pane();
        pane.setStyle("-fx-background-color : black");
        j1.makeMoto(this);
        j2.makeMoto(this);
        pane.getChildren().add(j1.getSprite().getSkinMotoView());
        pane.getChildren().add(j2.getSprite().getSkinMotoView());
        setLines();
        setMurs();
    }


    public void setTouche(Scene scene) {
        scene.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.ESCAPE) {
                pauseOn = !pauseOn;
            }

            if (event.getCode() == keyList.get(0)) {
                if (j1.getMovingAt() != VueMoto.Dir.DOWN) {
                    j1.setNewDir(VueMoto.Dir.UP);
                }
            } else if (event.getCode() == keyList.get(1)) {
                if (j1.getMovingAt() != VueMoto.Dir.RIGHT) {
                    j1.setNewDir(VueMoto.Dir.LEFT);
                }
            } else if (event.getCode() == keyList.get(2)) {
                if (j1.getMovingAt() != VueMoto.Dir.UP) {
                    j1.setNewDir(VueMoto.Dir.DOWN);
                }
            } else if (event.getCode() == keyList.get(3)) {
                if (j1.getMovingAt() != VueMoto.Dir.LEFT) {
                    j1.setNewDir(VueMoto.Dir.RIGHT);
                }
            } else if (event.getCode() == keyList.get(4)) {
                if (j2.getMovingAt() != VueMoto.Dir.DOWN) {
                    j2.setNewDir(VueMoto.Dir.UP);
                }
            } else if (event.getCode() == keyList.get(5)) {
                if (j2.getMovingAt() != VueMoto.Dir.RIGHT) {
                    j2.setNewDir(VueMoto.Dir.LEFT);
                }
            } else if (event.getCode() == keyList.get(6)) {
                if (j2.getMovingAt() != VueMoto.Dir.UP) {
                    j2.setNewDir(VueMoto.Dir.DOWN);
                }
            } else if (event.getCode() == keyList.get(7)) {
                if (j2.getMovingAt() != VueMoto.Dir.LEFT) {
                    j2.setNewDir(VueMoto.Dir.RIGHT);
                }
            }
            event.consume();
        });
    }
}
