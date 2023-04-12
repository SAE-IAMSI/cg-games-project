package games.project.modules.parametres.controller;

import games.project.metier.manager.JeuManager;
import games.project.modules.statistiques.StatsLauncher;
import games.project.modules.tournois.TournamentApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerFXML {

    @FXML
    private VBox vboxJeux;
    @FXML
    private Pane paneCompte;
    @FXML
    private Pane paneConnexion;
    @FXML
    private Pane paneCreationCompte;
    @FXML
    private Button buttonRetour;
    @FXML
    private ImageView imageFond;
    @FXML
    private Pane infoJeu;
    @FXML
    private HBox visuelJeu;
    @FXML
    private ImageView imageJeu;
    @FXML
    private ImageView flecheGauche;
    @FXML
    private ImageView flecheDroite;
    @FXML
    private HBox hboxPlay;
    @FXML
    private Pane panePlay;
    @FXML
    private Button buttonPlay;
    @FXML
    private Button buttonPlayRetour;

    private int indexImage;

    @FXML
    public void setGames() {
        List<String> jeuPath = new ArrayList<>(JeuManager.getInstance().getPaths());
        for (int i = 0; i < jeuPath.size(); i = i + 2) {
            final int fin = i + 1;
            Button b = new Button(jeuPath.get(i));
            b.setOnAction(actionEvent -> {
                try {
                    Application m = (Application) Class.forName(jeuPath.get(fin)).newInstance();
                    lanceInfoJeu(m);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            vboxJeux.getChildren().add(b);
        }
    }

    @FXML
    public void lanceCompte(){
        resetPane();
        imageFond.setOpacity(0.5);
        paneCompte.setVisible(true);
        buttonRetour.setVisible(true);
        buttonRetour.setOnMouseClicked(e -> {
            resetPane();
            buttonRetour.setVisible(false);
        });
    }

    @FXML
    public void lanceConnexion(){
        resetPane();
        imageFond.setOpacity(0.5);
        paneConnexion.setVisible(true);
        buttonRetour.setVisible(true);
        buttonRetour.setOnMouseClicked(e -> lanceCompte());
    }

    @FXML
    public void lanceCreationCompte(){
        resetPane();
        imageFond.setOpacity(0.5);
        paneCreationCompte.setVisible(true);
        buttonRetour.setVisible(true);
        buttonRetour.setOnMouseClicked(e -> lanceCompte());
    }

    @FXML
    public void resetPane(){
        paneCompte.setVisible(false);
        paneConnexion.setVisible(false);
        paneCreationCompte.setVisible(false);
        infoJeu.setVisible(false);
        buttonRetour.setVisible(false);
        imageFond.setOpacity(1);
    }

    @FXML
    public void lanceInfoJeu(Application jeu) {
        resetPane();
        indexImage = 0;
        imageFond.setOpacity(0.5);
        infoJeu.setVisible(true);
        ArrayList<Image> wallpaper = setWallpaper(jeu);
        imageJeu.setImage(wallpaper.get(indexImage));
        imageJeu.setOpacity(1);
        flecheDroite.setOpacity(1);
        flecheGauche.setOpacity(1);
        flecheGauche.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                indexImage --;
                if (indexImage < 0) {
                    indexImage = 2;
                }
                changerImageJeu(wallpaper.get(indexImage));

            }
        });
        flecheDroite.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                indexImage ++;
                if (indexImage > 2) {
                    indexImage = 0;
                }
                changerImageJeu(wallpaper.get(indexImage));
            }
        });
        hboxPlay.setOpacity(1);
        panePlay.setOpacity(0.5);
        buttonPlay.setOnAction(actionEvent -> Platform.runLater(() -> {
            try {
                jeu.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        buttonPlayRetour.setOnAction(e -> resetPane());
    }

    public void changerImageJeu(Image image) {
        imageJeu.setImage(image);
    }

    public ArrayList<Image> setWallpaper(Application jeu) {
        ArrayList<Image> wallpaper = new ArrayList<>();
        wallpaper.add(new Image(String.valueOf(jeu.getClass().getResource("images/wallpaper/presentation1.png"))));
        wallpaper.add(new Image(String.valueOf(jeu.getClass().getResource("images/wallpaper/presentation2.png"))));
        wallpaper.add(new Image(String.valueOf(jeu.getClass().getResource("images/wallpaper/presentation3.png"))));
        return wallpaper;
    }

    @FXML
    public void lanceTournois(){
        Platform.runLater(() -> {
            try {
                new TournamentApplication().start(new Stage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void lanceStatistiques(){
        Platform.runLater(() -> {
            new StatsLauncher().start(new Stage());
        });
    }
}
