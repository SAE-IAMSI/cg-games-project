package games.project.casse_briques.view;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.GameMenuController;
import games.project.metier.entite.Score;
import games.project.metier.manager.ScoreManager;
import games.project.stockage.Session;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class ViewPlayerLeaderBoard extends Pane {

    @FXML
    private Text title;
    @FXML
    private Text listScoreText;
    @FXML
    private Text backText;
    @FXML
    private CheckBox showOtherGame;

    private final GameMenuController gameMenu;
    private final BrickBreakerController brickBreakerController;

    public ViewPlayerLeaderBoard(BrickBreakerController brickBreakerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerLeaderBoard.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        this.brickBreakerController = brickBreakerController;
        this.gameMenu = new GameMenuController(brickBreakerController);
        Font cFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 12);
        Font btFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 8);
        Font checkBoxFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 7);

        title.setFont(cFont);
        backText.setFont(btFont);
        listScoreText.setFont(cFont);

        showOtherGame.setFont(checkBoxFont);


        generateScoreList();
        generateOnlyOther();
        showOtherGame.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    listScoreText.setText("");
                    generateOnlyOther();
                } else {
                    listScoreText.setText("");
                    generateScoreList();
                    generateOnlyOther();
                }
            }
        });
    }

    private void generateScoreList() {
        Session session = Session.getInstance();
        if (session.isConnected()) {
            String login = session.getLogin();
            ScoreManager sm = ScoreManager.getInstance();
            List<Score> scoreList = sm.getScoresHistoryByLogin(login);

            listScoreText.setText("");

            for (Score score : scoreList) {

                Calendar cal = getTime(score);
                String nomDuJeu = score.getGameCode();
                String scoreCB = nomDuJeu + " : " + login + " ---> " + score.getScore() + " || " + cal.get(Calendar.DAY_OF_MONTH) + " " + getMonth(cal) + " " + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + "H" + cal.get(Calendar.MINUTE) + " ||";
                listScoreText.setText(listScoreText.getText() + "\n" + scoreCB + "\n");
            }
        }
    }

    private void generateOnlyOther() {
        if (Session.getInstance().isConnected()) {
            String login = Session.getInstance().getLogin();
            ScoreManager sm = ScoreManager.getInstance();
            Score highScoreDK = sm.getHighScoreByGame(login, "DK");
            Score highScoreTetris = sm.getHighScoreByGame(login, "TETRIS");
            Score highScoreTRON = sm.getHighScoreByGame(login, "TRON");
            String scoreDK = "";
            String scoreTETRIS = "";
            String scoreTRON = "";

            if (highScoreTetris != null) {
                Calendar cal = getTime(highScoreTetris);
                scoreTETRIS = highScoreTetris.getGameCode() + " : " + highScoreTetris.getLogin() + " ---> " + highScoreTetris.getScore() + " || " + +cal.get(Calendar.DAY_OF_MONTH) + " " + getMonth(cal) + " " + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + "H" + cal.get(Calendar.MINUTE) + " ||";
            }
            if (highScoreDK != null) {
                Calendar cal = getTime(highScoreDK);
                scoreDK = highScoreDK.getGameCode() + " : " + highScoreDK.getLogin() + " ---> " + highScoreDK.getScore() + " || " + +cal.get(Calendar.DAY_OF_MONTH) + " " + getMonth(cal) + " " + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + "H" + cal.get(Calendar.MINUTE) + " ||";
            }
            if (highScoreTRON != null) {
                Calendar cal = getTime(highScoreTRON);
                scoreTRON = highScoreTRON.getGameCode() + " : " + highScoreTRON.getLogin() + " ---> " + highScoreTRON.getScore() + " || " + +cal.get(Calendar.DAY_OF_MONTH) + " " + getMonth(cal) + " " + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + "H" + cal.get(Calendar.MINUTE) + " ||";
            }
            listScoreText.setText(listScoreText.getText() + scoreTETRIS + "\n" + scoreDK + "\n" + scoreTRON);
        }
    }

    private Calendar getTime(Score score) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(score.getHorodatage());
        return cal;
    }

    private String getMonth(Calendar cal) {
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
        return month;
    }

    @FXML
    private void clickBack() {
        gameMenu.showBtStartMenuConnected();
        brickBreakerController.getChildren().remove(this);
    }

}
