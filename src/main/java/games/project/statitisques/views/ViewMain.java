package games.project.statitisques.views;

import games.project.statitisques.StatsLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class ViewMain extends Pane {
    @FXML
    private Text txtJo;

    @FXML
    private Text txtT;

    @FXML
    private Text txtG;

    @FXML
    private Text txtJe;

    @FXML
    private Text title;


    private final StatsLauncher statsLauncher;

    public ViewMain(StatsLauncher statsLauncher) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/ViewStatsGlobal.fxml"));
        this.statsLauncher = statsLauncher;

    }

    //renvoie vers la page des stats globales
    @FXML
    private void globalButton(MouseEvent event){

    }

    //renvoie vers la page des stats des joueurs
    @FXML
    private void playersButton(MouseEvent event){

    }

    @FXML
    private void tournamentButton(MouseEvent event){

    }

    @FXML
    private void gamesButton(MouseEvent event){

    }

    //renvoie vers le launcher
    @FXML
    private void previousPage(){

    }
}
