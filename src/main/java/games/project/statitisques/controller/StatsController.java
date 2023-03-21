package games.project.statitisques.controller;

import games.project.statitisques.StatsLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;

public class StatsController extends AnchorPane {

    @FXML
    private Text txtJ;

    @FXML
    private Text txtT;

    @FXML
    private Text txtG;

    public StatsController(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(StatsLauncher.class.getResource("fxml/ViewMainStats.fxml").toString()));

            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        /*

        a mettre dans un package view et faire un fichier pour chaque

        txtG.setFont(Font.loadFont(StatsLauncher.class.getResource("font/m42.TTF").toString(), 10));
        txtT.setFont(Font.loadFont(StatsLauncher.class.getResource("font/m42.TTF").toString(), 10));
        txtJ.setFont(Font.loadFont(StatsLauncher.class.getResource("font/m42.TTF").toString(), 10));
        */

    }


}
