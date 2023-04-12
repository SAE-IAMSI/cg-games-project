package games.project.paco_mano.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Jeu extends Pane {
    private ArrayList<Ghost> ghostArrayList;
    private PacMan pacMan;
    private Arene arene;

    public Arene getArene() {
        return arene;
    }

    public Jeu(PacMan pacMan, ArrayList<Ghost> ghostArrayList, Arene arene){
        this.arene=arene;
        this.pacMan=pacMan;
        this.ghostArrayList=ghostArrayList;
        /*Mur mur = new Mur();
        mur.dessinerCarte(2);

        arene.setMurs(mur);*/
        arene.drawLines();
        arene.drawPellets(this);
        arene.drawBonusFood(this);
        this.setLines();
        this.setMurs();
    }

    public void setMurs() {
        for (int i = 0; i < arene.getMurs().getMurList().size(); i++) {
            this.getChildren().add(arene.getMurs().getMurList().get(i));
        }
    }

    //ajoute les lignes à l'arène
    private void setLines() {
        for (int i = 0; i < arene.getLineList().size(); i++) {
            this.getChildren().add(arene.getLineList().get(i));
        }
    }

    public void setTouches(Scene scene){
        scene.setOnKeyPressed(event ->
        {
            switch (event.getCode()) {
                case UP -> pacMan.setNewDir(PacMan.Dir.UP);
                case DOWN -> pacMan.setNewDir(PacMan.Dir.DOWN);
                case LEFT -> pacMan.setNewDir(PacMan.Dir.LEFT);
                case RIGHT -> pacMan.setNewDir(PacMan.Dir.RIGHT);
            }
        });

    }


}
