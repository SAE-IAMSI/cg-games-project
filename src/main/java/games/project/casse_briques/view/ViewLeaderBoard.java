package games.project.casse_briques.view;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.GameMenuController;
import games.project.metier.entite.Score;
import games.project.metier.manager.ScoreManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;


public class ViewLeaderBoard extends Pane {
    private final Font cFont;
    @FXML
    private Text titre;
    @FXML
    private Text scoreText;
    @FXML
    private Text txtBack;

    private final BrickBreakerController brickBreakerController;
    private final GameMenuController gameMenu;


    public ViewLeaderBoard(BrickBreakerController brickBreakerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LeaderBoard.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        this.brickBreakerController = brickBreakerController;
        gameMenu = new GameMenuController(brickBreakerController);
        cFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 12);
        titre.setFont(cFont);
        txtBack.setFont(cFont);

        ScoreManager sm = ScoreManager.getInstance();
        List<Score> scores = sm.getLeaderboardByGame("CB");

        scoreText.setFont(cFont);
        scoreText.setText("");
        for (Score score : scores) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(score.getHorodatage());
            String month = "";
            switch (cal.get(Calendar.MONTH)) {
                case 0:
                    month = "Janvier";
                    break;
                case 1:
                    month = "Fevrier";
                    break;
                case 2:
                    month = "Mars";
                    break;
                case 3:
                    month = "Avril";
                    break;
                case 4:
                    month = "Mai";
                    break;
                case 5:
                    month = "Juin";
                    break;
                case 6:
                    month = "Juillet";
                    break;
                case 7:
                    month = "Aout";
                    break;
                case 8:
                    month = "Septembre";
                    break;
                case 9:
                    month = "Octobre";
                    break;
                case 10:
                    month = "Novembre";
                    break;
                default:
                    month = "Decembre";
                    break;
            }
            String line = (score.getLogin() == null ? "Invite" : score.getLogin()) + " ---> " + score.getScore() + " || " + cal.get(Calendar.DAY_OF_MONTH) + " " + month + " " + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + "H" + cal.get(Calendar.MINUTE) + " ||";
            scoreText.setText(scoreText.getText() + " \n" + line + "\n");
        }
    }

    @FXML
    private void clickBack() {
        brickBreakerController.getChildren().remove(this);
        gameMenu.startMenu();
    }

}
