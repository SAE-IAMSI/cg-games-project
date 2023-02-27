package games.project.motron;

import games.project.motron.metier.manager.ScoreManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;


public class GameOver extends GridPane { //menu plus tard


    private boolean relance = false;
    private boolean retourmenu = false;

    // motos --> ArrayList<Moto> afin de vérifier si égalité ou non
    public GameOver(ArrayList<Moto> motos){
        Label txt = new Label();
        /*if(motos.size() == 1) {
            Moto m = motos.get(0);
            txt = new Label(m.getMotoName() + " a gagné !");
        }
        else{
            txt = new Label("Égalité !");
        }*/
        Moto j1 = motos.get(0);
        Moto j2 = motos.get(1);
        int nbMortsJ1 = j1.getNbMorts();
        int nbMortsJ2 = j2.getNbMorts();
        if(nbMortsJ1 == 3 || nbMortsJ2 == 3) {
            if (j1.getScore().getScore() == j2.getScore().getScore()) {
                txt = new Label("Égalité !\n" +
                        "Score " + j1.getNomJoueur() + " : " + j1.getScore().getScore() + "\n" +
                        "Score " + j2.getNomJoueur() + " : " + j2.getScore().getScore());
            } else if (j1.getScore().getScore() < j2.getScore().getScore()) {
                if(nbMortsJ2 == 0){
                    j2.getScore().incrementScore(1000);
                }
                txt = new Label(j2.getNomJoueur() + " a gagné !\n" +
                        "Score " + j1.getNomJoueur()  + " : " + j1.getScore().getScore() + "\n" +
                        "Score " + j2.getNomJoueur()  + " : " + j2.getScore().getScore());
            } else {
                if(nbMortsJ1 == 0){
                    j1.getScore().incrementScore(5000);
                }
                txt = new Label(j1.getNomJoueur() + " a gagné !\n" +
                        "Score " + j1.getNomJoueur()  + " : " + j1.getScore().getScore() + "\n" +
                        "Score " + j2.getNomJoueur()  + " : " + j2.getScore().getScore());
            }
            if (j1.isConnecter()) {
                ScoreManager.getInstance().createScore(j1.getScore().getScore(), j1.getNomJoueur());
            }
            if (j2.isConnecter()) {
                ScoreManager.getInstance().createScore(j2.getScore().getScore(), j2.getNomJoueur());
            }
        }
        /*else{
            txt = new Label("Vies restantes :\n" + j1.getMotoName() + " : " + (3-nbMortsJ1) + "\n" + j2.getMotoName() + " : " + (3-nbMortsJ2) + "\n" +
                    "Score J1 : " + j1.getScore() + "\n" +
                    "Score J2 : " + j2.getScore());
        }*/
        txt.setStyle("-fx-text-fill: WHITE");
        txt.setFont(Jeu.policeTRON);
        this.setMinHeight(500);
        this.setMinWidth(500);

        Image background = new Image("file:src/main/resources/images/tronbackground.jpg");
        BackgroundImage bgImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(1,1,true,true,false,false));
        this.setBackground(new Background(bgImage));

        Button restart = new Button("REJOUER");
        restart.setFont(Jeu.policeTRON);
        restart.setStyle("""
                -fx-background-color:\s
                linear-gradient(#062f83 0%, #ffffff 50%);
                -fx-border-width: 3;
                -fx-border-color: #ffffff;
                -fx-text-fill: black;""");
        restart.setCursor(Cursor.HAND);
        restart.setOnMouseClicked(actionEvent -> {
            //TRON.launch();
            relance=true;
            //System.out.println("relancé");
        });

        Button menu = new Button("RETOUR AU MENU");
        menu.setFont(Jeu.policeTRON);
        menu.setStyle("""
                -fx-background-color:\s
                linear-gradient(#062f83 0%, #ffffff 50%);
                -fx-border-width: 3;
                -fx-border-color: #ffffff;
                -fx-text-fill: black;""");
        menu.setCursor(Cursor.HAND);
        menu.setOnMouseClicked(actionEvent -> {
            //TRON.launch();
            retourmenu = true;
            //System.out.println("relancé");
        });


        VBox vbox = new VBox(txt, restart, menu);
        vbox.setMinHeight(600);
        vbox.setMinWidth(600);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(25);

        this.setPadding(new Insets(100));
        this.setGridLinesVisible(false);
        this.add(vbox,2,2);


    }

    public boolean isR() {
        return relance;
    }

    public void setR(boolean r) {
        this.relance = r;
    }

    public boolean isRetourmenu() {
        return retourmenu;
    }

    public void setRetourmenu(boolean retourmenu) {
        this.retourmenu = retourmenu;
    }
}
